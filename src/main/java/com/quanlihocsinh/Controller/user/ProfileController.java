package com.quanlihocsinh.Controller.user;

import com.quanlihocsinh.dao.MenuDAO;
import com.quanlihocsinh.dao.PersonDAO;
import com.quanlihocsinh.dao.StudentDAO;
import com.quanlihocsinh.dao.StudentClassDAO;
import com.quanlihocsinh.dao.CohortDAO;

import com.quanlihocsinh.model.Person;
import com.quanlihocsinh.model.Student;
import com.quanlihocsinh.model.User;
import com.quanlihocsinh.model.menu;
import com.quanlihocsinh.model.tblClass;
import com.quanlihocsinh.model.Cohort;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/user/profile")
public class ProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /* ===== MENU ===== */
        MenuDAO menuDAO = new MenuDAO();
        List<menu> menuList = menuDAO.getAllMenus();
        request.setAttribute("menuList", menuList);

        /* ===== KIỂM TRA LOGIN ===== */
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");

        /* ===== LẤY PERSON ===== */
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

        /* ===== LẤY STUDENT ===== */
        Student student = null;
        if ("Student".equalsIgnoreCase(person.getPersonType())) {

            StudentDAO studentDAO = new StudentDAO();
            student = studentDAO.getById(person.getOriginalId());

            // Đồng bộ dữ liệu từ PERSON sang STUDENT
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

                /* ===== LẤY LỚP + KHÓA (CHỈ ĐỌC) ===== */
                StudentClassDAO studentClassDAO = new StudentClassDAO();
                CohortDAO cohortDAO = new CohortDAO();

                // Lấy lớp hiện tại
                tblClass classObj = studentDAO.getCurrentClassByStudentId(student.getStudentID());

                // Lấy khóa theo lớp
                Cohort cohortObj = null;
                if (classObj != null && classObj.getCohortID() != null) {
                    cohortObj = cohortDAO.getById(classObj.getCohortID());
                }

                // Lưu vào session
                session.setAttribute("currentClass", classObj);
                session.setAttribute("currentCohort", cohortObj);
            }
        }

        /* ===== GỬI DỮ LIỆU SANG JSP ===== */
        request.setAttribute("person", person);
        request.setAttribute("student", student);
        request.setAttribute("contentPage", "/WEB-INF/views/user/profile/list.jsp");
        request.setAttribute("pageTitle", "Hồ sơ: " + person.getFullname());

        request.getRequestDispatcher("/WEB-INF/views/shared/Layout.jsp")
                .forward(request, response);
    }
}
