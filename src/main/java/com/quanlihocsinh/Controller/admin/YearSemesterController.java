package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.dao.YearSemesterDAO;
import com.quanlihocsinh.model.YearSemester;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/yearsemester/*")
public class YearSemesterController extends HttpServlet {
    private YearSemesterDAO ysdao;

    @Override
    public void init() throws ServletException {
        super.init();
        ysdao = new YearSemesterDAO();
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
                    deleteYearSemester(request, response);
                    break;
                default:
                    listYearSemester(request, response);
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
                    insertYearSemester(request, response);
                    break;
                case "/edit":
                    updateYearSemester(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/admin/yearsemester/list");
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listYearSemester(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<YearSemester> list = ysdao.getAll();
        request.setAttribute("yearsemesters", list);
        request.getRequestDispatcher("/WEB-INF/views/admin/yearsemester/list.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/admin/yearsemester/add.jsp").forward(request, response);
    }

    private void insertYearSemester(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String sem = request.getParameter("semesterName");
        String year = request.getParameter("schoolYear");
        boolean active = request.getParameter("isActive") != null;

        YearSemester ys = new YearSemester();
        ys.setSemesterName(sem);
        ys.setSchoolYear(year);
        ys.setIsActive(active);

        ysdao.add(ys);
        response.sendRedirect(request.getContextPath() + "/admin/yearsemester/list");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        YearSemester ys = ysdao.getById(id);
        request.setAttribute("yearsemester", ys);
        request.getRequestDispatcher("/WEB-INF/views/admin/yearsemester/edit.jsp").forward(request, response);
    }

    private void updateYearSemester(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("yearSemesterID"));
        String sem = request.getParameter("semesterName");
        String year = request.getParameter("schoolYear");
        boolean active = request.getParameter("isActive") != null;

        YearSemester ys = new YearSemester();
        ys.setYearSemesterID(id);
        ys.setSemesterName(sem);
        ys.setSchoolYear(year);
        ys.setIsActive(active);

        ysdao.update(ys);
        response.sendRedirect(request.getContextPath() + "/admin/yearsemester/list");
    }

    private void deleteYearSemester(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        ysdao.delete(id);
        response.sendRedirect(request.getContextPath() + "/admin/yearsemester/list");
    }
}
