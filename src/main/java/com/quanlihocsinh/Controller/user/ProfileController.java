package com.quanlihocsinh.Controller.user;

import com.quanlihocsinh.dao.MenuDAO;
import com.quanlihocsinh.dao.PersonDAO;
import com.quanlihocsinh.dao.StudentDAO;
import com.quanlihocsinh.model.Person;
import com.quanlihocsinh.model.Student;
import com.quanlihocsinh.model.User;
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

        // Menu
        MenuDAO menuDAO = new MenuDAO();
        List<menu> menuList = menuDAO.getAllMenus();
        request.setAttribute("menuList", menuList);

        // Kiểm tra user đăng nhập
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");

        // Lấy personId từ user session
        int personId = user.getPersonId();

        PersonDAO personDAO = new PersonDAO();
        Person person = null;
        try {
            person = personDAO.getById(personId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (person == null) {
            response.sendRedirect(request.getContextPath() + "/user/home");
            return;
        }

        // Nếu person là học sinh, lấy thông tin từ tblStudent
        Student student = null;
        if ("Student".equalsIgnoreCase(person.getPersonType())) {
            StudentDAO studentDAO = new StudentDAO();
            student = studentDAO.getById(person.getOriginalId());

            // Điền thông tin từ Person nếu Student còn thiếu
            if (student != null) {
                if (student.getFullName() == null)
                    student.setFullName(person.getFullname());
                if (student.getBirth() == null)
                    student.setBirth(person.getBirth());
                if (student.getGender() == null)
                    student.setGender(person.getGender());
                if (student.getAddress() == null)
                    student.setAddress(person.getAddress());
                if (student.getNumberPhone() == null)
                    student.setNumberPhone(person.getPhone());
                if (student.getImages() == null)
                    student.setImages(person.getImages());
            }
        }

        request.setAttribute("person", person);
        request.setAttribute("student", student);
        request.setAttribute("contentPage", "/WEB-INF/views/user/profile/list.jsp");
        request.setAttribute("pageTitle", "Hồ sơ học sinh: " + person.getFullname());

        request.getRequestDispatcher("/WEB-INF/views/shared/Layout.jsp").forward(request, response);
    }
}
