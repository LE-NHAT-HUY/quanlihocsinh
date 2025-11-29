package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    // Sửa URL/USER/PASS nếu cần
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

    private Student mapResultSetToStudent(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setId(rs.getInt("ID"));
        s.setStudentID(rs.getString("StudentID"));
        s.setFullName(rs.getString("FullName"));
        // Birth is SQL date -> map to java.util.Date
        Date birth = rs.getDate("Birth");
        if (birth != null)
            s.setBirth(new java.util.Date(birth.getTime()));
        s.setGender(rs.getString("Gender"));
        s.setAddress(rs.getString("Address"));
        s.setNation(rs.getString("Nation"));
        s.setReligion(rs.getString("Religion"));
        s.setStatusStudent(rs.getString("StatusStudent"));
        s.setNumberPhone(rs.getString("NumberPhone"));
        s.setIsActive(rs.getBoolean("IsActive"));
        s.setImages(rs.getString("Images"));
        s.setHamlet(rs.getString("Hamlet"));
        s.setCommune(rs.getString("Commune"));
        s.setProvince(rs.getString("Province"));
        s.setNationality(rs.getString("Nationality"));
        return s;
    }

    public List<Student> getAll() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM dbo.tblStudent ORDER BY FullName";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSetToStudent(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Student getById(int id) {
        Student s = null;
        String sql = "SELECT * FROM dbo.tblStudent WHERE ID=?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    s = mapResultSetToStudent(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s;
    }

    public void add(Student s) {
        String sql = "INSERT INTO dbo.tblStudent(StudentID, FullName, Birth, Gender, Address, Nation, Religion, StatusStudent, NumberPhone, IsActive, Images, Hamlet, Commune, Province, Nationality) "
                +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getStudentID());
            ps.setString(2, s.getFullName());
            if (s.getBirth() != null)
                ps.setDate(3, new java.sql.Date(s.getBirth().getTime()));
            else
                ps.setNull(3, Types.DATE);
            ps.setString(4, s.getGender());
            ps.setString(5, s.getAddress());
            ps.setString(6, s.getNation());
            ps.setString(7, s.getReligion());
            ps.setString(8, s.getStatusStudent());
            ps.setString(9, s.getNumberPhone());
            ps.setBoolean(10, s.isIsActive());
            ps.setString(11, s.getImages());
            ps.setString(12, s.getHamlet());
            ps.setString(13, s.getCommune());
            ps.setString(14, s.getProvince());
            ps.setString(15, s.getNationality());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Student s) {
        String sql = "UPDATE dbo.tblStudent SET StudentID=?, FullName=?, Birth=?, Gender=?, Address=?, Nation=?, Religion=?, StatusStudent=?, NumberPhone=?, IsActive=?, Images=?, Hamlet=?, Commune=?, Province=?, Nationality=? WHERE ID=?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getStudentID());
            ps.setString(2, s.getFullName());
            if (s.getBirth() != null)
                ps.setDate(3, new java.sql.Date(s.getBirth().getTime()));
            else
                ps.setNull(3, Types.DATE);
            ps.setString(4, s.getGender());
            ps.setString(5, s.getAddress());
            ps.setString(6, s.getNation());
            ps.setString(7, s.getReligion());
            ps.setString(8, s.getStatusStudent());
            ps.setString(9, s.getNumberPhone());
            ps.setBoolean(10, s.isIsActive());
            ps.setString(11, s.getImages());
            ps.setString(12, s.getHamlet());
            ps.setString(13, s.getCommune());
            ps.setString(14, s.getProvince());
            ps.setString(15, s.getNationality());
            ps.setInt(16, s.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM dbo.tblStudent WHERE ID=?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void toggleStatus(int id, boolean status) {
        String sql = "UPDATE dbo.tblStudent SET IsActive=? WHERE ID=?";
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
