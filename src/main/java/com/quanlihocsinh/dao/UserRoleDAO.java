package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.Role;
import com.quanlihocsinh.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDAO {

    public List<Role> getRolesByUserId(int userId) {
        List<Role> list = new ArrayList<>();
        String sql = "SELECT r.RoleID, r.RoleName FROM UserRoles ur " +
                "JOIN Roles r ON ur.RoleID = r.RoleID " +
                "WHERE ur.UserID = ?";

        try (Connection con = DBUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Role r = new Role(rs.getInt("RoleID"), rs.getString("RoleName"));
                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
