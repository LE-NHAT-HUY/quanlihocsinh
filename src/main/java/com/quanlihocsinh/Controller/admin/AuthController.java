package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.dao.UserDAO;
import com.quanlihocsinh.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet({ "/login", "/logout", "/register" })
public class AuthController extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        String uri = req.getRequestURI();

        if (uri.endsWith("login"))
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        else if (uri.endsWith("register"))
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
        else if (uri.endsWith("logout")) {
            req.getSession().invalidate();
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        String uri = req.getRequestURI();

        if (uri.endsWith("login")) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            User u = userDAO.login(username, password);

            if (u != null) {
                req.getSession().setAttribute("user", u);
                resp.sendRedirect(req.getContextPath() + "/admin/home");
            } else {
                req.setAttribute("error", "Sai tài khoản hoặc mật khẩu");
                req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            }

        } else if (uri.endsWith("register")) {
            User u = new User();
            u.setUsername(req.getParameter("username"));
            u.setPasswordHash(req.getParameter("password"));
            u.setFullName(req.getParameter("fullName"));

            if (userDAO.register(u)) {
                resp.sendRedirect(req.getContextPath() + "/login");
            } else {
                req.setAttribute("error", "Đăng ký thất bại");
                req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
            }
        }
    }
}
