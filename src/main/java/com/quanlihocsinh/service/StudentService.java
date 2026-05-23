package com.quanlihocsinh.service;

import com.quanlihocsinh.model.Student;
import com.quanlihocsinh.util.DBUtil;
import com.quanlihocsinh.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class StudentService {

    public static class RegistrationResult {
        private final int studentId;
        private final int personId;
        private final int userId;
        private final String rawPassword;

        public RegistrationResult(int studentId, int personId, int userId, String rawPassword) {
            this.studentId = studentId;
            this.personId = personId;
            this.userId = userId;
            this.rawPassword = rawPassword;
        }

        public int getStudentId() {
            return studentId;
        }

        public int getPersonId() {
            return personId;
        }

        public int getUserId() {
            return userId;
        }

        public String getRawPassword() {
            return rawPassword;
        }
    }

    public RegistrationResult createStudentWithAccount(Student student) throws SQLException {
        if (student == null) {
            throw new IllegalArgumentException("Student is required");
        }
        if (student.getStudentID() == null || student.getStudentID().trim().isEmpty()) {
            throw new IllegalArgumentException("Student code is required");
        }
        if (student.getFullName() == null || student.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Student name is required");
        }

        Connection conn = DBUtil.getConnection();
        try {
            conn.setAutoCommit(false);

            int personId = insertPerson(conn, student);
            int studentDbId = insertStudent(conn, student, personId);
            String rawPassword = PasswordUtil.generateRandomPassword(8);
            String hashedPassword = PasswordUtil.hashPassword(rawPassword);
            int userId = insertUser(conn, student.getStudentID(), hashedPassword, personId);

            conn.commit();

            student.setPersonId(personId);
            student.setId(studentDbId);

            return new RegistrationResult(studentDbId, personId, userId, rawPassword);
        } catch (SQLException | RuntimeException ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    private int insertPerson(Connection conn, Student student) throws SQLException {
        String sql = "INSERT INTO Person(fullname, birth, gender, address, phone, images, is_active) VALUES(?, ?, ?, ?, ?, ?, 1)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, student.getFullName());
            if (student.getBirth() != null) {
                ps.setDate(2, new java.sql.Date(student.getBirth().getTime()));
            } else {
                ps.setNull(2, Types.DATE);
            }
            ps.setString(3, student.getGender());
            ps.setString(4, student.getAddress());
            ps.setString(5, student.getNumberPhone());
            ps.setString(6, student.getImages());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Cannot generate person_id");
    }

    private int insertStudent(Connection conn, Student student, int personId) throws SQLException {
        String sql = "INSERT INTO dbo.tblStudent " +
                "(StudentID, FullName, PersonID, Birth, Gender, Address, Nation, Religion, StatusStudent, NumberPhone, IsActive, Images, Hamlet, Commune, Province, Nationality) "
                +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, student.getStudentID());
            ps.setString(2, student.getFullName());
            ps.setInt(3, personId);

            if (student.getBirth() != null) {
                ps.setDate(4, new java.sql.Date(student.getBirth().getTime()));
            } else {
                ps.setNull(4, Types.DATE);
            }

            ps.setString(5, student.getGender());
            ps.setString(6, student.getAddress());
            ps.setString(7, student.getNation());
            ps.setString(8, student.getReligion());
            ps.setString(9, student.getStatusStudent());
            ps.setString(10, student.getNumberPhone());
            ps.setBoolean(11, student.isIsActive());
            ps.setString(12, student.getImages());
            ps.setString(13, student.getHamlet());
            ps.setString(14, student.getCommune());
            ps.setString(15, student.getProvince());
            ps.setString(16, student.getNationality());

            int affected = ps.executeUpdate();
            if (affected <= 0) {
                throw new SQLException("Insert student failed");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Cannot generate student ID");
    }

    private int insertUser(Connection conn, String username, String hashedPassword, int personId) throws SQLException {
        String sql = "INSERT INTO Users(username, password_hash, role_id, person_id, is_active, created_at) VALUES(?, ?, 3, ?, 1, GETDATE())";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, username);
            ps.setString(2, hashedPassword);
            ps.setInt(3, personId);

            int affected = ps.executeUpdate();
            if (affected <= 0) {
                throw new SQLException("Insert user failed");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Cannot generate user ID");
    }
}