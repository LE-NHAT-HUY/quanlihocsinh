package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.dao.TeacherDAO;
import com.quanlihocsinh.model.Teacher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/admin/teacher")
public class TeacherController extends HttpServlet {
    private TeacherDAO dao = new TeacherDAO();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null)
            action = "list";

        switch (action) {
            case "add":
                request.getRequestDispatcher("/WEB-INF/views/admin/teacher/add.jsp").forward(request, response);
                break;
            case "edit":
                int idEdit = Integer.parseInt(request.getParameter("id"));
                Teacher tEdit = dao.getById(idEdit);
                request.setAttribute("teacher", tEdit);
                request.getRequestDispatcher("/WEB-INF/views/admin/teacher/edit.jsp").forward(request, response);
                break;
            case "delete":
                int idDelete = Integer.parseInt(request.getParameter("id"));
                dao.delete(idDelete);
                response.sendRedirect(request.getContextPath() + "/admin/teacher?action=list");
                break;
            case "toggleStatus":
                int idToggle = Integer.parseInt(request.getParameter("id"));
                boolean status = "on".equals(request.getParameter("isActive"))
                        || "true".equals(request.getParameter("isActive"));
                dao.toggleStatus(idToggle, status);
                response.sendRedirect(request.getContextPath() + "/admin/teacher?action=list");
                break;
            case "list":
            default:
                List<Teacher> list = dao.getAll();
                request.setAttribute("teachers", list);
                request.getRequestDispatcher("/WEB-INF/views/admin/teacher/list.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null)
            action = "list";

        switch (action) {
            case "add":
                Teacher tAdd = extractTeacherFromRequest(request);
                dao.add(tAdd);
                response.sendRedirect(request.getContextPath() + "/admin/teacher?action=list");
                break;
            case "edit":
                Teacher tEdit = extractTeacherFromRequest(request);
                tEdit.setId(Integer.parseInt(request.getParameter("id")));
                dao.update(tEdit);
                response.sendRedirect(request.getContextPath() + "/admin/teacher?action=list");
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/admin/teacher?action=list");
                break;
        }
    }

    // Hàm hỗ trợ lấy dữ liệu từ form
    private Teacher extractTeacherFromRequest(HttpServletRequest request) {
        Teacher t = new Teacher();
        t.setTeacherID(request.getParameter("teacherID"));
        t.setFullName(request.getParameter("fullName"));

        String birthStr = request.getParameter("birth");
        if (birthStr != null && !birthStr.isEmpty()) {
            try {
                Date birth = sdf.parse(birthStr);
                t.setBirth(birth);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        t.setGender(request.getParameter("gender"));
        t.setAddress(request.getParameter("address"));
        t.setStatusTeacher(request.getParameter("statusTeacher"));
        t.setCccd(request.getParameter("cccd"));
        t.setNation(request.getParameter("nation"));
        t.setReligion(request.getParameter("religion"));
        t.setGroupDV(request.getParameter("groupDV"));
        t.setNumberPhone(request.getParameter("numberPhone"));
        t.setNumberBHXH(request.getParameter("numberBHXH"));
        t.setIsActive("on".equals(request.getParameter("isActive")) || "true".equals(request.getParameter("isActive")));

        String depID = request.getParameter("departmentID");
        t.setDepartmentID(depID != null && !depID.isEmpty() ? Integer.parseInt(depID) : null);

        String hamlet = request.getParameter("hamlet");
        t.setHamlet(hamlet != null && !hamlet.isEmpty() ? Integer.parseInt(hamlet) : null);

        t.setCommune(request.getParameter("commune"));
        t.setProvince(request.getParameter("province"));
        t.setNationality(request.getParameter("nationality"));
        t.setImages(request.getParameter("images"));

        return t;
    }
}
