package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.dao.TeacherSubjectDAO;
import com.quanlihocsinh.model.User;
import com.quanlihocsinh.util.DBUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/teacher/*")
public class TeacherSubjectsController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");

        // chỉ cho giáo viên
        if (user.getRoleId() != 2) {
            resp.sendRedirect(req.getContextPath() + "/access-denied");
            return;
        }

        int teacherID = user.getPersonId(); // ✅ ĐÚNG

        try {
            TeacherSubjectDAO tsDao = new TeacherSubjectDAO();
            req.setAttribute("subjects",
                    tsDao.findSubjectsByTeacher(teacherID));

            req.getRequestDispatcher("/WEB-INF/views/teacher/subject.jsp")
                    .forward(req, resp);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
