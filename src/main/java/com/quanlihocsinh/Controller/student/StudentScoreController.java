package com.quanlihocsinh.Controller.student;

import com.quanlihocsinh.dao.*;
import com.quanlihocsinh.model.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/student/grades")
public class StudentScoreController extends HttpServlet {

    private ScoreDAO scoreDAO;
    private StudentDAO studentDAO;
    private YearSemesterDAO yearSemesterDAO;
    private MenuDAO menuDAO;
    private TblClassDAO tblClassDAO;

    @Override
    public void init() {
        scoreDAO = new ScoreDAO();
        studentDAO = new StudentDAO();
        yearSemesterDAO = new YearSemesterDAO();
        menuDAO = new MenuDAO();
        tblClassDAO = new TblClassDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        // 1. Kiểm tra quyền Học sinh (Role = 3)
        if (user == null || user.getRoleId() != 3) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // 2. Load Menu Sidebar
        req.setAttribute("menuList", menuDAO.getAllMenus());

        // 3. Lấy thông tin Học sinh
        int studentPK = user.getEntityId();
        Student student = studentDAO.getById(studentPK);

        if (student == null) {
            req.setAttribute("errorMessage", "Không tìm thấy thông tin học sinh.");
            req.getRequestDispatcher("/WEB-INF/views/shared/Layout.jsp").forward(req, resp);
            return;
        }

        // 4. Lấy Lớp học hiện tại
        tblClass currentClass = studentDAO.getCurrentClassByStudentId(student.getStudentID());

        // 5. Xử lý Học kỳ (Lấy danh sách & Xác định kỳ được chọn)
        List<YearSemester> yearSemesters = yearSemesterDAO.getAllActive();
        req.setAttribute("yearSemesters", yearSemesters);

        int yearSemesterID = 0;
        String yidStr = req.getParameter("yearSemesterID");

        if (yidStr != null && !yidStr.isEmpty()) {
            try {
                yearSemesterID = Integer.parseInt(yidStr);
            } catch (NumberFormatException e) {
                // Nếu param lỗi thì giữ 0
            }
        } else if (!yearSemesters.isEmpty()) {
            // Mặc định chọn kỳ mới nhất (đầu danh sách)
            yearSemesterID = yearSemesters.get(0).getYearSemesterID();
        }

        // 6. Lấy Bảng điểm (Transcript)
        if (yearSemesterID > 0) {
            List<Score> transcript = scoreDAO.getStudentTranscript(student.getStudentID(), yearSemesterID);
            req.setAttribute("transcript", transcript);
        }

        // 7. Gửi dữ liệu sang View
        req.setAttribute("student", student);
        req.setAttribute("currentClass", currentClass);
        req.setAttribute("yearSemesterID", yearSemesterID);

        req.setAttribute("pageTitle", "Xem điểm cá nhân");
        req.setAttribute("contentPage", "/WEB-INF/views/student/score/list.jsp");
        req.getRequestDispatcher("/WEB-INF/views/shared/Layout.jsp").forward(req, resp);
    }
}