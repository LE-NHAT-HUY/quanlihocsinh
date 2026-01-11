package com.quanlihocsinh.Controller.teacher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

// 1. Import các DAO
import com.quanlihocsinh.dao.MenuDAO;
import com.quanlihocsinh.dao.StatisticsDAO; // <-- Đã thêm mới
import com.quanlihocsinh.model.menu;

@WebServlet("/teacher/home")
public class HomeController extends HttpServlet {

    // Khai báo biến DAO
    private MenuDAO menuDAO;
    private StatisticsDAO statisticsDAO;

    // 2. Khởi tạo DAO trong phương thức init (Chạy 1 lần khi server start)
    @Override
    public void init() {
        menuDAO = new MenuDAO();
        statisticsDAO = new StatisticsDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // --- Phần Menu ---
        List<menu> menuList = menuDAO.getAllMenus();
        request.setAttribute("menuList", menuList);

        // --- Phần Thống kê (MỚI) ---
        // Lấy số liệu thực tế từ Database thông qua DAO
        int totalStudents = statisticsDAO.countStudents();
        int totalTeachers = statisticsDAO.countTeachers();
        int totalClasses = statisticsDAO.countClasses();
        int totalSubjects = statisticsDAO.countSubjects();

        // Gửi dữ liệu sang View (JSP)
        request.setAttribute("totalStudents", totalStudents); // Thêm biến này nếu giao diện giáo viên cũng cần xem số
                                                              // HS
        request.setAttribute("totalTeachers", totalTeachers);
        request.setAttribute("totalClasses", totalClasses);
        request.setAttribute("totalSubjects", totalSubjects);

        // --- Cấu hình View ---
        request.setAttribute("contentPage", "/WEB-INF/views/teacher/home.jsp");
        request.setAttribute("pageTitle", "Trang chủ giáo viên");

        request.getRequestDispatcher("/WEB-INF/views/shared/Layoutteacher.jsp")
                .forward(request, response);
    }
}