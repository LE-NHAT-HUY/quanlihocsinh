package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.dao.RoleDAO;
import com.quanlihocsinh.dao.UserDAO;
import com.quanlihocsinh.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/editUser")
public class EditUserController extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/admin/users");
            return;
        }

        try {
            int userId = Integer.parseInt(idStr);
            User user = userDAO.getById(userId);
            if (user == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/users?msg=not_found");
                return;
            }

            req.setAttribute("user", user);
            req.getRequestDispatcher("/WEB-INF/views/admin/editUser.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String idStr = req.getParameter("id");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String fullName = req.getParameter("fullname");

        try {
            if (idStr == null || idStr.trim().isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/admin/users?msg=invalid_id");
                return;
            }

            int userId = Integer.parseInt(idStr);
            User currentUser = userDAO.getById(userId);
            if (currentUser == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/users?msg=not_found");
                return;
            }
            int roleId = currentUser.getRoleId();

            if (username == null || username.trim().isEmpty() || fullName == null || fullName.trim().isEmpty()) {
                req.setAttribute("error", "Vui lòng nhập đầy đủ tên đăng nhập và họ tên");
                req.setAttribute("user", userDAO.getById(userId));
                req.getRequestDispatcher("/WEB-INF/views/admin/editUser.jsp").forward(req, resp);
                return;
            }

            if (userDAO.isUsernameTakenExceptUserId(username.trim(), userId)) {
                req.setAttribute("error", "Tên đăng nhập đã tồn tại");
                req.setAttribute("user", userDAO.getById(userId));
                req.getRequestDispatcher("/WEB-INF/views/admin/editUser.jsp").forward(req, resp);
                return;
            }

            boolean ok = userDAO.updateAccount(userId, username.trim(), password, roleId, fullName.trim());
            if (ok) {
                resp.sendRedirect(req.getContextPath() + "/admin/users?msg=updated");
            } else {
                req.setAttribute("error", "Không thể cập nhật tài khoản");
                req.setAttribute("user", userDAO.getById(userId));
                req.getRequestDispatcher("/WEB-INF/views/admin/editUser.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi cập nhật tài khoản: " + e.getMessage());
            try {
                int userId = Integer.parseInt(idStr);
                req.setAttribute("user", userDAO.getById(userId));
            } catch (Exception ignore) {
            }
            req.getRequestDispatcher("/WEB-INF/views/admin/editUser.jsp").forward(req, resp);
        }
    }
}