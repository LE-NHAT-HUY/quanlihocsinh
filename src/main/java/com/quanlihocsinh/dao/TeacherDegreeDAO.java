package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.TeacherDegree;
import com.quanlihocsinh.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class TeacherDegreeDAO {

    private TeacherDegree map(ResultSet rs) throws SQLException {
        TeacherDegree degree = new TeacherDegree();
        degree.setDegreeID(rs.getInt("DegreeID"));
        degree.setTeacherID(rs.getInt("TeacherID"));
        degree.setDegreeName(rs.getString("DegreeName"));
        degree.setMajor(rs.getString("Major"));

        int graduationYear = rs.getInt("GraduationYear");
        degree.setGraduationYear(rs.wasNull() ? null : graduationYear);

        degree.setGraduationSchool(rs.getString("GraduationSchool"));
        degree.setAttachmentPath(rs.getString("AttachmentPath"));

        try {
            degree.setTeacherName(rs.getString("TeacherName"));
        } catch (SQLException ignored) {
        }

        return degree;
    }

    public List<TeacherDegree> getAllByTeacher(int teacherID) {
        List<TeacherDegree> list = new ArrayList<>();
        String sql = "SELECT d.*, t.FullName AS TeacherName " +
                "FROM tblTeacherDegree d " +
                "JOIN tblTeacher t ON d.TeacherID = t.ID " +
                "WHERE d.TeacherID = ? " +
                "ORDER BY d.DegreeID DESC";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teacherID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<TeacherDegree> getAll() {
        List<TeacherDegree> list = new ArrayList<>();
        String sql = "SELECT d.*, t.FullName AS TeacherName " +
                "FROM tblTeacherDegree d " +
                "JOIN tblTeacher t ON d.TeacherID = t.ID " +
                "ORDER BY d.DegreeID DESC";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public TeacherDegree getById(int degreeID) {
        String sql = "SELECT d.*, t.FullName AS TeacherName " +
                "FROM tblTeacherDegree d " +
                "JOIN tblTeacher t ON d.TeacherID = t.ID " +
                "WHERE d.DegreeID = ?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, degreeID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void add(TeacherDegree degree) {
        String sql = "INSERT INTO tblTeacherDegree(TeacherID, DegreeName, Major, GraduationYear, GraduationSchool, AttachmentPath) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, degree.getTeacherID());
            ps.setString(2, degree.getDegreeName());
            ps.setString(3, degree.getMajor());
            if (degree.getGraduationYear() != null) {
                ps.setInt(4, degree.getGraduationYear());
            } else {
                ps.setNull(4, Types.INTEGER);
            }
            ps.setString(5, degree.getGraduationSchool());
            ps.setString(6, degree.getAttachmentPath());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to insert teacher degree", e);
        }
    }

    public void update(TeacherDegree degree) {
        String sql = "UPDATE tblTeacherDegree SET TeacherID=?, DegreeName=?, Major=?, GraduationYear=?, GraduationSchool=?, AttachmentPath=? WHERE DegreeID=?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, degree.getTeacherID());
            ps.setString(2, degree.getDegreeName());
            ps.setString(3, degree.getMajor());
            if (degree.getGraduationYear() != null) {
                ps.setInt(4, degree.getGraduationYear());
            } else {
                ps.setNull(4, Types.INTEGER);
            }
            ps.setString(5, degree.getGraduationSchool());
            ps.setString(6, degree.getAttachmentPath());
            ps.setInt(7, degree.getDegreeID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update teacher degree", e);
        }
    }

    public void delete(int degreeID) {
        String sql = "DELETE FROM tblTeacherDegree WHERE DegreeID=?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, degreeID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete teacher degree", e);
        }
    }
}
