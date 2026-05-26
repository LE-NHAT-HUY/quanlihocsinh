package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.dao.TblClassDAO;
import com.quanlihocsinh.dao.CohortDAO;
import com.quanlihocsinh.model.tblClass;
import com.quanlihocsinh.model.Cohort;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/class/*")
public class ClassController extends HttpServlet {
    private TblClassDAO dao = new TblClassDAO();
    private CohortDAO cohortDAO = new CohortDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null)
            action = "list";

        switch (action) {
            case "add":
                List<Cohort> cohorts = cohortDAO.getAll();
                request.setAttribute("cohorts", cohorts);
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
                List<Cohort> cohortList = cohortDAO.getAll();

                request.setAttribute("classes", list);
                request.setAttribute("cohortList", cohortList);

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
                addMultipleClasses(request, response);
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
            case "toggleStatus":
                toggleStatus(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/admin/class?action=list");
        }
    }

    private void addMultipleClasses(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String className = request.getParameter("className");
        String cohortIdStr = request.getParameter("cohortID");
        String maxStudentsStr = request.getParameter("maxStudents");

        if (cohortIdStr == null || cohortIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/class?action=list");
            return;
        }

        int cohortId = Integer.parseInt(cohortIdStr);
        int maxStudents = (maxStudentsStr != null && !maxStudentsStr.isEmpty()) ? Integer.parseInt(maxStudentsStr) : 40;

        Cohort cohort = cohortDAO.getById(cohortId);
        if (cohort == null) {
            response.sendRedirect(request.getContextPath() + "/admin/class?action=list");
            return;
        }

        int startYear = cohort.getStartYear();

        int[] gradeIds = { 6, 7, 8, 9 };

        for (int i = 0; i < 4; i++) {
            tblClass newClass = new tblClass();
            newClass.setClassName(className);
            newClass.setGradeID(gradeIds[i]);
            newClass.setCohortID(cohortId);
            newClass.setMaxStudents(maxStudents);
            newClass.setCurrentStudents(0);

            int yearStart = startYear + i;
            int yearEnd = yearStart + 1;
            String schoolYear = yearStart + "-" + yearEnd;
            newClass.setSchoolYear(schoolYear);

            newClass.setActive(true);

            dao.add(newClass);
        }

        response.sendRedirect(request.getContextPath() + "/admin/class?action=list");
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

    private void toggleStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idToggle = request.getParameter("id");
        String isActive = request.getParameter("isActive");

        if (idToggle != null && !idToggle.isEmpty()) {
            boolean status = "on".equals(isActive) || "true".equals(isActive);
            dao.toggleStatus(Integer.parseInt(idToggle), status);
        }

        response.sendRedirect(request.getContextPath() + "/admin/class?action=list");
    }
}
