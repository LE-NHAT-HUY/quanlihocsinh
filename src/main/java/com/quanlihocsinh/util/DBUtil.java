package com.quanlihocsinh.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static final String URL = "jdbc:sqlserver://LENOVO\\HUY123:1433;"
            + "databaseName=StudentManagementDB;"
            + "integratedSecurity=true;"
            + "encrypt=true;"
            + "trustServerCertificate=true";

    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy driver SQLServer JDBC!");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
