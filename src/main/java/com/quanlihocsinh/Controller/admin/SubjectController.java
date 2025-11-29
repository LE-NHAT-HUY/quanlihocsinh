package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.dao.SubjectDAO;
import com.quanlihocsinh.model.Subject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SubjectController", urlPatterns = { "/admin/subject/*" })
public class SubjectController extends HttpServlet {
    private SubjectDAO dao = new SubjectDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        if (action == null)
            action = "list";

        switch (action) {
            case "add":
                showAddForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteSubject(request, response);
                break;
            default:
                listSubject(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        if (action == null)
            action = "list";

        switch (action) {
            case "add":
                Subject sAdd = getSubjectFromRequest(request);
                dao.addSubject(sAdd);
                response.sendRedirect(request.getContextPath() + "/admin/subject");
                break;
            case "edit":
                Subject sEdit = getSubjectFromRequest(request);
                sEdit.setSubjectID(Integer.parseInt(request.getParameter("subjectID")));
                dao.updateSubject(sEdit);
                response.sendRedirect(request.getContextPath() + "/admin/subject");
                break;
            case "toggleStatus":
                int id = Integer.parseInt(request.getParameter("id"));
                boolean status = request.getParameter("isActive") != null;
                dao.toggleStatus(id, status);
                response.sendRedirect(request.getContextPath() + "/admin/subject");
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/admin/subject");
        }
    }

    private void listSubject(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Subject> subjects = dao.getAllSubjects();
        request.setAttribute("subjects", subjects);
        request.getRequestDispatcher("/WEB-INF/views/admin/subject/list.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/admin/subject/add.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Subject s = dao.getSubjectById(id);
        request.setAttribute("subject", s);
        request.getRequestDispatcher("/WEB-INF/views/admin/subject/edit.jsp").forward(request, response);
    }

    private void deleteSubject(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        dao.deleteSubject(id);
        response.sendRedirect(request.getContextPath() + "/admin/subject");
    }

    private Subject getSubjectFromRequest(HttpServletRequest request) {
        Subject s = new Subject();
        s.setSubjectName(request.getParameter("subjectName"));
        s.setNumberOfLesson(parseIntSafe(request.getParameter("numberOfLesson")));
        s.setSemester(request.getParameter("semester"));
        s.setDepartmentID(parseIntSafe(request.getParameter("departmentID")));
        s.setIsActive(request.getParameter("isActive") != null);
        return s;
    }

    private Integer parseIntSafe(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return null;
        }
    }
}
