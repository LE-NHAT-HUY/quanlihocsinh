package com.quanlihocsinh.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // --- CẤU HÌNH THÍ NGHIỆM ---
    // SQL Server (Windows Authentication) bỏ qua SSL warning
    private static final String URL = "jdbc:sqlserver://LENOVO\\\\HUY123;databaseName=StudentManagementDB;integratedSecurity=true;encrypt=true;trustServerCertificate=true";

    static {
        try {
            // Load SQL Server JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        // Dùng integratedSecurity=true nên không cần USER, PASS
        return DriverManager.getConnection(URL);
    }
}
