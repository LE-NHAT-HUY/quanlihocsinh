package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.dao.UserDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/createUser")
public class CreateUserContrller extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Chỉ cần forward sang trang nhập liệu, không cần load danh sách Person nữa
        req.getRequestDispatcher("/WEB-INF/views/admin/createUser.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8"); // Đảm bảo tiếng Việt

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String fullName = req.getParameter("fullname");
        int roleId = Integer.parseInt(req.getParameter("roleId"));

        try {
            // Gọi hàm tạo tài khoản chuẩn mới viết
            userDAO.createAccount(username, password, roleId, fullName);

            // Thành công -> chuyển về danh sách
            resp.sendRedirect(req.getContextPath() + "/admin/users?msg=success");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi tạo tài khoản: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/admin/createUser.jsp").forward(req, resp);
        }
    }
}