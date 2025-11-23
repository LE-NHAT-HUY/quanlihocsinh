package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.dao.GradeDAO;
import com.quanlihocsinh.model.Grade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/grade/*")
public class GradeController extends HttpServlet {
    private GradeDAO gradeDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        gradeDAO = new GradeDAO(); // DAO tự tạo Connection bên trong
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
                    showAddForm(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/delete":
                    deleteGrade(request, response);
                    break;
                default:
                    listGrades(request, response);
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
                    insertGrade(request, response);
                    break;
                case "/edit":
                    updateGrade(request, response);
                    break;
                case "/toggleStatus":
                    toggleStatus(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/admin/grade/list");
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    // ---------------------- METHODS ----------------------

    private void listGrades(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Grade> list = gradeDAO.getAll();
        request.setAttribute("grades", list);
        request.getRequestDispatcher("/WEB-INF/views/admin/grade/list.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/admin/grade/add.jsp").forward(request, response);
    }

    private void insertGrade(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String name = request.getParameter("gradeName");
        String desc = request.getParameter("description");
        boolean isActive = request.getParameter("isActive") != null;

        Grade g = new Grade();
        g.setGradeName(name);
        g.setDescription(desc);
        g.setIsActive(isActive);

        gradeDAO.add(g);
        response.sendRedirect(request.getContextPath() + "/admin/grade/list");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Grade g = gradeDAO.getById(id); // gọi đúng tên phương thức
        request.setAttribute("grade", g);
        request.getRequestDispatcher("/WEB-INF/views/admin/grade/edit.jsp").forward(request, response);
    }

    private void updateGrade(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("gradeID"));
        String name = request.getParameter("gradeName");
        String desc = request.getParameter("description");
        boolean isActive = request.getParameter("isActive") != null;

        Grade g = new Grade();
        g.setGradeID(id);
        g.setGradeName(name);
        g.setDescription(desc);
        g.setIsActive(isActive);

        gradeDAO.update(g);
        response.sendRedirect(request.getContextPath() + "/admin/grade/list");
    }

    private void deleteGrade(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        gradeDAO.delete(id);
        response.sendRedirect(request.getContextPath() + "/admin/grade/list");
    }

    private void toggleStatus(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Grade g = gradeDAO.getById(id);
        g.setIsActive(!g.isIsActive()); // đổi trạng thái
        gradeDAO.update(g);
        response.sendRedirect(request.getContextPath() + "/admin/grade/list");
    }
}
