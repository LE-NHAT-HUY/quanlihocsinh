package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.AdminMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AdminMenuDAO {

    @Autowired
    private DataSource dataSource;

    public List<AdminMenu> getMenus() {
        List<AdminMenu> list = new ArrayList<>();
        String sql = "SELECT * FROM AdminMenu WHERE IsActive = 1 ORDER BY ItemOrder";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                AdminMenu m = new AdminMenu();
                m.setAdminMenulD(rs.getLong("AdminMenulD"));
                m.setItemName(rs.getString("ItemName"));
                m.setItemLevel(rs.getInt("ItemLevel"));
                m.setParentLevel(rs.getInt("ParentLevel"));
                m.setItemOrder(rs.getInt("ItemOrder"));
                m.setIsActive(rs.getBoolean("IsActive"));
                m.setItemTarget(rs.getString("ItemTarget"));
                m.setAreaName(rs.getString("AreaName"));
                m.setControllerName(rs.getString("ControllerName"));
                m.setActionName(rs.getString("ActionName"));
                m.setIcon(rs.getString("Icon"));
                m.setIdName(rs.getString("IdName"));

                list.add(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
