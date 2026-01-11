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

    private YearSemesterDAO yearSemesterDAO;
    private ScoreDAO scoreDAO;
    private StudentDAO studentDAO;
    private MenuDAO menuDAO;
    private AchievementService achievementService;

    @Override
    public void init() {
        yearSemesterDAO = new YearSemesterDAO();
        scoreDAO = new ScoreDAO();
        studentDAO = new StudentDAO();
        menuDAO = new MenuDAO();
        achievementService = new AchievementService();
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

        // 2. Load Menu & Thông tin học sinh
        req.setAttribute("menuList", menuDAO.getAllMenus());
        Student student = studentDAO.getById(user.getEntityId());
        req.setAttribute("student", student);

        // 3. LOGIC CHÍNH: Lấy lịch sử thành tích
        List<String> schoolYears = yearSemesterDAO.getDistinctSchoolYears();
        List<YearAchievement> achievementList = new ArrayList<>();

        // --- BẮT ĐẦU DEBUG LOG (Xem kết quả ở tab Output của NetBeans) ---
        System.out.println("=============================================");
        System.out.println("DEBUG: Tính thành tích cho HS: " + student.getFullName());
        System.out.println("Tổng số năm học tìm thấy: " + schoolYears.size());

        for (String year : schoolYears) {
            YearAchievement ya = new YearAchievement();
            ya.setSchoolYear(year);

            // Tìm ID học kỳ bằng từ khóa ngắn gọn "1" và "2"
            // (Yêu cầu DAO dùng LIKE '%1%' và LIKE '%2%')
            int idHK1 = yearSemesterDAO.getSemesterIDByYearAndName(year, "1");
            int idHK2 = yearSemesterDAO.getSemesterIDByYearAndName(year, "2");

            System.out.println("---------------------------------------------");
            System.out.println("Năm học: " + year);
            System.out.println("  -> ID HK1 tìm được: " + idHK1);
            System.out.println("  -> ID HK2 tìm được: " + idHK2);

            // Lấy danh sách điểm
            List<Score> listHK1 = (idHK1 > 0) ? scoreDAO.getStudentTranscript(student.getStudentID(), idHK1)
                    : new ArrayList<>();
            List<Score> listHK2 = (idHK2 > 0) ? scoreDAO.getStudentTranscript(student.getStudentID(), idHK2)
                    : new ArrayList<>();

            System.out.println("  -> Số lượng điểm HK1: " + listHK1.size());
            System.out.println("  -> Số lượng điểm HK2: " + listHK2.size());

            // --- TÍNH TOÁN HỌC KỲ 1 ---
            double avgHK1 = achievementService.calculateAverage(listHK1);
            String rankHK1 = achievementService.classifyAcademic(avgHK1, listHK1);
            ya.setAvgHK1(avgHK1);
            ya.setRankHK1(rankHK1);

            // --- TÍNH TOÁN HỌC KỲ 2 ---
            double avgHK2 = achievementService.calculateAverage(listHK2);
            String rankHK2 = achievementService.classifyAcademic(avgHK2, listHK2);
            ya.setAvgHK2(avgHK2);
            ya.setRankHK2(rankHK2);

            // --- TÍNH TOÁN CẢ NĂM ---
            // Logic: Chỉ tính cả năm nếu ít nhất HK1 đã có điểm
            if (!listHK1.isEmpty()) {
                double avgYear = achievementService.calculateYearAverage(avgHK1, avgHK2);
                ya.setAvgYear(avgYear);

                // Gộp điểm để xét môn khống chế
                List<Score> allScores = new ArrayList<>(listHK1);
                allScores.addAll(listHK2);

                String rankYear = achievementService.classifyAcademic(avgYear, allScores);
                ya.setRankYear(rankYear);

                // Xét danh hiệu (chỉ có khi đã xếp loại)
                String title = achievementService.getTitle(rankYear);
                ya.setTitleYear(title);
            } else {
                ya.setAvgYear(0.0);
                ya.setRankYear("Chưa xếp loại");
                ya.setTitleYear("");
            }

            achievementList.add(ya);
        }
        System.out.println("=============================================");
        // --- KẾT THÚC DEBUG LOG ---

        req.setAttribute("achievementList", achievementList);
        req.setAttribute("pageTitle", "Lịch sử thành tích");
        req.setAttribute("contentPage", "/WEB-INF/views/student/achievement/index.jsp");
        req.getRequestDispatcher("/WEB-INF/views/shared/Layout.jsp").forward(req, resp);
    }
}