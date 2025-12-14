package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.Student;
import com.quanlihocsinh.model.StudentClass;
import com.quanlihocsinh.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentClassDAO {

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

    public List<StudentClass> getByClassAndYear(int classID, int yearSemesterID) throws SQLException {
        List<StudentClass> list = new ArrayList<>();
        String sql = "SELECT sc.*, s.fullName, s.gender, s.birth, s.numberPhone, s.address " +
                "FROM tblStudentClass sc " +
                "INNER JOIN tblStudent s ON sc.studentID = s.studentID " +
                "WHERE sc.classID=? AND sc.yearSemesterID=? AND sc.isActive=1 " +
                "ORDER BY s.fullName";

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

                // Tạo đối tượng Student và gán thông tin
                Student student = new Student();
                student.setStudentID(rs.getString("studentID"));
                student.setFullName(rs.getString("fullName"));
                student.setGender(rs.getString("gender"));
                student.setBirth(rs.getDate("birth"));
                student.setNumberPhone(rs.getString("numberPhone"));
                student.setAddress(rs.getString("address"));

                // Gán Student vào StudentClass
                sc.setStudent(student);
                list.add(sc);
            }
        }
        return list;
    }

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

    public boolean delete(int studentClassID) throws SQLException {
        String sql = "DELETE FROM tblStudentClass WHERE studentClassID=?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentClassID);
            return ps.executeUpdate() > 0;
        }
    }

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

    public List<StudentClass> getByClass(int classID) throws SQLException {
        List<StudentClass> list = new ArrayList<>();
        String sql = "SELECT sc.*, s.FullName FROM tblStudentClass sc " +
                "INNER JOIN tblStudent s ON sc.StudentID = s.StudentID " +
                "WHERE sc.ClassID = ? " +
                "ORDER BY s.FullName";

        try (Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, classID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StudentClass sc = new StudentClass();
                    sc.setStudentClassID(rs.getInt("StudentClassID"));
                    sc.setStudentID(rs.getString("StudentID"));
                    sc.setYearSemesterID(rs.getInt("YearSemesterID"));

                    // Gán đối tượng Student
                    Student s = new Student();
                    s.setStudentID(rs.getString("StudentID"));
                    s.setFullName(rs.getString("FullName"));
                    sc.setStudent(s);

                    list.add(sc);
                }
            }
        }
        return list;
    }

    public List<StudentClass> getStudentsByClass(int classID) throws SQLException {
        List<StudentClass> list = new ArrayList<>();
        String sql = "SELECT sc.*, s.fullName, s.gender, s.birth, s.numberPhone, s.address " +
                "FROM tblStudentClass sc " +
                "INNER JOIN tblStudent s ON sc.studentID = s.studentID " +
                "WHERE sc.classID=? AND sc.isActive=1 " + // BỎ điều kiện yearSemesterID
                "ORDER BY s.fullName";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, classID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                StudentClass sc = new StudentClass();
                sc.setStudentClassID(rs.getInt("studentClassID"));
                sc.setStudentID(rs.getString("studentID"));
                sc.setClassID(rs.getInt("classID"));
                sc.setCohortID(rs.getInt("cohortID"));
                sc.setActive(rs.getBoolean("isActive"));
                sc.setYearSemesterID(rs.getInt("yearSemesterID")); // Vẫn lấy nhưng không dùng để filter

                Student student = new Student();
                student.setStudentID(rs.getString("studentID"));
                student.setFullName(rs.getString("fullName"));
                student.setGender(rs.getString("gender"));
                student.setBirth(rs.getDate("birth"));
                student.setNumberPhone(rs.getString("numberPhone"));
                student.setAddress(rs.getString("address"));

                sc.setStudent(student);
                list.add(sc);
            }
        }
        return list;
    }
}
