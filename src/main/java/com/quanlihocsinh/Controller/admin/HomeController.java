package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.dao.StatisticsDAO;
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

    private StatisticsDAO statisticsDAO;

    @Override
    public void init() throws ServletException {
        statisticsDAO = new StatisticsDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            request.setAttribute("totalStudents", statisticsDAO.countStudents());
            request.setAttribute("totalTeachers", statisticsDAO.countTeachers());
            request.setAttribute("totalClasses", statisticsDAO.countClasses());

            List<ChartData> classData = statisticsDAO.getClassSizeData();
            List<ChartData> ratingData = statisticsDAO.getAcademicRatingData();
            List<ChartData> scoreData = statisticsDAO.getScoreDistributionData();

            request.setAttribute("classData", classData);
            request.setAttribute("ratingData", ratingData);
            request.setAttribute("scoreData", scoreData);
        } catch (Exception e) {
            e.printStackTrace();

            request.setAttribute("totalStudents", 0);
            request.setAttribute("totalTeachers", 0);
            request.setAttribute("totalClasses", 0);
            request.setAttribute("classData", new ArrayList<ChartData>());
            request.setAttribute("ratingData", new ArrayList<ChartData>());
            request.setAttribute("scoreData", new ArrayList<ChartData>());
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/admin/home.jsp");
        rd.forward(request, response);
    }
}