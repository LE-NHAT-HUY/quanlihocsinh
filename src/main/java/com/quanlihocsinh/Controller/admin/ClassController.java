package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.dao.ClassDAO;
import com.quanlihocsinh.model.tblClass;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/class/*")
public class ClassController extends HttpServlet {
    private ClassDAO classDAO;

    @Override
    public void init() throws ServletException {
        classDAO = new ClassDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null)
            action = "/list";

        try {
            switch (action) {
                case "/add":
                    request.getRequestDispatcher("/WEB-INF/views/admin/class/add.jsp").forward(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/delete":
                    deleteClass(request, response);
                    break;
                default:
                    listClasses(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null)
            action = "/list";

        try {
            switch (action) {
                case "/add":
                    addClass(request, response);
                    break;
                case "/edit":
                    updateClass(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/admin/class/list");
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    // LIST
    private void listClasses(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<tblClass> list = classDAO.getAll();
        request.setAttribute("classes", list);
        request.getRequestDispatcher("/WEB-INF/views/admin/class/list.jsp").forward(request, response);
    }

    // SHOW EDIT FORM
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        tblClass c = classDAO.getById(id);
        request.setAttribute("classObj", c);
        request.getRequestDispatcher("/WEB-INF/views/admin/class/edit.jsp").forward(request, response);
    }

    // ADD
    private void addClass(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        tblClass c = new tblClass();
        c.setClassName(request.getParameter("className"));
        c.setGradeID(Integer.parseInt(request.getParameter("gradeID")));
        String cohortParam = request.getParameter("cohortID");
        c.setCohortID((cohortParam != null && !cohortParam.isEmpty()) ? Integer.parseInt(cohortParam) : null);
        c.setMaxStudents(Integer.parseInt(request.getParameter("maxStudents")));
        c.setCurrentStudents(Integer.parseInt(request.getParameter("currentStudents")));
        c.setSchoolYear(request.getParameter("schoolYear"));
        c.setActive(request.getParameter("isActive") != null);

        classDAO.add(c);
        response.sendRedirect(request.getContextPath() + "/admin/class/list");
    }

    // UPDATE
    private void updateClass(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        tblClass c = new tblClass();
        c.setClassID(Integer.parseInt(request.getParameter("classID")));
        c.setClassName(request.getParameter("className"));
        c.setGradeID(Integer.parseInt(request.getParameter("gradeID")));
        String cohortParam = request.getParameter("cohortID");
        c.setCohortID((cohortParam != null && !cohortParam.isEmpty()) ? Integer.parseInt(cohortParam) : null);
        c.setMaxStudents(Integer.parseInt(request.getParameter("maxStudents")));
        c.setCurrentStudents(Integer.parseInt(request.getParameter("currentStudents")));
        c.setSchoolYear(request.getParameter("schoolYear"));
        c.setActive(request.getParameter("isActive") != null);

        classDAO.update(c);
        response.sendRedirect(request.getContextPath() + "/admin/class/list");
    }

    // DELETE
    private void deleteClass(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        classDAO.delete(id);
        response.sendRedirect(request.getContextPath() + "/admin/class/list");
    }
}
