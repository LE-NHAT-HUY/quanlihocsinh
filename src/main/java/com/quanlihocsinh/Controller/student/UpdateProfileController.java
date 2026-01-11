// File: src/java/com/quanlihocsinh/Controller/user/UpdateProfileController.java

package com.quanlihocsinh.Controller.student;

import com.quanlihocsinh.dao.PersonDAO;
import com.quanlihocsinh.dao.StudentDAO;
import com.quanlihocsinh.model.Person;
import com.quanlihocsinh.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/user/updateProfile")
public class UpdateProfileController extends HttpServlet {

    private PersonDAO personDAO;
    private StudentDAO studentDAO;

    @Override
    public void init() {
        personDAO = new PersonDAO();
        studentDAO = new StudentDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");

        // 1. Lấy dữ liệu cơ bản
        String fullName = req.getParameter("fullName");
        String phone = req.getParameter("phone");
        String gender = req.getParameter("gender");
        String address = req.getParameter("address"); // Địa chỉ đầy đủ (gộp)

        // 2. Lấy dữ liệu chi tiết (Mới thêm)
        String hamlet = req.getParameter("hamlet");
        String commune = req.getParameter("commune");
        String province = req.getParameter("province");
        String nation = req.getParameter("nation");
        String religion = req.getParameter("religion");
        String nationality = req.getParameter("nationality");

        // Xử lý ngày sinh
        String birthStr = req.getParameter("birth");
        Date sqlBirth = null;
        try {
            if (birthStr != null && !birthStr.isEmpty()) {
                sqlBirth = Date.valueOf(birthStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // A. Cập nhật bảng Person (Chỉ chứa thông tin chung)
            Person person = user.getProfile();
            if (person == null) {
                person = new Person();
                person.setPersonId(user.getPersonId());
            }
            person.setFullName(fullName);
            person.setPhone(phone);
            person.setGender(gender);
            person.setAddress(address);
            person.setBirth(sqlBirth);

            personDAO.update(person); // Lưu bảng Person

            // B. Cập nhật bảng tblStudent (Chứa thông tin chi tiết thôn, xã, dân tộc...)
            if (user.getRoleId() == 3 && user.getEntityId() > 0) {
                studentDAO.updateProfileInfo(
                        user.getEntityId(),
                        fullName, sqlBirth, gender, address, phone,
                        hamlet, commune, province, nation, religion, nationality);
            }

            // Cập nhật session và reload
            user.setProfile(person);
            session.setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/user/profile?msg=success");

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "Lỗi cập nhật: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/user/profile/list.jsp").forward(req, resp);
        }
    }
}