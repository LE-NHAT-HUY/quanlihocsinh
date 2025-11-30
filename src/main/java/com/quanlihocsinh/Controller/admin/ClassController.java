package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.dao.TblClassDAO;
import com.quanlihocsinh.model.tblClass;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/class/*")
public class ClassController extends HttpServlet {
    private TblClassDAO dao = new TblClassDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null)
            action = "list";

        switch (action) {
            case "add":
                request.getRequestDispatcher("/WEB-INF/views/admin/class/add.jsp").forward(request, response);
                break;
            case "edit":
                String idEdit = request.getParameter("id");
                if (idEdit != null && !idEdit.isEmpty()) {
                    int id = Integer.parseInt(idEdit);
                    tblClass c = dao.getById(id);
                    request.setAttribute("classItem", c);
                    request.getRequestDispatcher("/WEB-INF/views/admin/class/edit.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/class?action=list");
                }
                break;
            case "delete":
                String idDel = request.getParameter("id");
                if (idDel != null && !idDel.isEmpty()) {
                    dao.delete(Integer.parseInt(idDel));
                }
                response.sendRedirect(request.getContextPath() + "/admin/class?action=list");
                break;
            case "toggleStatus":
                String idToggle = request.getParameter("id");
                String isActive = request.getParameter("isActive");
                if (idToggle != null && !idToggle.isEmpty()) {
                    boolean status = "on".equals(isActive) || "true".equals(isActive);
                    dao.toggleStatus(Integer.parseInt(idToggle), status);
                }
                response.sendRedirect(request.getContextPath() + "/admin/class?action=list");
                break;
            case "list":
            default:
                List<tblClass> list = dao.getAll();
                request.setAttribute("classes", list);
                request.getRequestDispatcher("/WEB-INF/views/admin/class/list.jsp").forward(request, response);
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
                tblClass newC = extractClassFromRequest(request);
                dao.add(newC);
                response.sendRedirect(request.getContextPath() + "/admin/class?action=list");
                break;
            case "edit":
                tblClass editC = extractClassFromRequest(request);
                String id = request.getParameter("id");
                if (id != null && !id.isEmpty()) {
                    editC.setClassID(Integer.parseInt(id));
                    dao.update(editC);
                }
                response.sendRedirect(request.getContextPath() + "/admin/class?action=list");
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/admin/class?action=list");
        }
    }

    private tblClass extractClassFromRequest(HttpServletRequest request) {
        tblClass c = new tblClass();
        c.setClassName(request.getParameter("className"));

        String grade = request.getParameter("gradeID");
        c.setGradeID(grade != null && !grade.isEmpty() ? Integer.parseInt(grade) : 0);

        String cohort = request.getParameter("cohortID");
        c.setCohortID(cohort != null && !cohort.isEmpty() ? Integer.parseInt(cohort) : null);

        String maxs = request.getParameter("maxStudents");
        c.setMaxStudents(maxs != null && !maxs.isEmpty() ? Integer.parseInt(maxs) : 0);

        String currs = request.getParameter("currentStudents");
        c.setCurrentStudents(currs != null && !currs.isEmpty() ? Integer.parseInt(currs) : 0);

        c.setSchoolYear(request.getParameter("schoolYear"));
        c.setActive(request.getParameter("isActive") != null);

        return c;
    }
}