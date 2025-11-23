package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.dao.CohortDAO;
import com.quanlihocsinh.model.Cohort;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/cohort/*")
public class CohortController extends HttpServlet {

    private CohortDAO dao = new CohortDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getPathInfo();
        if (action == null)
            action = "/list";

        switch (action) {
            case "/add":
                request.getRequestDispatcher("/WEB-INF/views/admin/cohort/add.jsp").forward(request, response);
                break;

            case "/edit":
                int editId = Integer.parseInt(request.getParameter("id"));
                Cohort c = dao.getById(editId);
                request.setAttribute("cohort", c);
                request.getRequestDispatcher("/WEB-INF/views/admin/cohort/edit.jsp").forward(request, response);
                break;

            case "/delete":
                int delId = Integer.parseInt(request.getParameter("id"));
                dao.delete(delId);
                response.sendRedirect(request.getContextPath() + "/admin/cohort/list");
                break;

            default: // /list
                List<Cohort> list = dao.getAll();
                request.setAttribute("cohorts", list); // trùng với JSP
                request.getRequestDispatcher("/WEB-INF/views/admin/cohort/index.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getPathInfo();
        if (action == null)
            action = "/list";

        switch (action) {
            case "/add":
                addCohort(request, response);
                break;
            case "/edit":
                updateCohort(request, response); // ⚠ lỗi nếu phương thức chưa tồn tại
                break;
            case "/toggleStatus":
                toggleStatus(request, response); // ⚠ lỗi nếu phương thức chưa tồn tại
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/admin/cohort/list");
                break;
        }
    }

    // --- Hàm xử lý thêm mới ---
    private void addCohort(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Cohort c = new Cohort();
        c.setCohortName(Integer.parseInt(request.getParameter("cohortName")));
        c.setStartYear(Integer.parseInt(request.getParameter("startYear")));
        c.setEndYear(Integer.parseInt(request.getParameter("endYear")));
        c.setIsActive(request.getParameter("isActive") != null);

        dao.add(c);
        response.sendRedirect(request.getContextPath() + "/admin/cohort/list");
    }

    // --- Hàm xử lý cập nhật ---
    private void updateCohort(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Cohort c = new Cohort();
        c.setCohortID(Integer.parseInt(request.getParameter("cohortID")));
        c.setCohortName(Integer.parseInt(request.getParameter("cohortName")));
        c.setStartYear(Integer.parseInt(request.getParameter("startYear")));
        c.setEndYear(Integer.parseInt(request.getParameter("endYear")));
        c.setIsActive(request.getParameter("isActive") != null);

        dao.update(c);
        response.sendRedirect(request.getContextPath() + "/admin/cohort/list");
    }

    // --- Hàm xử lý toggle isActive ---
    private void toggleStatus(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean isActive = request.getParameter("isActive") != null;

        Cohort c = dao.getById(id);
        if (c != null) {
            c.setIsActive(isActive);
            dao.update(c);
        }
        response.sendRedirect(request.getContextPath() + "/admin/cohort/list");
    }
}
