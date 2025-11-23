package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.tblClass;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassDAO {

    private String jdbcURL = "jdbc:sqlserver://localhost:1433;databaseName=YourDB";
    private String jdbcUsername = "sa";
    private String jdbcPassword = "your_password";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    // ------------------- CRUD -------------------

    // ADD
    public void add(tblClass c) throws SQLException {
        String sql = "INSERT INTO tblClass (className, gradeID, cohortID, maxStudents, currentStudents, schoolYear, isActive) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getClassName());
            ps.setInt(2, c.getGradeID());
            if (c.getCohortID() != null)
                ps.setInt(3, c.getCohortID());
            else
                ps.setNull(3, Types.INTEGER);
            ps.setInt(4, c.getMaxStudents());
            ps.setInt(5, c.getCurrentStudents());
            ps.setString(6, c.getSchoolYear());
            ps.setBoolean(7, c.isActive());
            ps.executeUpdate();
        }
    }

    // GET ALL
    public List<tblClass> getAll() throws SQLException {
        List<tblClass> list = new ArrayList<>();
        String sql = "SELECT * FROM tblClass";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                tblClass c = new tblClass();
                c.setClassID(rs.getInt("classID"));
                c.setClassName(rs.getString("className"));
                c.setGradeID(rs.getInt("gradeID"));
                int cohortID = rs.getInt("cohortID");
                c.setCohortID(rs.wasNull() ? null : cohortID);
                c.setMaxStudents(rs.getInt("maxStudents"));
                c.setCurrentStudents(rs.getInt("currentStudents"));
                c.setSchoolYear(rs.getString("schoolYear"));
                c.setActive(rs.getBoolean("isActive"));
                list.add(c);
            }
        }
        return list;
    }

    // GET BY ID
    public tblClass getById(int id) throws SQLException {
        tblClass c = null;
        String sql = "SELECT * FROM tblClass WHERE classID = ?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    c = new tblClass();
                    c.setClassID(rs.getInt("classID"));
                    c.setClassName(rs.getString("className"));
                    c.setGradeID(rs.getInt("gradeID"));
                    int cohortID = rs.getInt("cohortID");
                    c.setCohortID(rs.wasNull() ? null : cohortID);
                    c.setMaxStudents(rs.getInt("maxStudents"));
                    c.setCurrentStudents(rs.getInt("currentStudents"));
                    c.setSchoolYear(rs.getString("schoolYear"));
                    c.setActive(rs.getBoolean("isActive"));
                }
            }
        }
        return c;
    }

    // UPDATE
    public void update(tblClass c) throws SQLException {
        String sql = "UPDATE tblClass SET className=?, gradeID=?, cohortID=?, maxStudents=?, currentStudents=?, schoolYear=?, isActive=? "
                +
                "WHERE classID=?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getClassName());
            ps.setInt(2, c.getGradeID());
            if (c.getCohortID() != null)
                ps.setInt(3, c.getCohortID());
            else
                ps.setNull(3, Types.INTEGER);
            ps.setInt(4, c.getMaxStudents());
            ps.setInt(5, c.getCurrentStudents());
            ps.setString(6, c.getSchoolYear());
            ps.setBoolean(7, c.isActive());
            ps.setInt(8, c.getClassID());
            ps.executeUpdate();
        }
    }

    // DELETE
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM tblClass WHERE classID = ?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
