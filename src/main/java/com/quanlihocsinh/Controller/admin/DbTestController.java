package com.quanlihocsinh.Controller.admin;

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/dbtest")
public class DbTestController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String JDBC_URL = "jdbc:sqlserver://LENOVO\\HUY123:1433;"
            + "databaseName=StudentManagementDB;"
            + "integratedSecurity=true;"
            + "encrypt=true;"
            + "trustServerCertificate=true";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String message;
        try (Connection conn = DriverManager.getConnection(JDBC_URL)) {
            message = "DB Connection Successful!";

            // Truy vấn dữ liệu từ tblCohort
            String sql = "SELECT TOP 5 CohortID, CohortName, StartYear, EndYear, IsActive FROM tblCohort";
            try (Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql)) {

                System.out.println("----- Sample Data from tblCohort -----");
                while (rs.next()) {
                    int id = rs.getInt("CohortID");
                    int name = rs.getInt("CohortName");
                    int start = rs.getInt("StartYear");
                    int end = rs.getInt("EndYear");
                    boolean active = rs.getBoolean("IsActive");

                    System.out.println("ID: " + id + ", Cohort: " + name +
                            ", Start: " + start + ", End: " + end + ", Active: " + active);
                }
                System.out.println("-------------------------------------");
            }

        } catch (SQLException e) {
            message = "DB Connection Failed! Error: " + e.getMessage();
            e.printStackTrace();
        }

        request.setAttribute("message", message);
        request.getRequestDispatcher("/WEB-INF/views/dbtest.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
