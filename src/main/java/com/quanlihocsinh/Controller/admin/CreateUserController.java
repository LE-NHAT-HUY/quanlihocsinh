package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.dao.UserDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/createUser")
public class CreateUserController extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/views/admin/createUser.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String fullName = req.getParameter("fullname");
        int roleId = Integer.parseInt(req.getParameter("roleId"));

        try {

            userDAO.createAccount(username, password, roleId, fullName);

            resp.sendRedirect(req.getContextPath() + "/admin/users?msg=success");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi tạo tài khoản: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/admin/createUser.jsp").forward(req, resp);
        }
    }
}