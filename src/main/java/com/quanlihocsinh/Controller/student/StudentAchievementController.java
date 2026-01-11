package com.quanlihocsinh.Controller.student;

import com.quanlihocsinh.dao.*;
import com.quanlihocsinh.model.*;
import com.quanlihocsinh.service.AchievementService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/student/achievements")
public class StudentAchievementController extends HttpServlet {

    private ScoreDAO scoreDAO;
    private StudentDAO studentDAO;
    private MenuDAO menuDAO;
    private AchievementService achievementService;

    @Override
    public void init() {
        scoreDAO = new ScoreDAO();
        studentDAO = new StudentDAO();
        menuDAO = new MenuDAO();
        achievementService = new AchievementService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || user.getRoleId() != 3) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        req.setAttribute("menuList", menuDAO.getAllMenus());
        int studentPK = user.getEntityId();
        Student student = studentDAO.getById(studentPK);
        req.setAttribute("student", student);

        // === TÍNH TOÁN THÀNH TÍCH ===

        // 1. Lấy bảng điểm HK1 (ID=1) và HK2 (ID=2)
        // Lưu ý: Đảm bảo ID học kỳ trong DB khớp với logic này (hoặc query động)
        List<Score> listHK1 = scoreDAO.getStudentTranscript(student.getStudentID(), 1);
        List<Score> listHK2 = scoreDAO.getStudentTranscript(student.getStudentID(), 2);

        // 2. Tính ĐTB từng kỳ
        double avgHK1 = achievementService.calculateAverage(listHK1);
        double avgHK2 = achievementService.calculateAverage(listHK2);

        // 3. Xếp loại từng kỳ
        String rankHK1 = achievementService.classifyAcademic(avgHK1, listHK1);
        String rankHK2 = achievementService.classifyAcademic(avgHK2, listHK2);

        // 4. Tính Cả năm
        double avgYear = achievementService.calculateYearAverage(avgHK1, avgHK2);

        // Gộp danh sách điểm cả năm để xét điểm liệt (Logic đơn giản: lấy tất cả điểm
        // đã có)
        List<Score> allScores = new ArrayList<>();
        allScores.addAll(listHK1);
        allScores.addAll(listHK2);

        String rankYear = achievementService.classifyAcademic(avgYear, allScores);
        String titleYear = achievementService.getTitle(rankYear);

        // 5. Gửi dữ liệu sang View
        req.setAttribute("avgHK1", avgHK1);
        req.setAttribute("rankHK1", rankHK1);

        req.setAttribute("avgHK2", avgHK2);
        req.setAttribute("rankHK2", rankHK2);

        req.setAttribute("avgYear", avgYear);
        req.setAttribute("rankYear", rankYear);
        req.setAttribute("titleYear", titleYear);

        req.setAttribute("pageTitle", "Thành tích học tập");
        req.setAttribute("contentPage", "/WEB-INF/views/student/achievement/index.jsp");
        req.getRequestDispatcher("/WEB-INF/views/shared/Layout.jsp").forward(req, resp);
    }
}