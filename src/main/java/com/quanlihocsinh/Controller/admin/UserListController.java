package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.dao.UserDAO;
import com.quanlihocsinh.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/users")
public class UserListController extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Kiểm tra session (Admin mới được vào)
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // (Tuỳ chọn: Kiểm tra quyền Admin roleId == 1)
        User currentUser = (User) session.getAttribute("user");
        if (currentUser.getRoleId() != 1) {
            resp.sendRedirect(req.getContextPath() + "/access-denied");
            return;
        }

        // 2. Lấy danh sách người dùng từ Database
        List<User> list = userDAO.getAllUsers();

        // 3. Gửi dữ liệu sang JSP
        req.setAttribute("users", list);

        // 4. Forward về trang JSP bạn vừa tạo
        req.getRequestDispatcher("/WEB-INF/views/admin/users.jsp").forward(req, resp);
    }
}