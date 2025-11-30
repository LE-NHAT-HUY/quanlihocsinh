package com.quanlihocsinh.Controller.user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.quanlihocsinh.dao.MenuDAO;
import com.quanlihocsinh.model.menu;

import java.io.IOException;
import java.util.List;

@WebServlet("/user/home")
public class HomeController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy menu
        MenuDAO menuDAO = new MenuDAO();
        List<menu> menuList = menuDAO.getAllMenus();
        request.setAttribute("menuList", menuList);

        // Dữ liệu demo
        request.setAttribute("totalClasses", 10);
        request.setAttribute("totalSubjects", 15);
        request.setAttribute("totalTeachers", 20);

        // Trang nội dung sẽ nhúng vào layout
        request.setAttribute("contentPage", "/WEB-INF/views/user/home.jsp");
        request.setAttribute("pageTitle", "Trang chủ người dùng");

        // Chỉ forward tới layout
        request.getRequestDispatcher("/WEB-INF/views/shared/Layout.jsp")
                .forward(request, response);
    }
}
