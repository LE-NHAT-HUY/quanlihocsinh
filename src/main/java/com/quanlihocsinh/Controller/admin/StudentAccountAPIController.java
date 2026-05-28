package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.dao.StudentDAO;
import com.quanlihocsinh.dao.UserDAO;
import com.quanlihocsinh.model.Student;
import com.quanlihocsinh.model.User;
import com.quanlihocsinh.util.DBUtil;
import com.quanlihocsinh.util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

@WebServlet("/admin/student-account")
public class StudentAccountAPIController extends HttpServlet {

    private final StudentDAO studentDAO = new StudentDAO();
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String studentID = request.getParameter("studentID");
        try (PrintWriter out = response.getWriter()) {
            if (studentID == null || studentID.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(jsonError("Thiếu studentID"));
                return;
            }

            AccountInfo account = findByStudentID(studentID.trim());
            if (account == null) {
                out.print("{\"exists\":false}");
                return;
            }

            out.print(account.toJson());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try (PrintWriter out = response.getWriter()) {
                out.print(jsonError("Lỗi khi lấy thông tin tài khoản: " + e.getMessage()));
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String action = request.getParameter("action");
        try (PrintWriter out = response.getWriter()) {
            if (action == null || action.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(jsonError("Thiếu action"));
                return;
            }

            if ("generate".equalsIgnoreCase(action)) {
                String studentID = request.getParameter("studentID");
                if (studentID == null || studentID.trim().isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print(jsonError("Thiếu studentID"));
                    return;
                }

                String normalizedStudentID = studentID.trim();
                if (findAccountByStudentCode(normalizedStudentID) != null) {
                    out.print(jsonError("Học sinh này đã có tài khoản"));
                    return;
                }

                Student student = studentDAO.getByStudentId(normalizedStudentID);
                if (student == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print(jsonError("Không tìm thấy học sinh"));
                    return;
                }

                if (student.getPersonId() <= 0) {
                    int personId = createPersonForStudent(student);
                    if (personId <= 0) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        out.print(jsonError("Không tạo được Person cho học sinh"));
                        return;
                    }

                    studentDAO.updatePersonId(student.getId(), personId);
                    student.setPersonId(personId);
                }

                String rawPassword = PasswordUtil.generateRandomPassword(8);
                String hashedPassword = PasswordUtil.hashPassword(rawPassword);

                int userId = insertStudentAccount(student, hashedPassword);
                if (userId <= 0) {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.print(jsonError("Không cấp được tài khoản"));
                    return;
                }

                out.print("{\"success\":true,\"message\":\"Đã cấp tài khoản thành công\",\"rawPassword\":\""
                        + escapeJson(rawPassword) + "\",\"userId\":" + userId + ",\"studentID\":\""
                        + escapeJson(student.getStudentID()) + "\",\"fullName\":\""
                        + escapeJson(student.getFullName()) + "\",\"isActive\":true}");
                return;
            }

            if ("update".equalsIgnoreCase(action)) {
                int userId = parseIntOrZero(request.getParameter("userId"));
                String password = request.getParameter("password");
                boolean isActive = "1".equals(request.getParameter("isActive"))
                        || "true".equalsIgnoreCase(request.getParameter("isActive"));

                if (userId <= 0) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print(jsonError("userId không hợp lệ"));
                    return;
                }

                User current = userDAO.getById(userId);
                if (current == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print(jsonError("Không tìm thấy tài khoản"));
                    return;
                }

                String newPasswordHash = current.getPassword();
                if (password != null && !password.trim().isEmpty()) {
                    newPasswordHash = PasswordUtil.hashPassword(password.trim());
                }

                updateAccountStatusAndPassword(userId, newPasswordHash, isActive);
                out.print("{\"success\":true,\"message\":\"Đã cập nhật tài khoản thành công\"}");
                return;
            }

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(jsonError("Action không hợp lệ"));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try (PrintWriter out = response.getWriter()) {
                out.print(jsonError("Lỗi xử lý tài khoản: " + e.getMessage()));
            }
        }
    }

    private AccountInfo findByStudentID(String studentID) throws SQLException {
        String sql = "SELECT TOP 1 u.user_id, u.username, u.is_active, p.fullname, u.person_id " +
                "FROM Users u " +
                "LEFT JOIN Person p ON p.person_id = u.person_id " +
                "WHERE u.username = ? AND u.role_id = 3";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, studentID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    AccountInfo info = new AccountInfo();
                    info.studentID = studentID;
                    info.personId = rs.getInt("person_id");
                    info.userId = rs.getInt("user_id");
                    info.username = rs.getString("username");
                    info.fullName = rs.getString("fullname");
                    info.isActive = rs.getBoolean("is_active");
                    info.exists = info.userId > 0;
                    return info;
                }
            }
        }
        return null;
    }

    private AccountInfo findAccountByStudentCode(String studentID) throws SQLException {
        if (studentID == null || studentID.trim().isEmpty()) {
            return null;
        }

        String sql = "SELECT TOP 1 u.user_id, u.username, u.is_active, p.fullname, u.person_id " +
                "FROM Users u " +
                "LEFT JOIN Person p ON u.person_id = p.person_id " +
                "WHERE u.username = ? AND u.role_id = 3";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, studentID.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    AccountInfo info = new AccountInfo();
                    info.exists = true;
                    info.personId = rs.getInt("person_id");
                    info.userId = rs.getInt("user_id");
                    info.username = rs.getString("username");
                    info.studentID = rs.getString("username");
                    info.fullName = rs.getString("fullname");
                    info.isActive = rs.getBoolean("is_active");
                    return info;
                }
            }
        }

        return null;
    }

    private int createPersonForStudent(Student student) throws SQLException {
        String sql = "INSERT INTO Person(fullname, birth, gender, address, phone, images, is_active) VALUES(?, ?, ?, ?, ?, ?, 1)";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, student.getFullName());
            if (student.getBirth() != null) {
                ps.setDate(2, new java.sql.Date(student.getBirth().getTime()));
            } else {
                ps.setNull(2, Types.DATE);
            }
            ps.setString(3, student.getGender());
            ps.setString(4, student.getAddress());
            ps.setString(5, student.getNumberPhone());
            ps.setString(6, student.getImages());

            int affected = ps.executeUpdate();
            if (affected <= 0) {
                return 0;
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }

    private int insertStudentAccount(Student student, String hashedPassword) throws SQLException {
        String sql = "INSERT INTO Users(username, password_hash, role_id, person_id, is_active, created_at) VALUES(?, ?, 3, ?, 1, GETDATE())";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, student.getStudentID());
            ps.setString(2, hashedPassword);
            ps.setInt(3, student.getPersonId());
            int affected = ps.executeUpdate();
            if (affected <= 0) {
                return 0;
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    private void updateAccountStatusAndPassword(int userId, String passwordHash, boolean isActive) throws SQLException {
        String sql = "UPDATE Users SET password_hash = ?, is_active = ? WHERE user_id = ? AND role_id = 3";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, passwordHash);
            ps.setBoolean(2, isActive);
            ps.setInt(3, userId);
            ps.executeUpdate();
        }
    }

    private int parseIntOrZero(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }

    private String jsonError(String message) {
        return "{\"success\":false,\"message\":\"" + escapeJson(message) + "\"}";
    }

    private String escapeJson(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "\\r")
                .replace("\n", "\\n");
    }

    private static class AccountInfo {
        boolean exists;
        int userId;
        int personId;
        String username;
        String studentID;
        String fullName;
        boolean isActive;

        String toJson() {
            return "{\"exists\":" + exists + ",\"userId\":" + userId +
                    ",\"personId\":" + personId +
                    ",\"username\":\"" + username +
                    "\",\"studentID\":\"" + studentID +
                    "\",\"fullName\":\"" + fullName +
                    "\",\"isActive\":" + isActive + "}";
        }
    }
}
