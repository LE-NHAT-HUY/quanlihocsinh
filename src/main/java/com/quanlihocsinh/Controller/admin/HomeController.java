package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.model.ChartData;

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int totalStudents = 7;

        int totalTeachers = 4;

        int totalClasses = 12;

        request.setAttribute("totalStudents", totalStudents);
        request.setAttribute("totalTeachers", totalTeachers);
        request.setAttribute("totalClasses", totalClasses);

        List<ChartData> classData = new ArrayList<>();

        classData.add(new ChartData("10A1", 45));
        classData.add(new ChartData("10A2", 42));
        classData.add(new ChartData("11B1", 38));
        classData.add(new ChartData("11B2", 40));
        classData.add(new ChartData("12C1", 35));

        request.setAttribute("classData", classData);

        List<ChartData> ratingData = new ArrayList<>();

        ratingData.add(new ChartData("Giỏi", 150));
        ratingData.add(new ChartData("Khá", 400));
        ratingData.add(new ChartData("Trung Bình", 500));
        ratingData.add(new ChartData("Yếu", 150));
        ratingData.add(new ChartData("Kém", 50));

        request.setAttribute("ratingData", ratingData);

        List<ChartData> scoreData = new ArrayList<>();
        scoreData.add(new ChartData("< 5", 200));
        scoreData.add(new ChartData("5 - 7", 500));
        scoreData.add(new ChartData("7 - 8", 300));
        scoreData.add(new ChartData("8 - 9", 200));
        scoreData.add(new ChartData("9 - 10", 50));

        request.setAttribute("scoreData", scoreData);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/admin/home.jsp");
        rd.forward(request, response);
    }
}