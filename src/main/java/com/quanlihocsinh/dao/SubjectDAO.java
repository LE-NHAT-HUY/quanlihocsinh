package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.Subject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {
    private final String URL = "jdbc:sqlserver://LENOVO\\HUY123:1433;databaseName=StudentManagementDB;integratedSecurity=true;encrypt=true;trustServerCertificate=true";
    private final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    private Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL);
    }

    public List<Subject> getAllSubjects() {
        List<Subject> list = new ArrayList<>();
        String sql = "SELECT * FROM tblSubject ORDER BY SubjectName";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Subject s = new Subject();
                s.setSubjectID(rs.getInt("SubjectID"));
                s.setSubjectName(rs.getString("SubjectName"));
                s.setNumberOfLesson((Integer) rs.getObject("NumberOfLesson"));
                s.setSemester(rs.getString("Semester"));
                s.setIsActive(rs.getBoolean("IsActive"));
                s.setDepartmentID((Integer) rs.getObject("DepartmentID"));
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Subject getSubjectById(int id) {
        Subject s = null;
        String sql = "SELECT * FROM tblSubject WHERE SubjectID=?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    s = new Subject();
                    s.setSubjectID(rs.getInt("SubjectID"));
                    s.setSubjectName(rs.getString("SubjectName"));
                    s.setNumberOfLesson((Integer) rs.getObject("NumberOfLesson"));
                    s.setSemester(rs.getString("Semester"));
                    s.setIsActive(rs.getBoolean("IsActive"));
                    s.setDepartmentID((Integer) rs.getObject("DepartmentID"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s;
    }

    public void addSubject(Subject s) {
        String sql = "INSERT INTO tblSubject(SubjectName, NumberOfLesson, Semester, IsActive, DepartmentID) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getSubjectName());
            ps.setObject(2, s.getNumberOfLesson(), Types.INTEGER);
            ps.setString(3, s.getSemester());
            ps.setBoolean(4, s.isIsActive());
            ps.setObject(5, s.getDepartmentID(), Types.INTEGER);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSubject(Subject s) {
        String sql = "UPDATE tblSubject SET SubjectName=?, NumberOfLesson=?, Semester=?, IsActive=?, DepartmentID=? WHERE SubjectID=?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getSubjectName());
            ps.setObject(2, s.getNumberOfLesson(), Types.INTEGER);
            ps.setString(3, s.getSemester());
            ps.setBoolean(4, s.isIsActive());
            ps.setObject(5, s.getDepartmentID(), Types.INTEGER);
            ps.setInt(6, s.getSubjectID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSubject(int id) {
        String sql = "DELETE FROM tblSubject WHERE SubjectID=?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void toggleStatus(int id, boolean status) {
        String sql = "UPDATE tblSubject SET IsActive=? WHERE SubjectID=?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
