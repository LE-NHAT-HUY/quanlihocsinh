package com.quanlihocsinh.Controller.student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

// Import các DAO cần thiết
import com.quanlihocsinh.dao.MenuDAO;
import com.quanlihocsinh.dao.StatisticsDAO; // <-- Thêm import này
import com.quanlihocsinh.model.menu;

@WebServlet("/student/home")
public class HomeController extends HttpServlet {

    private MenuDAO menuDAO;
    private StatisticsDAO statisticsDAO;

    @Override
    public void init() {
        // Khởi tạo DAO 1 lần để tối ưu
        menuDAO = new MenuDAO();
        statisticsDAO = new StatisticsDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. GIỮ NGUYÊN: Lấy danh sách menu
        List<menu> menuList = menuDAO.getAllMenus();
        request.setAttribute("menuList", menuList);

        // 2. CẬP NHẬT: Lấy số liệu thống kê thực tế từ DB
        // (Thay thế các số cứng 10, 15, 20 cũ)
        int totalStudents = statisticsDAO.countStudents();
        int totalTeachers = statisticsDAO.countTeachers();
        int totalClasses = statisticsDAO.countClasses();
        int totalSubjects = statisticsDAO.countSubjects();

        request.setAttribute("totalStudents", totalStudents);
        request.setAttribute("totalTeachers", totalTeachers);
        request.setAttribute("totalClasses", totalClasses);
        request.setAttribute("totalSubjects", totalSubjects);

        // 3. GIỮ NGUYÊN: Truyền đường dẫn content và title
        request.setAttribute("contentPage", "/WEB-INF/views/student/home.jsp");
        request.setAttribute("pageTitle", "Trang chủ học sinh");

        // 4. GIỮ NGUYÊN: Forward đến Layout
        request.getRequestDispatcher("/WEB-INF/views/shared/Layout.jsp")
                .forward(request, response);
    }
}