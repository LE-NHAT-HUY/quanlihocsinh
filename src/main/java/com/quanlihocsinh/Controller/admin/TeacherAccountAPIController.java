package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.dao.TeacherDAO;
import com.quanlihocsinh.dao.UserDAO;
import com.quanlihocsinh.model.Teacher;
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

@WebServlet("/admin/teacher-account")
public class TeacherAccountAPIController extends HttpServlet {

    private final TeacherDAO teacherDAO = new TeacherDAO();
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String teacherID = request.getParameter("teacherID");
        try (PrintWriter out = response.getWriter()) {
            if (teacherID == null || teacherID.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(jsonError("Thiếu teacherID"));
                return;
            }

            AccountInfo account = findByTeacherID(teacherID.trim());
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
                String teacherID = request.getParameter("teacherID");
                if (teacherID == null || teacherID.trim().isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print(jsonError("Thiếu teacherID"));
                    return;
                }

                String normalizedTeacherID = teacherID.trim();
                if (findAccountByTeacherCode(normalizedTeacherID) != null) {
                    out.print(jsonError("Giáo viên này đã có tài khoản"));
                    return;
                }

                Teacher teacher = teacherDAO.getById(parseIntOrZero(request.getParameter("id")));
                // try to find by TeacherID if id param not present
                if (teacher == null) {
                    // fallback: try to look up teacher by TeacherID field
                    // TeacherDAO doesn't have getByTeacherCode helper; query by getAll and match
                    teacher = findTeacherByTeacherCode(normalizedTeacherID);
                }

                if (teacher == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print(jsonError("Không tìm thấy giáo viên"));
                    return;
                }

                if (teacher.getPersonId() <= 0) {
                    int personId = createPersonForTeacher(teacher);
                    if (personId <= 0) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        out.print(jsonError("Không tạo được Person cho giáo viên"));
                        return;
                    }

                    teacherDAO.updatePersonId(teacher.getId(), personId);
                    teacher.setPersonId(personId);
                }

                String rawPassword = PasswordUtil.generateRandomPassword(8);
                String hashedPassword = PasswordUtil.hashPassword(rawPassword);

                int userId = insertTeacherAccount(teacher, hashedPassword);
                if (userId <= 0) {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.print(jsonError("Không cấp được tài khoản"));
                    return;
                }

                out.print("{\"success\":true,\"message\":\"Đã cấp tài khoản thành công\",\"rawPassword\":\""
                        + escapeJson(rawPassword) + "\",\"userId\":" + userId + ",\"teacherID\":\""
                        + escapeJson(teacher.getTeacherID()) + "\",\"fullName\":\""
                        + escapeJson(teacher.getFullName()) + "\",\"isActive\":true}");
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

    private AccountInfo findByTeacherID(String teacherID) throws SQLException {
        String sql = "SELECT TOP 1 u.user_id, u.username, u.is_active, p.fullname, u.person_id " +
                "FROM Users u " +
                "LEFT JOIN Person p ON p.person_id = u.person_id " +
                "WHERE u.username = ? AND u.role_id = 2";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, teacherID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    AccountInfo info = new AccountInfo();
                    info.teacherID = teacherID;
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

    private AccountInfo findAccountByTeacherCode(String teacherID) throws SQLException {
        if (teacherID == null || teacherID.trim().isEmpty()) {
            return null;
        }

        String sql = "SELECT TOP 1 u.user_id, u.username, u.is_active, p.fullname, u.person_id " +
                "FROM Users u " +
                "LEFT JOIN Person p ON u.person_id = p.person_id " +
                "WHERE u.username = ? AND u.role_id = 2";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, teacherID.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    AccountInfo info = new AccountInfo();
                    info.exists = true;
                    info.personId = rs.getInt("person_id");
                    info.userId = rs.getInt("user_id");
                    info.username = rs.getString("username");
                    info.teacherID = rs.getString("username");
                    info.fullName = rs.getString("fullname");
                    info.isActive = rs.getBoolean("is_active");
                    return info;
                }
            }
        }

        return null;
    }

    private int createPersonForTeacher(Teacher teacher) throws SQLException {
        String sql = "INSERT INTO Person(fullname, birth, gender, address, phone, images, is_active) VALUES(?, ?, ?, ?, ?, ?, 1)";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, teacher.getFullName());
            if (teacher.getBirth() != null) {
                ps.setDate(2, new java.sql.Date(teacher.getBirth().getTime()));
            } else {
                ps.setNull(2, Types.DATE);
            }
            ps.setString(3, teacher.getGender());
            ps.setString(4, teacher.getAddress());
            ps.setString(5, teacher.getNumberPhone());
            ps.setString(6, teacher.getImages());

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

    private int insertTeacherAccount(Teacher teacher, String hashedPassword) throws SQLException {
        String sql = "INSERT INTO Users(username, password_hash, role_id, person_id, is_active, created_at) VALUES(?, ?, 2, ?, 1, GETDATE())";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, teacher.getTeacherID());
            ps.setString(2, hashedPassword);
            ps.setInt(3, teacher.getPersonId());
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
        String sql = "UPDATE Users SET password_hash = ?, is_active = ? WHERE user_id = ? AND role_id = 2";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, passwordHash);
            ps.setBoolean(2, isActive);
            ps.setInt(3, userId);
            ps.executeUpdate();
        }
    }

    private Teacher findTeacherByTeacherCode(String teacherCode) {
        try {
            for (Teacher t : teacherDAO.findAll()) {
                if (t.getTeacherID() != null && t.getTeacherID().equalsIgnoreCase(teacherCode)) {
                    return t;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
        String teacherID;
        String fullName;
        boolean isActive;

        String toJson() {
            return "{\"exists\":" + exists + ",\"userId\":" + userId +
                    ",\"personId\":" + personId +
                    ",\"username\":\"" + username +
                    "\",\"teacherID\":\"" + teacherID +
                    "\",\"fullName\":\"" + fullName +
                    "\",\"isActive\":" + isActive + "}";
        }
    }
}
