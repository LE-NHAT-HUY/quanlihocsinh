package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.Teacher;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {
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

    // Map ResultSet thành Teacher
    private Teacher mapResultSetToTeacher(ResultSet rs) throws SQLException {
        Teacher t = new Teacher();
        t.setId(rs.getInt("ID"));
        t.setTeacherID(rs.getString("TeacherID"));
        t.setFullName(rs.getString("FullName"));
        t.setBirth(rs.getDate("Birth"));
        t.setGender(rs.getString("Gender"));
        t.setAddress(rs.getString("Address"));
        t.setStatusTeacher(rs.getString("StatusTeacher"));
        t.setCccd(rs.getString("CCCD"));
        t.setNation(rs.getString("Nation"));
        t.setReligion(rs.getString("Religion"));
        t.setGroupDV(rs.getString("GroupDV"));
        t.setNumberPhone(rs.getString("NumberPhone"));
        t.setNumberBHXH(rs.getString("NumberBHXH"));
        t.setIsActive(rs.getBoolean("IsActive"));

        int depID = rs.getInt("DepartmentID");
        t.setDepartmentID(rs.wasNull() ? null : depID);

        int hamlet = rs.getInt("Hamlet");
        t.setHamlet(rs.wasNull() ? null : hamlet);

        t.setCommune(rs.getString("Commune"));
        t.setProvince(rs.getString("Province"));
        t.setNationality(rs.getString("Nationality"));
        t.setImages(rs.getString("Images"));

        return t;
    }

    // Lấy tất cả giáo viên
    public List<Teacher> getAll() {
        List<Teacher> list = new ArrayList<>();
        String sql = "SELECT * FROM tblTeacher ORDER BY ID";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToTeacher(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy giáo viên theo ID
    public Teacher getById(int id) {
        Teacher t = null;
        String sql = "SELECT * FROM tblTeacher WHERE ID=?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    t = mapResultSetToTeacher(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }

    // Thêm mới giáo viên
    public void add(Teacher t) {
        String sql = "INSERT INTO tblTeacher(TeacherID, FullName, Birth, Gender, Address, StatusTeacher, CCCD, Nation, Religion, GroupDV, NumberPhone, NumberBHXH, IsActive, DepartmentID, Hamlet, Commune, Province, Nationality, Images) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t.getTeacherID());
            ps.setString(2, t.getFullName());
            ps.setDate(3, t.getBirth() != null ? new java.sql.Date(t.getBirth().getTime()) : null);
            ps.setString(4, t.getGender());
            ps.setString(5, t.getAddress());
            ps.setString(6, t.getStatusTeacher());
            ps.setString(7, t.getCccd());
            ps.setString(8, t.getNation());
            ps.setString(9, t.getReligion());
            ps.setString(10, t.getGroupDV());
            ps.setString(11, t.getNumberPhone());
            ps.setString(12, t.getNumberBHXH());
            ps.setBoolean(13, t.isIsActive());
            if (t.getDepartmentID() != null)
                ps.setInt(14, t.getDepartmentID());
            else
                ps.setNull(14, Types.INTEGER);
            if (t.getHamlet() != null)
                ps.setInt(15, t.getHamlet());
            else
                ps.setNull(15, Types.INTEGER);
            ps.setString(16, t.getCommune());
            ps.setString(17, t.getProvince());
            ps.setString(18, t.getNationality());
            ps.setString(19, t.getImages());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cập nhật giáo viên
    public void update(Teacher t) {
        String sql = "UPDATE tblTeacher SET TeacherID=?, FullName=?, Birth=?, Gender=?, Address=?, StatusTeacher=?, CCCD=?, Nation=?, Religion=?, GroupDV=?, NumberPhone=?, NumberBHXH=?, IsActive=?, DepartmentID=?, Hamlet=?, Commune=?, Province=?, Nationality=?, Images=? "
                +
                "WHERE ID=?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t.getTeacherID());
            ps.setString(2, t.getFullName());
            ps.setDate(3, t.getBirth() != null ? new java.sql.Date(t.getBirth().getTime()) : null);
            ps.setString(4, t.getGender());
            ps.setString(5, t.getAddress());
            ps.setString(6, t.getStatusTeacher());
            ps.setString(7, t.getCccd());
            ps.setString(8, t.getNation());
            ps.setString(9, t.getReligion());
            ps.setString(10, t.getGroupDV());
            ps.setString(11, t.getNumberPhone());
            ps.setString(12, t.getNumberBHXH());
            ps.setBoolean(13, t.isIsActive());
            if (t.getDepartmentID() != null)
                ps.setInt(14, t.getDepartmentID());
            else
                ps.setNull(14, Types.INTEGER);
            if (t.getHamlet() != null)
                ps.setInt(15, t.getHamlet());
            else
                ps.setNull(15, Types.INTEGER);
            ps.setString(16, t.getCommune());
            ps.setString(17, t.getProvince());
            ps.setString(18, t.getNationality());
            ps.setString(19, t.getImages());
            ps.setInt(20, t.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa giáo viên
    public void delete(int id) {
        String sql = "DELETE FROM tblTeacher WHERE ID=?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Toggle trạng thái hoạt động
    public void toggleStatus(int id, boolean status) {
        String sql = "UPDATE tblTeacher SET IsActive=? WHERE ID=?";
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
