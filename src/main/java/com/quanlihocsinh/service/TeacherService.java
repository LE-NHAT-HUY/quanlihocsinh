package com.quanlihocsinh.service;

import com.quanlihocsinh.model.Teacher;
import com.quanlihocsinh.util.DBUtil;
import com.quanlihocsinh.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class TeacherService {

    public static class RegistrationResult {
        private final int teacherDbId;
        private final int personId;
        private final int userId;
        private final String rawPassword;

        public RegistrationResult(int teacherDbId, int personId, int userId, String rawPassword) {
            this.teacherDbId = teacherDbId;
            this.personId = personId;
            this.userId = userId;
            this.rawPassword = rawPassword;
        }

        public int getTeacherDbId() {
            return teacherDbId;
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

    public RegistrationResult createTeacherWithAccount(Teacher teacher) throws SQLException {
        if (teacher == null) {
            throw new IllegalArgumentException("Teacher is required");
        }
        if (teacher.getTeacherID() == null || teacher.getTeacherID().trim().isEmpty()) {
            throw new IllegalArgumentException("Teacher code is required");
        }
        if (teacher.getFullName() == null || teacher.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Teacher name is required");
        }

        Connection conn = DBUtil.getConnection();
        try {
            conn.setAutoCommit(false);

            int personId = insertPerson(conn, teacher);
            int teacherDbId = insertTeacher(conn, teacher, personId);
            String rawPassword = PasswordUtil.generateRandomPassword(8);
            String hashedPassword = PasswordUtil.hashPassword(rawPassword);
            int userId = insertUser(conn, teacher.getTeacherID(), hashedPassword, personId);

            conn.commit();

            teacher.setPersonId(personId);
            teacher.setId(teacherDbId);

            return new RegistrationResult(teacherDbId, personId, userId, rawPassword);
        } catch (SQLException | RuntimeException ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    private int insertPerson(Connection conn, Teacher teacher) throws SQLException {
        String sql = "INSERT INTO Person(fullname, birth, gender, address, phone, images, is_active) VALUES(?, ?, ?, ?, ?, ?, 1)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, teacher.getFullName());
            if (teacher.getBirth() != null) {
                ps.setDate(2, new java.sql.Date(teacher.getBirth().getTime()));
            } else {
                ps.setNull(2, Types.DATE);
            }
            ps.setString(3, teacher.getGender());
            ps.setString(4, teacher.getAddress());
            ps.setString(5, teacher.getNumberPhone());
            ps.setString(6, teacher.getImages());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Cannot generate person_id");
    }

    private int insertTeacher(Connection conn, Teacher teacher, int personId) throws SQLException {
        String sql = "INSERT INTO tblTeacher(" +
                "TeacherID, FullName, PersonID, Birth, Gender, Address, StatusTeacher, CCCD, Nation, Religion, " +
                "GroupDV, NumberPhone, NumberBHXH, IsActive, DepartmentID, Hamlet, Commune, Province, Nationality, Images) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, teacher.getTeacherID());
            ps.setString(2, teacher.getFullName());
            ps.setInt(3, personId);

            if (teacher.getBirth() != null) {
                ps.setDate(4, new java.sql.Date(teacher.getBirth().getTime()));
            } else {
                ps.setNull(4, Types.DATE);
            }

            ps.setString(5, teacher.getGender());
            ps.setString(6, teacher.getAddress());
            ps.setString(7, teacher.getStatusTeacher());
            ps.setString(8, teacher.getCccd());
            ps.setString(9, teacher.getNation());
            ps.setString(10, teacher.getReligion());
            ps.setString(11, teacher.getGroupDV());
            ps.setString(12, teacher.getNumberPhone());
            ps.setString(13, teacher.getNumberBHXH());
            ps.setBoolean(14, teacher.isIsActive());

            if (teacher.getDepartmentID() != null) {
                ps.setInt(15, teacher.getDepartmentID());
            } else {
                ps.setNull(15, Types.INTEGER);
            }

            if (teacher.getHamlet() != null) {
                ps.setInt(16, teacher.getHamlet());
            } else {
                ps.setNull(16, Types.INTEGER);
            }

            ps.setString(17, teacher.getCommune());
            ps.setString(18, teacher.getProvince());
            ps.setString(19, teacher.getNationality());
            ps.setString(20, teacher.getImages());

            int affected = ps.executeUpdate();
            if (affected <= 0) {
                throw new SQLException("Insert teacher failed");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Cannot generate teacher ID");
    }

    private int insertUser(Connection conn, String username, String hashedPassword, int personId) throws SQLException {
        String sql = "INSERT INTO Users(username, password_hash, role_id, person_id, is_active, created_at) VALUES(?, ?, 2, ?, 1, GETDATE())";
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