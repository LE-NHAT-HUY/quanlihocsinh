package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.dao.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/teachersubject/*")
public class ATSubjectController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            TeacherSubjectDAO tsDao = new TeacherSubjectDAO();
            TeacherDAO teacherDAO = new TeacherDAO();
            SubjectDAO subjectDAO = new SubjectDAO();

            req.setAttribute("teachers", teacherDAO.findAll());
            req.setAttribute("subjects", subjectDAO.findAll());
            req.setAttribute("mappings", tsDao.findAll());

            req.getRequestDispatcher("/WEB-INF/views/admin/teachersubject/list.jsp")
                    .forward(req, resp);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        try {
            TeacherSubjectDAO tsDao = new TeacherSubjectDAO();

            if ("assign".equals(action)) {
                int teacherID = Integer.parseInt(req.getParameter("teacherID"));
                int subjectID = Integer.parseInt(req.getParameter("subjectID"));
                String adminID = (String) req.getSession().getAttribute("adminID");

                tsDao.assign(teacherID, subjectID, adminID);

            } else if ("unassign".equals(action)) {
                int teacherID = Integer.parseInt(req.getParameter("teacherID"));
                int subjectID = Integer.parseInt(req.getParameter("subjectID"));

                tsDao.unassign(teacherID, subjectID);
            }

            resp.sendRedirect(req.getContextPath() + "/admin/teachersubject/list.jsp");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
