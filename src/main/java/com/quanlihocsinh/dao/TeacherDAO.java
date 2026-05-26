package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.Teacher;
import com.quanlihocsinh.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {

    private Teacher mapResultSetToTeacher(ResultSet rs) throws SQLException {
        Teacher t = new Teacher();
        t.setId(rs.getInt("ID"));
        t.setPersonId(rs.getInt("PersonID"));
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
        t.setEmail(rs.getString("Email"));
        t.setNumberBHXH(rs.getString("NumberBHXH"));
        t.setIsActive(rs.getBoolean("IsActive"));
        t.setPosition(rs.getString("Position"));

        int depID = rs.getInt("DepartmentID");
        t.setDepartmentID(rs.wasNull() ? null : depID);

        int hamlet = rs.getInt("Hamlet");
        t.setHamlet(rs.wasNull() ? null : hamlet);

        t.setCommune(rs.getString("Commune"));
        t.setProvince(rs.getString("Province"));
        t.setNationality(rs.getString("Nationality"));
        t.setEmergencyContactName(rs.getString("EmergencyContactName"));
        t.setEmergencyPhone(rs.getString("EmergencyContactPhone"));
        t.setTaxCode(rs.getString("TaxCode"));
        t.setBankName(rs.getString("BankName"));
        t.setAccountNumber(rs.getString("BankAccount"));
        t.setImages(rs.getString("Images"));

        return t;
    }

    public List<Teacher> getAll() {
        List<Teacher> list = new ArrayList<>();
        String sql = "SELECT * FROM tblTeacher ORDER BY ID DESC";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToTeacher(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Teacher getById(int id) {
        String sql = "SELECT * FROM tblTeacher WHERE ID=?";
        Teacher t = null;

        try (Connection conn = DBUtil.getConnection();
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

    public void add(Teacher t) {
        String sql = "INSERT INTO tblTeacher(TeacherID, FullName, Birth, Gender, Address, StatusTeacher, CCCD, Nation, Religion, GroupDV, NumberPhone, Email, NumberBHXH, IsActive, Position, DepartmentID, Hamlet, Commune, Province, Nationality, EmergencyContactName, EmergencyContactPhone, TaxCode, BankName, BankAccount, Images) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
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
            ps.setString(12, t.getEmail());
            ps.setString(13, t.getNumberBHXH());
            ps.setBoolean(14, t.isIsActive());
            ps.setString(15, t.getPosition());

            if (t.getDepartmentID() != null)
                ps.setInt(16, t.getDepartmentID());
            else
                ps.setNull(16, Types.INTEGER);

            if (t.getHamlet() != null)
                ps.setInt(17, t.getHamlet());
            else
                ps.setNull(17, Types.INTEGER);

            ps.setString(18, t.getCommune());
            ps.setString(19, t.getProvince());
            ps.setString(20, t.getNationality());
            ps.setString(21, t.getEmergencyContactName());
            ps.setString(22, t.getEmergencyPhone());
            ps.setString(23, t.getTaxCode());
            ps.setString(24, t.getBankName());
            ps.setString(25, t.getAccountNumber());
            ps.setString(26, t.getImages());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Teacher t) {
        String sql = "UPDATE tblTeacher SET TeacherID=?, FullName=?, Birth=?, Gender=?, Address=?, StatusTeacher=?, CCCD=?, Nation=?, Religion=?, GroupDV=?, NumberPhone=?, Email=?, NumberBHXH=?, IsActive=?, Position=?, DepartmentID=?, Hamlet=?, Commune=?, Province=?, Nationality=?, EmergencyContactName=?, EmergencyContactPhone=?, TaxCode=?, BankName=?, BankAccount=?, Images=? "
                + "WHERE ID=?";

        try (Connection conn = DBUtil.getConnection();
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
            ps.setString(12, t.getEmail());
            ps.setString(13, t.getNumberBHXH());
            ps.setBoolean(14, t.isIsActive());
            ps.setString(15, t.getPosition());

            if (t.getDepartmentID() != null)
                ps.setInt(16, t.getDepartmentID());
            else
                ps.setNull(16, Types.INTEGER);

            if (t.getHamlet() != null)
                ps.setInt(17, t.getHamlet());
            else
                ps.setNull(17, Types.INTEGER);

            ps.setString(18, t.getCommune());
            ps.setString(19, t.getProvince());
            ps.setString(20, t.getNationality());
            ps.setString(21, t.getEmergencyContactName());
            ps.setString(22, t.getEmergencyPhone());
            ps.setString(23, t.getTaxCode());
            ps.setString(24, t.getBankName());
            ps.setString(25, t.getAccountNumber());
            ps.setString(26, t.getImages());
            ps.setInt(27, t.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM tblTeacher WHERE ID=?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void toggleStatus(int id, boolean status) {
        String sql = "UPDATE tblTeacher SET IsActive=? WHERE ID=?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateStatus(int id, boolean isActive) {
        String sql = "UPDATE tblTeacher SET IsActive = ? WHERE ID = ?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, isActive);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Teacher> findAll() {
        List<Teacher> list = new ArrayList<>();
        String sql = "SELECT * FROM tblTeacher";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Teacher t = new Teacher();
                t.setId(rs.getInt("ID"));
                t.setPersonId(rs.getInt("PersonID"));
                t.setTeacherID(rs.getString("TeacherID"));
                t.setFullName(rs.getString("FullName"));
                list.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
