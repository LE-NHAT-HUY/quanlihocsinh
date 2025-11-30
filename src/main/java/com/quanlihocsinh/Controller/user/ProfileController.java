package com.quanlihocsinh.Controller.user;

import com.quanlihocsinh.dao.MenuDAO;
import com.quanlihocsinh.dao.StudentDAO;
import com.quanlihocsinh.model.Student;
import com.quanlihocsinh.model.menu;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/user/profile")
public class ProfileController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy menu
        MenuDAO menuDAO = new MenuDAO();
        List<menu> menuList = menuDAO.getAllMenus();
        request.setAttribute("menuList", menuList);

        // Lấy id học sinh từ param
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/user/home");
            return;
        }

        int studentId;
        try {
            studentId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/user/home");
            return;
        }

        // Lấy thông tin học sinh
        StudentDAO dao = new StudentDAO();
        Student student = dao.getById(studentId);

        if (student == null) {
            response.sendRedirect(request.getContextPath() + "/user/home");
            return;
        }

        // Đưa dữ liệu vào request
        request.setAttribute("student", student);
        request.setAttribute("contentPage", "/WEB-INF/views/user/profile/list.jsp");
        request.setAttribute("pageTitle", "Hồ sơ học sinh: " + student.getFullName());

        // Chỉ forward một lần duy nhất tới layout
        request.getRequestDispatcher("/WEB-INF/views/shared/Layout.jsp")
                .forward(request, response);
    }
}
