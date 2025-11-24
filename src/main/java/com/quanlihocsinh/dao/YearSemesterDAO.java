package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.YearSemester;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class YearSemesterDAO {

    private final String URL = "jdbc:sqlserver://LENOVO\\HUY123:1433;"
            + "databaseName=StudentManagementDB;"
            + "integratedSecurity=true;"
            + "encrypt=true;"
            + "trustServerCertificate=true";

    private final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    // Kết nối CSDL
    private Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER); // Load driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL);
    }

    // Lấy tất cả YearSemester
    public List<YearSemester> getAll() throws SQLException {
        List<YearSemester> list = new ArrayList<>();
        String sql = "SELECT * FROM tblYearSemester";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                YearSemester ys = new YearSemester();
                ys.setYearSemesterID(rs.getInt("YearSemesterID"));
                ys.setSemesterName(rs.getString("SemesterName"));
                ys.setSchoolYear(rs.getString("SchoolYear"));
                ys.setIsActive(rs.getBoolean("IsActive"));
                list.add(ys);
            }
        }
        return list;
    }

    // Lấy theo ID
    public YearSemester getById(int id) throws SQLException {
        String sql = "SELECT * FROM tblYearSemester WHERE YearSemesterID=?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    YearSemester ys = new YearSemester();
                    ys.setYearSemesterID(rs.getInt("YearSemesterID"));
                    ys.setSemesterName(rs.getString("SemesterName"));
                    ys.setSchoolYear(rs.getString("SchoolYear"));
                    ys.setIsActive(rs.getBoolean("IsActive"));
                    return ys;
                }
            }
        }
        return null;
    }

    // Thêm mới
    public void add(YearSemester ys) throws SQLException {
        String sql = "INSERT INTO tblYearSemester(SemesterName, SchoolYear, IsActive) VALUES(?,?,?)";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ys.getSemesterName());
            ps.setString(2, ys.getSchoolYear());
            ps.setBoolean(3, ys.getIsActive());
            ps.executeUpdate();
        }
    }

    // Cập nhật
    public void update(YearSemester ys) throws SQLException {
        String sql = "UPDATE tblYearSemester SET SemesterName=?, SchoolYear=?, IsActive=? WHERE YearSemesterID=?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ys.getSemesterName());
            ps.setString(2, ys.getSchoolYear());
            ps.setBoolean(3, ys.getIsActive());
            ps.setInt(4, ys.getYearSemesterID());
            ps.executeUpdate();
        }
    }

    // Xóa
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM tblYearSemester WHERE YearSemesterID=?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
