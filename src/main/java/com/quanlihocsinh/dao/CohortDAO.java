package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.Cohort;
import com.quanlihocsinh.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CohortDAO {

    public List<Cohort> getAll() {
        List<Cohort> list = new ArrayList<>();
        String sql = "SELECT CohortID, CohortName, StartYear, EndYear, IsActive FROM dbo.tblCohort ORDER BY CohortID";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cohort c = new Cohort();
                c.setCohortID(rs.getInt("CohortID"));
                c.setCohortName(rs.getInt("CohortName"));
                c.setStartYear(rs.getInt("StartYear"));
                c.setEndYear(rs.getInt("EndYear"));
                c.setIsActive(rs.getBoolean("IsActive"));
                list.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public Cohort getById(int id) {
        Cohort c = null;
        String sql = "SELECT CohortID, CohortName, StartYear, EndYear, IsActive FROM dbo.tblCohort WHERE CohortID = ?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    c = new Cohort();
                    c.setCohortID(rs.getInt("CohortID"));
                    c.setCohortName(rs.getInt("CohortName"));
                    c.setStartYear(rs.getInt("StartYear"));
                    c.setEndYear(rs.getInt("EndYear"));
                    c.setIsActive(rs.getBoolean("IsActive"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return c;
    }

    public void add(Cohort c) {
        String sql = "INSERT INTO dbo.tblCohort (CohortName, StartYear, EndYear, IsActive) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, c.getCohortName());
            ps.setInt(2, c.getStartYear());
            ps.setInt(3, c.getEndYear());
            ps.setBoolean(4, c.getIsActive());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Cohort c) {
        String sql = "UPDATE dbo.tblCohort SET CohortName=?, StartYear=?, EndYear=?, IsActive=? WHERE CohortID=?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, c.getCohortName());
            ps.setInt(2, c.getStartYear());
            ps.setInt(3, c.getEndYear());
            ps.setBoolean(4, c.getIsActive());
            ps.setInt(5, c.getCohortID());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM dbo.tblCohort WHERE CohortID=?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testConnection() {
        String sql = "SELECT TOP 5 CohortID, CohortName, StartYear, EndYear, IsActive FROM dbo.tblCohort";

        try (Connection conn = DBUtil.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Kết nối DB thành công!");
            while (rs.next()) {
                System.out.println(rs.getInt("CohortID") + " | " +
                        rs.getInt("CohortName") + " | " +
                        rs.getInt("StartYear") + " | " +
                        rs.getInt("EndYear") + " | " +
                        rs.getBoolean("IsActive"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
