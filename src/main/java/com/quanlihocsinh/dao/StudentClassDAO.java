package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.Student;
import com.quanlihocsinh.model.StudentClass;
import com.quanlihocsinh.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentClassDAO {

    // Thêm học sinh vào lớp
    public boolean add(StudentClass sc) throws SQLException {
        String sql = "INSERT INTO tblStudentClass(studentID, classID, cohortID, isActive, yearSemesterID) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sc.getStudentID());
            ps.setInt(2, sc.getClassID());
            ps.setInt(3, sc.getCohortID());
            ps.setBoolean(4, sc.isActive());
            ps.setInt(5, sc.getYearSemesterID());
            return ps.executeUpdate() > 0;
        }
    }

    // Lấy danh sách học sinh trong lớp (có tên lớp)
    public List<StudentClass> getByClassAndYear(int classID, int yearSemesterID) throws SQLException {
        List<StudentClass> list = new ArrayList<>();
        String sql = "SELECT sc.*, c.ClassName " +
                "FROM tblStudentClass sc " +
                "LEFT JOIN tblClass c ON sc.classID = c.ClassID " +
                "WHERE sc.classID=? AND sc.yearSemesterID=? AND sc.isActive=1";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, classID);
            ps.setInt(2, yearSemesterID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                StudentClass sc = new StudentClass();
                sc.setStudentClassID(rs.getInt("studentClassID"));
                sc.setStudentID(rs.getString("studentID"));
                sc.setClassID(rs.getInt("classID"));
                sc.setCohortID(rs.getInt("cohortID"));
                sc.setActive(rs.getBoolean("isActive"));
                sc.setYearSemesterID(rs.getInt("yearSemesterID"));
                list.add(sc);
            }
        }
        return list;
    }

    // Lấy danh sách học sinh chưa thuộc lớp
    public List<Student> getStudentsNotInClass(int classID, int yearSemesterID) throws SQLException {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM tblStudent WHERE studentID NOT IN " +
                "(SELECT studentID FROM tblStudentClass WHERE classID=? AND yearSemesterID=? AND isActive=1)";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, classID);
            ps.setInt(2, yearSemesterID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("id"));
                s.setStudentID(rs.getString("studentID"));
                s.setFullName(rs.getString("fullName"));
                list.add(s);
            }
        }
        return list;
    }

    // Xóa học sinh khỏi lớp
    public boolean delete(int studentClassID) throws SQLException {
        String sql = "DELETE FROM tblStudentClass WHERE studentClassID=?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentClassID);
            return ps.executeUpdate() > 0;
        }
    }

    // Lấy classID và yearSemesterID mặc định
    public int[] getDefaultClassAndYear() throws SQLException {
        String sql = "SELECT TOP 1 classID, yearSemesterID FROM tblStudentClass ORDER BY studentClassID";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next())
                return new int[] { rs.getInt("classID"), rs.getInt("yearSemesterID") };
        }
        return new int[] { 1, 1 };
    }
}
