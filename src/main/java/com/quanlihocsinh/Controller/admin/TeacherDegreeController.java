package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.dao.TeacherDAO;
import com.quanlihocsinh.dao.TeacherDegreeDAO;
import com.quanlihocsinh.model.Teacher;
import com.quanlihocsinh.model.TeacherDegree;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/teacher-degree")
public class TeacherDegreeController extends HttpServlet {

    private final TeacherDegreeDAO degreeDAO = new TeacherDegreeDAO();
    private final TeacherDAO teacherDAO = new TeacherDAO();

    private void setFlash(HttpServletRequest request, String success, String error) {
        HttpSession session = request.getSession();
        if (success != null) {
            session.setAttribute("flashSuccess", success);
        }
        if (error != null) {
            session.setAttribute("flashError", error);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null || action.trim().isEmpty() || "list".equalsIgnoreCase(action)) {
            showList(request, response);
            return;
        }

        switch (action.toLowerCase()) {
            case "add":
                request.setAttribute("mode", "add");
                showForm(request, response, null, "add");
                break;
            case "edit":
                int editId = parseIntOrZero(request.getParameter("id"));
                TeacherDegree degree = degreeDAO.getById(editId);
                if (degree == null) {
                    setFlash(request, null, "Không tìm thấy bằng cấp cần chỉnh sửa.");
                    response.sendRedirect(request.getContextPath() + "/admin/teacher-degree");
                    return;
                }
                showForm(request, response, degree, "edit");
                break;
            case "delete":
                int deleteId = parseIntOrZero(request.getParameter("id"));
                int teacherID = parseIntOrZero(request.getParameter("teacherID"));
                degreeDAO.delete(deleteId);
                setFlash(request, "Đã xóa bằng cấp.", null);
                response.sendRedirect(buildListUrl(request, teacherID));
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/admin/teacher-degree");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null || action.trim().isEmpty()) {
            request.setAttribute("flashError", "Thiếu hành động xử lý form.");
            request.setAttribute("teachers", teacherDAO.getAll());
            request.setAttribute("selectedTeacherId", parseIntOrZero(request.getParameter("teacherID")));
            request.setAttribute("degree", buildDegreeFromRequest(request));
            request.setAttribute("mode", "add");
            request.getRequestDispatcher("/WEB-INF/views/admin/teacher/degree/form.jsp").forward(request, response);
            return;
        }

        try {
            TeacherDegree degree = buildDegreeFromRequest(request);
            if (degree.getTeacherID() <= 0) {
                request.setAttribute("flashError", "Vui lòng chọn giáo viên.");
                request.setAttribute("teachers", teacherDAO.getAll());
                request.setAttribute("selectedTeacherId", degree.getTeacherID());
                request.setAttribute("degree", degree);
                request.setAttribute("mode", "add");
                request.getRequestDispatcher("/WEB-INF/views/admin/teacher/degree/form.jsp").forward(request, response);
                return;
            }

            if ("add".equalsIgnoreCase(action)) {
                degreeDAO.add(degree);
                setFlash(request, "Thêm bằng cấp thành công.", null);
                response.sendRedirect(buildListUrl(request, degree.getTeacherID()));
            } else if ("edit".equalsIgnoreCase(action)) {
                degree.setDegreeID(parseIntOrZero(request.getParameter("id")));
                degreeDAO.update(degree);
                setFlash(request, "Cập nhật bằng cấp thành công.", null);
                response.sendRedirect(buildListUrl(request, degree.getTeacherID()));
            } else if ("delete".equalsIgnoreCase(action)) {
                int degreeId = parseIntOrZero(request.getParameter("id"));
                degreeDAO.delete(degreeId);
                setFlash(request, "Đã xóa bằng cấp.", null);
                response.sendRedirect(buildListUrl(request, degree.getTeacherID()));
            } else {
                request.setAttribute("flashError", "Hành động không hợp lệ: " + action);
                request.setAttribute("teachers", teacherDAO.getAll());
                request.setAttribute("selectedTeacherId", degree.getTeacherID());
                request.setAttribute("degree", degree);
                request.setAttribute("mode", "add");
                request.getRequestDispatcher("/WEB-INF/views/admin/teacher/degree/form.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("flashError", "Không lưu được bằng cấp: " + e.getMessage());
            request.setAttribute("teachers", teacherDAO.getAll());
            request.setAttribute("selectedTeacherId", parseIntOrZero(request.getParameter("teacherID")));
            request.setAttribute("degree", buildDegreeFromRequest(request));
            request.setAttribute("mode", "add");
            request.getRequestDispatcher("/WEB-INF/views/admin/teacher/degree/form.jsp").forward(request, response);
        }
    }

    private void showList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int teacherID = parseIntOrZero(request.getParameter("teacherID"));
        List<Teacher> teachers = teacherDAO.getAll();
        request.setAttribute("teachers", teachers);
        request.setAttribute("selectedTeacherId", teacherID);
        request.setAttribute("selectedTeacher", teacherID > 0 ? teacherDAO.getById(teacherID) : null);
        request.setAttribute("degrees", teacherID > 0 ? degreeDAO.getAllByTeacher(teacherID) : degreeDAO.getAll());
        request.getRequestDispatcher("/WEB-INF/views/admin/teacher/degree/list.jsp").forward(request, response);
    }

    private void showForm(HttpServletRequest request, HttpServletResponse response, TeacherDegree degree, String mode)
            throws ServletException, IOException {
        int teacherID = parseIntOrZero(request.getParameter("teacherID"));
        if (degree != null && teacherID <= 0) {
            teacherID = degree.getTeacherID();
        }
        request.setAttribute("teachers", teacherDAO.getAll());
        request.setAttribute("selectedTeacherId", teacherID);
        request.setAttribute("degree", degree);
        request.setAttribute("mode", mode);
        request.getRequestDispatcher("/WEB-INF/views/admin/teacher/degree/form.jsp").forward(request, response);
    }

    private TeacherDegree buildDegreeFromRequest(HttpServletRequest request) {
        TeacherDegree degree = new TeacherDegree();
        degree.setTeacherID(parseIntOrZero(request.getParameter("teacherID")));
        degree.setDegreeName(request.getParameter("degreeName"));
        degree.setMajor(request.getParameter("major"));
        degree.setGraduationYear(parseIntegerOrNull(request.getParameter("graduationYear")));
        degree.setGraduationSchool(request.getParameter("graduationSchool"));
        degree.setAttachmentPath(request.getParameter("attachmentPath"));
        return degree;
    }

    private int parseIntOrZero(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }

    private Integer parseIntegerOrNull(String value) {
        try {
            if (value == null || value.trim().isEmpty()) {
                return null;
            }
            return Integer.valueOf(value.trim());
        } catch (Exception e) {
            return null;
        }
    }

    private String buildListUrl(HttpServletRequest request, int teacherID) {
        String url = request.getContextPath() + "/admin/teacher-degree";
        if (teacherID > 0) {
            url += "?teacherID=" + teacherID;
        }
        return url;
    }
}
