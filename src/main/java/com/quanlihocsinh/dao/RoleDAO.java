package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.Role;
import com.quanlihocsinh.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO {

    public List<Role> getAllRoles() {
        List<Role> list = new ArrayList<>();
        String sql = "SELECT RoleID, RoleName FROM Roles ORDER BY RoleID";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Role r = new Role();
                r.setRoleID(rs.getInt("RoleID"));
                r.setRoleName(rs.getString("RoleName"));
                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Integer> getRoleIdsOfUser(int userId) {
        List<Integer> list = new ArrayList<>();
        String sql = "SELECT RoleID FROM UserRoles WHERE UserID = ?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getInt("RoleID"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Role> getRolesOfUser(int userId) {
        List<Role> list = new ArrayList<>();
        String sql = "SELECT r.RoleID, r.RoleName " +
                "FROM Roles r " +
                "JOIN UserRoles ur ON r.RoleID = ur.RoleID " +
                "WHERE ur.UserID = ?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Role r = new Role();
                r.setRoleID(rs.getInt("RoleID"));
                r.setRoleName(rs.getString("RoleName"));
                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean updateUserRoles(int userId, List<Integer> roles) {
        String deleteSql = "DELETE FROM UserRoles WHERE UserID = ?";
        String insertSql = "INSERT INTO UserRoles(UserID, RoleID) VALUES(?, ?)";

        try (Connection conn = DBUtil.getConnection()) {

            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(deleteSql)) {
                ps.setInt(1, userId);
                ps.executeUpdate();
            }

            if (roles != null) {
                try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                    for (int roleId : roles) {
                        ps.setInt(1, userId);
                        ps.setInt(2, roleId);
                        ps.executeUpdate();
                    }
                }
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
