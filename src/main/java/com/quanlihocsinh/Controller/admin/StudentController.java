package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.dao.StudentDAO;
import com.quanlihocsinh.model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/admin/student")
public class StudentController extends HttpServlet {
    private StudentDAO dao = new StudentDAO();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null)
            action = "list";

        switch (action) {
            case "add":
                request.getRequestDispatcher("/WEB-INF/views/admin/student/add.jsp").forward(request, response);
                break;
            case "edit":
                String idEdit = request.getParameter("id");
                if (idEdit != null && !idEdit.isEmpty()) {
                    Student s = dao.getById(Integer.parseInt(idEdit));
                    request.setAttribute("student", s);
                    request.getRequestDispatcher("/WEB-INF/views/admin/student/edit.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/student?action=list");
                }
                break;
            case "delete":
                String idDel = request.getParameter("id");
                if (idDel != null && !idDel.isEmpty()) {
                    dao.delete(Integer.parseInt(idDel));
                }
                response.sendRedirect(request.getContextPath() + "/admin/student?action=list");
                break;
            case "toggleStatus":
                String idToggle = request.getParameter("id");
                String isActive = request.getParameter("isActive");
                if (idToggle != null && !idToggle.isEmpty()) {
                    boolean status = "on".equals(isActive) || "true".equals(isActive);
                    dao.toggleStatus(Integer.parseInt(idToggle), status);
                }
                response.sendRedirect(request.getContextPath() + "/admin/student?action=list");
                break;
            case "list":
            default:
                List<Student> list = dao.getAll();
                request.setAttribute("students", list);
                request.getRequestDispatcher("/WEB-INF/views/admin/student/list.jsp").forward(request, response);
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
                Student sAdd = extractStudentFromRequest(request);
                dao.add(sAdd);
                response.sendRedirect(request.getContextPath() + "/admin/student?action=list");
                break;
            case "edit":
                Student sEdit = extractStudentFromRequest(request);
                String id = request.getParameter("id");
                if (id != null && !id.isEmpty()) {
                    sEdit.setId(Integer.parseInt(id));
                    dao.update(sEdit);
                }
                response.sendRedirect(request.getContextPath() + "/admin/student?action=list");
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/admin/student?action=list");
                break;
        }
    }

    private Student extractStudentFromRequest(HttpServletRequest request) {
        Student s = new Student();
        s.setStudentID(request.getParameter("studentID"));
        s.setFullName(request.getParameter("fullName"));

        String birthStr = request.getParameter("birth");
        if (birthStr != null && !birthStr.isEmpty()) {
            try {
                Date d = sdf.parse(birthStr);
                s.setBirth(d);
            } catch (ParseException e) {
                s.setBirth(null);
            }
        }

        s.setGender(request.getParameter("gender"));
        s.setAddress(request.getParameter("address"));
        s.setNation(request.getParameter("nation"));
        s.setReligion(request.getParameter("religion"));
        s.setStatusStudent(request.getParameter("statusStudent"));
        s.setNumberPhone(request.getParameter("numberPhone"));
        s.setIsActive("on".equals(request.getParameter("isActive")) || "true".equals(request.getParameter("isActive")));
        s.setImages(request.getParameter("images"));
        s.setHamlet(request.getParameter("hamlet"));
        s.setCommune(request.getParameter("commune"));
        s.setProvince(request.getParameter("province"));
        s.setNationality(request.getParameter("nationality"));
        return s;
    }
}
