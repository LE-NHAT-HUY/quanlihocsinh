package com.quanlihocsinh.Controller;

import com.quanlihocsinh.dao.UserDAO;
import com.quanlihocsinh.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username").trim();
        String password = req.getParameter("password");

        try {
            User user = userDAO.checkLogin(username, password);
            if (user == null) {
                req.setAttribute("error", "Sai tài khoản hoặc mật khẩu.");
                req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
                return;
            }

            HttpSession session = req.getSession();
            session.setAttribute("user", user);

            // Điều hướng theo role_id
            switch (user.getRoleId()) {
                case 1:
                    resp.sendRedirect(req.getContextPath() + "/admin/home");
                    break;
                case 2:
                    resp.sendRedirect(req.getContextPath() + "/teacher/home");
                    break;
                case 3:
                    resp.sendRedirect(req.getContextPath() + "/student/home");
                    break;
                default:
                    resp.sendRedirect(req.getContextPath() + "/");
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }

}
