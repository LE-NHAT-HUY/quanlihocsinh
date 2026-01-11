package com.quanlihocsinh.Controller.teacher;

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

@WebServlet("/teacher/*")
public class ProfileController extends HttpServlet {

    private PersonDAO personDAO;
    private StudentDAO studentDAO;
    private StudentClassDAO studentClassDAO;
    private CohortDAO cohortDAO;
    private MenuDAO menuDAO;

    @Override
    public void init() throws ServletException {
        personDAO = new PersonDAO();
        studentDAO = new StudentDAO();
        studentClassDAO = new StudentClassDAO();
        cohortDAO = new CohortDAO();
        menuDAO = new MenuDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /* ===== 1. MENU (Giữ nguyên) ===== */
        List<menu> menuList = menuDAO.getAllMenus();
        request.setAttribute("menuList", menuList);

        /* ===== 2. KIỂM TRA LOGIN ===== */
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");

        /* ===== 3. LẤY THÔNG TIN CÁ NHÂN (PERSON) ===== */
        // Nên lấy lại từ DB để đảm bảo dữ liệu mới nhất (thay vì lấy từ session)
        int personId = user.getPersonId();
        Person person = null;
        try {
            person = personDAO.getById(personId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (person == null) {
            // Trường hợp lỗi dữ liệu nghiêm trọng
            response.sendRedirect(request.getContextPath() + "/logout");
            return;
        }

        /* ===== 4. XỬ LÝ THEO VAI TRÒ (Dựa vào RoleID) ===== */
        Student student = null;

        // RoleID = 3 là Học sinh
        if (user.getRoleId() == 3) {

            // LOGIC MỚI: Lấy ID Học sinh từ EntityId (đã nạp khi login)
            int studentPK = user.getEntityId();

            if (studentPK > 0) {
                try {
                    // Lấy thông tin học sinh theo ID
                    student = studentDAO.getById(studentPK);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (student != null) {
                    // --- ĐỒNG BỘ DỮ LIỆU HIỂN THỊ ---
                    // Vì thông tin chính (Tên, ngày sinh...) nằm ở bảng Person,
                    // ta gán nó vào object Student để JSP hiển thị không bị null.

                    student.setFullName(person.getFullName());
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

                    /* ===== LẤY LỚP + KHÓA (Logic cũ) ===== */
                    try {
                        // Lưu ý: Hàm này cần nhận Student ID (thường là chuỗi mã HS hoặc ID số)
                        // Tùy vào StudentDAO viết thế nào, ở đây ta truyền ID số
                        tblClass classObj = studentDAO.getCurrentClassByStudentId(student.getStudentID());

                        Cohort cohortObj = null;
                        if (classObj != null && classObj.getCohortID() != null) {
                            cohortObj = cohortDAO.getById(classObj.getCohortID());
                        }

                        request.setAttribute("currentClass", classObj);
                        request.setAttribute("currentCohort", cohortObj);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        // RoleID = 2 (Giáo viên) - Bạn có thể thêm logic lấy thông tin GV ở đây nếu cần
        else if (user.getRoleId() == 2) {
            // Logic cho giáo viên (nếu cần hiển thị profile khác)
        }

        /* ===== 5. GỬI DỮ LIỆU SANG JSP ===== */
        request.setAttribute("person", person);
        request.setAttribute("student", student);

        // Thiết lập layout
        request.setAttribute("contentPage", "/WEB-INF/views/teacher/profile.jsp");
        request.setAttribute("pageTitle", "Hồ sơ: " + person.getFullName());

        request.getRequestDispatcher("/WEB-INF/views/shared/Layoutteacher.jsp")
                .forward(request, response);
    }
}