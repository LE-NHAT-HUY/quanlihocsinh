package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.YearSemester;
import com.quanlihocsinh.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class YearSemesterDAO {

    public List<YearSemester> getAll() throws SQLException {
        List<YearSemester> list = new ArrayList<>();
        String sql = "SELECT * FROM tblYearSemester";

        try (Connection conn = DBUtil.getConnection();
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

    public YearSemester getById(int id) throws SQLException {
        String sql = "SELECT * FROM tblYearSemester WHERE YearSemesterID=?";

        try (Connection conn = DBUtil.getConnection();
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

    public void add(YearSemester ys) throws SQLException {
        String sql = "INSERT INTO tblYearSemester (SemesterName, SchoolYear, IsActive) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ys.getSemesterName());
            ps.setString(2, ys.getSchoolYear());
            ps.setBoolean(3, ys.getIsActive());
            ps.executeUpdate();
        }
    }

    public void update(YearSemester ys) throws SQLException {
        String sql = "UPDATE tblYearSemester SET SemesterName=?, SchoolYear=?, IsActive=? WHERE YearSemesterID=?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ys.getSemesterName());
            ps.setString(2, ys.getSchoolYear());
            ps.setBoolean(3, ys.getIsActive());
            ps.setInt(4, ys.getYearSemesterID());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM tblYearSemester WHERE YearSemesterID=?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<YearSemester> getAllActive() {
        List<YearSemester> list = new ArrayList<>();

        String sql = "SELECT * FROM tblYearSemester WHERE IsActive = 1 ORDER BY YearSemesterID DESC";

        try (Connection conn = DBUtil.getConnection();
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
        } catch (Exception e) {

            e.printStackTrace();
        }
        return list;
    }

    public List<String> getDistinctSchoolYears() {
        List<String> years = new ArrayList<>();
        String sql = "SELECT DISTINCT SchoolYear FROM tblYearSemester ORDER BY SchoolYear DESC";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                years.add(rs.getString("SchoolYear"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return years;
    }

    public int getSemesterIDByYearAndName(String schoolYear, String semesterKeyword) {

        String sql = "SELECT YearSemesterID FROM tblYearSemester WHERE SchoolYear = ? AND SemesterName LIKE ?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, schoolYear);
            ps.setString(2, "%" + semesterKeyword + "%");

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("YearSemesterID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean setActive(int id, boolean active) {
        String sql = "UPDATE tblYearSemester SET IsActive = ? WHERE YearSemesterID = ?";
        try (java.sql.Connection conn = DBUtil.getConnection();
                java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, active);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
