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

    // Bỏ "throws SQLException" ở dòng này để Controller không bị lỗi
    public List<YearSemester> getAllActive() {
        List<YearSemester> list = new ArrayList<>();
        // Lấy các học kỳ đang kích hoạt (IsActive = 1)
        String sql = "SELECT * FROM tblYearSemester WHERE IsActive = 1 ORDER BY YearSemesterID DESC";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                YearSemester ys = new YearSemester();
                ys.setYearSemesterID(rs.getInt("YearSemesterID"));
                ys.setSemesterName(rs.getString("SemesterName"));
                ys.setSchoolYear(rs.getString("SchoolYear"));

                // --- SỬA LẠI DÒNG NÀY ---
                // Dùng setIsActive thay vì setActive
                ys.setIsActive(rs.getBoolean("IsActive"));

                list.add(ys);
            }
        } catch (Exception e) {
            // Bắt lỗi tại đây để Controller sạch sẽ
            e.printStackTrace();
        }
        return list;
    }

}
