package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.model.ChartData;
// Import các Service/DAO của bạn ở đây (ví dụ: StudentService, ClassService...)

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/admin/home")
public class HomeController extends HttpServlet {

    // Khai báo các Service (Giả sử bạn đã có các lớp này để gọi Database)
    // private StudentService studentService = new StudentService();
    // private TeacherService teacherService = new TeacherService();
    // private ClassService classService = new ClassService();
    // private ScoreService scoreService = new ScoreService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. LẤY SỐ LIỆU TỔNG QUAN (CARD STATS)
        // Thay số cứng bằng: studentService.countActiveStudents();
        int totalStudents = 1250;

        // Thay số cứng bằng: teacherService.countActiveTeachers();
        int totalTeachers = 84;

        // Thay số cứng bằng: classService.countActiveClasses();
        int totalClasses = 32;

        request.setAttribute("totalStudents", totalStudents);
        request.setAttribute("totalTeachers", totalTeachers);
        request.setAttribute("totalClasses", totalClasses);

        // 2. LẤY DỮ LIỆU BIỂU ĐỒ SĨ SỐ LỚP (BAR CHART)
        // Logic: Lấy danh sách lớp và sĩ số hiện tại (currentStudents)
        List<ChartData> classData = new ArrayList<>();
        // Code mẫu giả lập DB:
        classData.add(new ChartData("10A1", 45));
        classData.add(new ChartData("10A2", 42));
        classData.add(new ChartData("11B1", 38));
        classData.add(new ChartData("11B2", 40));
        classData.add(new ChartData("12C1", 35));
        // Gán vào request
        request.setAttribute("classData", classData);

        // 3. LẤY DỮ LIỆU BIỂU ĐỒ HỌC LỰC (DOUGHNUT CHART)
        // Logic: Query COUNT(*) group by AcademicRating
        List<ChartData> ratingData = new ArrayList<>();
        // Nhãn ở đây dùng Tiếng Việt để hiển thị lên màn hình
        ratingData.add(new ChartData("Giỏi", 150));
        ratingData.add(new ChartData("Khá", 400));
        ratingData.add(new ChartData("Trung Bình", 500));
        ratingData.add(new ChartData("Yếu", 150));
        ratingData.add(new ChartData("Kém", 50));

        request.setAttribute("ratingData", ratingData);

        // 4. LẤY DỮ LIỆU BIỂU ĐỒ PHỔ ĐIỂM (BAR CHART)
        // Logic: Đếm số lượng học sinh theo các khoảng điểm
        List<ChartData> scoreData = new ArrayList<>();
        scoreData.add(new ChartData("< 5", 200));
        scoreData.add(new ChartData("5 - 7", 500));
        scoreData.add(new ChartData("7 - 8", 300));
        scoreData.add(new ChartData("8 - 9", 200));
        scoreData.add(new ChartData("9 - 10", 50));

        request.setAttribute("scoreData", scoreData);

        // Forward về View
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/admin/home.jsp");
        rd.forward(request, response);
    }
}