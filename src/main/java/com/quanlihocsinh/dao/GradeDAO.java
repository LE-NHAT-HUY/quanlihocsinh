package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.Grade;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {

    private final String URL = "jdbc:sqlserver://LENOVO\\HUY123:1433;"
            + "databaseName=StudentManagementDB;"
            + "integratedSecurity=true;"
            + "encrypt=true;"
            + "trustServerCertificate=true";

    private final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    public GradeDAO() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // Lấy tất cả bản ghi
    public List<Grade> getAll() {
        List<Grade> list = new ArrayList<>();
        String sql = "SELECT GradeID, GradeName, Description, IsActive FROM dbo.tblGrade ORDER BY GradeID";

        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Grade g = new Grade();
                g.setGradeID(rs.getInt("GradeID"));
                g.setGradeName(rs.getString("GradeName"));
                g.setDescription(rs.getString("Description"));
                g.setIsActive(rs.getBoolean("IsActive"));
                list.add(g);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Lấy theo ID
    public Grade getById(int id) {
        Grade g = null;
        String sql = "SELECT GradeID, GradeName, Description, IsActive FROM dbo.tblGrade WHERE GradeID = ?";

        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    g = new Grade();
                    g.setGradeID(rs.getInt("GradeID"));
                    g.setGradeName(rs.getString("GradeName"));
                    g.setDescription(rs.getString("Description"));
                    g.setIsActive(rs.getBoolean("IsActive"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return g;
    }

    // Thêm mới bản ghi
    public void add(Grade g) {
        String sql = "INSERT INTO dbo.tblGrade (GradeName, Description, IsActive) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, g.getGradeName());
            ps.setString(2, g.getDescription());
            ps.setBoolean(3, g.isIsActive());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cập nhật bản ghi
    public void update(Grade g) {
        String sql = "UPDATE dbo.tblGrade SET GradeName=?, Description=?, IsActive=? WHERE GradeID=?";

        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, g.getGradeName());
            ps.setString(2, g.getDescription());
            ps.setBoolean(3, g.isIsActive());
            ps.setInt(4, g.getGradeID());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa bản ghi
    public void delete(int id) {
        String sql = "DELETE FROM dbo.tblGrade WHERE GradeID=?";

        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
