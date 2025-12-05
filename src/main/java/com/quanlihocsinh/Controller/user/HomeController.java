package com.quanlihocsinh.Controller.user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.quanlihocsinh.dao.MenuDAO;
import com.quanlihocsinh.model.menu;

import java.io.IOException;
import java.util.List;

@WebServlet("/student/home")
public class HomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy danh sách menu
        MenuDAO menuDAO = new MenuDAO();
        List<menu> menuList = menuDAO.getAllMenus();
        request.setAttribute("menuList", menuList);

        // Thông tin thống kê tạm thời
        request.setAttribute("totalClasses", 10);
        request.setAttribute("totalSubjects", 15);
        request.setAttribute("totalTeachers", 20);

        // Truyền đường dẫn content và title vào layout
        request.setAttribute("contentPage", "/WEB-INF/views/student/home.jsp");
        request.setAttribute("pageTitle", "Trang chủ học sinh");

        // Forward đến Layout.jsp
        request.getRequestDispatcher("/WEB-INF/views/shared/Layout.jsp")
                .forward(request, response);
    }
}
