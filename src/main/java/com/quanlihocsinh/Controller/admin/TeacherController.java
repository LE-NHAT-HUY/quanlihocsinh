package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.dao.TeacherDAO;
import com.quanlihocsinh.model.Teacher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/admin/teacher/*")
public class TeacherController extends HttpServlet {

    private TeacherDAO teacherDAO = new TeacherDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null)
            action = "list";

        switch (action) {
            case "add":
                request.getRequestDispatcher("/WEB-INF/views/admin/teacher/add.jsp")
                        .forward(request, response);
                break;

            case "edit":
                int id = Integer.parseInt(request.getParameter("id"));
                Teacher teacher = teacherDAO.getById(id);
                request.setAttribute("teacher", teacher);
                request.getRequestDispatcher("/WEB-INF/views/admin/teacher/edit.jsp")
                        .forward(request, response);
                break;

            case "delete":
                int idDel = Integer.parseInt(request.getParameter("id"));
                teacherDAO.delete(idDel);
                response.sendRedirect(request.getContextPath() + "/admin/teacher");
                break;

            case "toggleStatus":
                int idToggle = Integer.parseInt(request.getParameter("id"));
                String isActiveParam = request.getParameter("isActive");
                boolean newStatus = "on".equals(isActiveParam) || "true".equals(isActiveParam);
                teacherDAO.updateStatus(idToggle, newStatus);
                response.sendRedirect(request.getContextPath() + "/admin/teacher");
                break;

            default: // LIST
                List<Teacher> teachers = teacherDAO.getAll();
                request.setAttribute("teachers", teachers);
                request.getRequestDispatcher("/WEB-INF/views/admin/teacher/list.jsp")
                        .forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            Teacher t = getTeacherFromRequest(request);
            teacherDAO.add(t);
            response.sendRedirect(request.getContextPath() + "/admin/teacher");
        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Teacher t = getTeacherFromRequest(request);
            t.setId(id);
            teacherDAO.update(t);
            response.sendRedirect(request.getContextPath() + "/admin/teacher");
        }
    }

    private Teacher getTeacherFromRequest(HttpServletRequest request) {
        Teacher t = new Teacher();

        t.setTeacherID(request.getParameter("teacherID"));
        t.setFullName(request.getParameter("fullName"));

        String birthStr = request.getParameter("birth");
        if (birthStr != null && !birthStr.isEmpty()) {
            t.setBirth(Date.valueOf(birthStr));
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
        t.setIsActive(request.getParameter("isActive") != null);
        t.setDepartmentID(parseIntOrZero(request.getParameter("departmentID")));
        t.setHamlet(parseIntOrZero(request.getParameter("hamlet")));
        t.setCommune(request.getParameter("commune"));
        t.setProvince(request.getParameter("province"));
        t.setNationality(request.getParameter("nationality"));
        t.setImages(request.getParameter("images"));

        return t;
    }

    private int parseIntOrZero(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }
}
