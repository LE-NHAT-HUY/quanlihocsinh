package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.ClassTransferOption;
import com.quanlihocsinh.model.Student;
import com.quanlihocsinh.model.StudentClass;
import com.quanlihocsinh.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
                "JOIN tblStudent s ON sc.studentID = s.studentID " +
                "WHERE sc.classID = ? AND sc.yearSemesterID = ? AND sc.isActive = 1";

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

    public List<Student> getStudentsNotInClass(int classID, int yearSemesterID) throws SQLException {
        List<Student> list = new ArrayList<>();

        String sql = "SELECT * FROM tblStudent WHERE studentID NOT IN " +
                "(SELECT studentID FROM tblStudentClass WHERE yearSemesterID = ? AND isActive = 1)";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, yearSemesterID);

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
                "WHERE sc.classID=? AND sc.isActive=1 " +
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
                sc.setYearSemesterID(rs.getInt("yearSemesterID"));

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

    private Integer extractGrade(String className) {
        if (className == null)
            return null;
        Matcher m = Pattern.compile("^(\\d+)").matcher(className.trim());
        return m.find() ? Integer.parseInt(m.group(1)) : null;
    }

    public List<ClassTransferOption> getTransferableClasses(int currentClassId) throws Exception {
        List<ClassTransferOption> result = new ArrayList<>();

        String currentNameSql = "SELECT className FROM tblClass WHERE classID = ?";
        String allClassSql = "SELECT classID, className FROM tblClass WHERE isActive = 1 AND classID <> ? ORDER BY className";

        try (Connection con = DBUtil.getConnection();
                PreparedStatement psCurrent = con.prepareStatement(currentNameSql);
                PreparedStatement psAll = con.prepareStatement(allClassSql)) {

            psCurrent.setInt(1, currentClassId);
            String currentClassName = null;
            try (ResultSet rs = psCurrent.executeQuery()) {
                if (rs.next())
                    currentClassName = rs.getString("className");
            }

            Integer currentGrade = extractGrade(currentClassName);
            if (currentGrade == null)
                return result;

            psAll.setInt(1, currentClassId);
            try (ResultSet rs = psAll.executeQuery()) {
                while (rs.next()) {
                    int classID = rs.getInt("classID");
                    String className = rs.getString("className");

                    Integer grade = extractGrade(className);
                    if (grade != null && grade.equals(currentGrade)) {
                        result.add(new ClassTransferOption(classID, className));
                    }
                }
            }
        }
        return result;
    }

    public boolean transferStudent(String studentId, int fromClassId, int toClassId, int yearSemesterId)
            throws SQLException {
        String findActiveOldSql = "SELECT studentClassID FROM tblStudentClass WHERE studentID = ? AND classID = ? AND yearSemesterID = ? AND isActive = 1";
        String findTargetSql = "SELECT TOP 1 studentClassID, isActive FROM tblStudentClass WHERE studentID = ? AND classID = ? AND yearSemesterID = ? ORDER BY studentClassID DESC";
        String deactivateOldByIdSql = "UPDATE tblStudentClass SET isActive = 0 WHERE studentClassID = ? AND isActive = 1";
        String activateTargetByIdSql = "UPDATE tblStudentClass SET isActive = 1 WHERE studentClassID = ?";
        String moveOldToNewSql = "UPDATE tblStudentClass SET classID = ? WHERE studentClassID = ? AND isActive = 1";

        Connection con = null;
        try {
            con = DBUtil.getConnection();
            con.setAutoCommit(false); // Bắt đầu Transaction

            int oldStudentClassId = 0;
            // 1. Tìm đúng bản ghi đang active ở lớp cũ, kỳ học hiện tại
            try (PreparedStatement ps = con.prepareStatement(findActiveOldSql)) {
                ps.setString(1, studentId);
                ps.setInt(2, fromClassId);
                ps.setInt(3, yearSemesterId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        oldStudentClassId = rs.getInt("studentClassID");
                    } else {
                        con.rollback();
                        return false; // Không tìm thấy bản ghi đang học ở lớp cũ
                    }
                }
            }

            int targetStudentClassId = 0;
            boolean targetIsActive = false;
            // 2. Kiểm tra bản ghi ở lớp mới (nếu đã có)
            try (PreparedStatement ps = con.prepareStatement(findTargetSql)) {
                ps.setString(1, studentId);
                ps.setInt(2, toClassId);
                ps.setInt(3, yearSemesterId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        targetStudentClassId = rs.getInt("studentClassID");
                        targetIsActive = rs.getBoolean("isActive");
                    }
                }
            }

            if (targetStudentClassId > 0 && targetIsActive) {
                con.rollback();
                return false; // Đã active ở lớp đích
            }

            if (targetStudentClassId > 0) {
                // 3a. Đã có bản ghi lớp đích (inactive): tắt lớp cũ và bật lớp đích
                try (PreparedStatement ps = con.prepareStatement(deactivateOldByIdSql)) {
                    ps.setInt(1, oldStudentClassId);
                    int updated = ps.executeUpdate();
                    if (updated == 0) {
                        con.rollback();
                        return false;
                    }
                }
                try (PreparedStatement ps = con.prepareStatement(activateTargetByIdSql)) {
                    ps.setInt(1, targetStudentClassId);
                    int updated = ps.executeUpdate();
                    if (updated == 0) {
                        con.rollback();
                        return false;
                    }
                }
            } else {
                // 3b. Chưa có bản ghi lớp đích: chuyển trực tiếp classID của bản ghi active
                try (PreparedStatement ps = con.prepareStatement(moveOldToNewSql)) {
                    ps.setInt(1, toClassId);
                    ps.setInt(2, oldStudentClassId);
                    int updated = ps.executeUpdate();
                    if (updated == 0) {
                        con.rollback();
                        return false;
                    }
                }
            }

            con.commit(); // Xác nhận mọi thay đổi
            return true;
        } catch (Exception ex) {
            if (con != null) {
                con.rollback();
            }
            System.err.println("transferStudent failed: studentId=" + studentId
                    + ", fromClassId=" + fromClassId
                    + ", toClassId=" + toClassId
                    + ", yearSemesterId=" + yearSemesterId);
            ex.printStackTrace();
            return false;
        } finally {
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
    }

}
