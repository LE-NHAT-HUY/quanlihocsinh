package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.Grade;
import com.quanlihocsinh.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {

    private Grade map(ResultSet rs) throws SQLException {
        Grade g = new Grade();
        g.setGradeID(rs.getInt("GradeID"));
        g.setGradeName(rs.getString("GradeName"));
        g.setDescription(rs.getString("Description"));
        g.setIsActive(rs.getBoolean("IsActive"));
        return g;
    }

    public List<Grade> getAll() {
        List<Grade> list = new ArrayList<>();
        String sql = "SELECT GradeID, GradeName, Description, IsActive FROM dbo.tblGrade ORDER BY GradeID";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next())
                list.add(map(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public Grade getById(int id) {
        String sql = "SELECT GradeID, GradeName, Description, IsActive FROM dbo.tblGrade WHERE GradeID = ?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return map(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void add(Grade g) {
        String sql = "INSERT INTO dbo.tblGrade (GradeName, Description, IsActive) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, g.getGradeName());
            ps.setString(2, g.getDescription());
            ps.setBoolean(3, g.isIsActive());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Grade g) {
        String sql = "UPDATE dbo.tblGrade SET GradeName=?, Description=?, IsActive=? WHERE GradeID=?";

        try (Connection conn = DBUtil.getConnection();
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

    // Xóa
    public void delete(int id) {
        String sql = "DELETE FROM dbo.tblGrade WHERE GradeID=?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
