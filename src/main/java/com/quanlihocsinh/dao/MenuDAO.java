package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.menu;
import com.quanlihocsinh.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {

    private menu map(ResultSet rs) throws SQLException {
        menu m = new menu();
        m.setMenuID(rs.getInt("menuID"));
        m.setMenuName(rs.getString("menuName"));
        m.setControllerName(rs.getString("controllerName"));
        m.setActionName(rs.getString("actionName"));
        m.setLevels((Integer) rs.getObject("levels"));
        m.setParentID((Integer) rs.getObject("parentID"));
        m.setMenuOrder((Integer) rs.getObject("menuOrder"));
        m.setPosition((Integer) rs.getObject("position"));
        m.setIcon(rs.getString("icon"));
        m.setIDName(rs.getString("IDName"));
        m.setItemTarget(rs.getString("itemTarget"));
        m.setIsActive(rs.getBoolean("isActive"));
        return m;
    }

    public List<menu> getAllMenus() {
        List<menu> list = new ArrayList<>();
        String sql = "SELECT * FROM tblMenu ORDER BY menuOrder";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next())
                list.add(map(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public menu getMenuById(int id) {
        String sql = "SELECT * FROM tblMenu WHERE menuID=?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return map(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addMenu(menu m) {
        String sql = "INSERT INTO tblMenu(menuName, controllerName, actionName, levels, parentID, menuOrder, position, icon, IDName, itemTarget, isActive) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, m.getMenuName());
            ps.setString(2, m.getControllerName());
            ps.setString(3, m.getActionName());
            ps.setObject(4, m.getLevels(), Types.INTEGER);
            ps.setObject(5, m.getParentID(), Types.INTEGER);
            ps.setObject(6, m.getMenuOrder(), Types.INTEGER);
            ps.setObject(7, m.getPosition(), Types.INTEGER);
            ps.setString(8, m.getIcon());
            ps.setString(9, m.getIDName());
            ps.setString(10, m.getItemTarget());
            ps.setBoolean(11, m.isIsActive());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMenu(menu m) {
        String sql = "UPDATE tblMenu SET menuName=?, controllerName=?, actionName=?, levels=?, parentID=?, menuOrder=?, position=?, icon=?, IDName=?, itemTarget=?, isActive=? "
                + "WHERE menuID=?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, m.getMenuName());
            ps.setString(2, m.getControllerName());
            ps.setString(3, m.getActionName());
            ps.setObject(4, m.getLevels(), Types.INTEGER);
            ps.setObject(5, m.getParentID(), Types.INTEGER);
            ps.setObject(6, m.getMenuOrder(), Types.INTEGER);
            ps.setObject(7, m.getPosition(), Types.INTEGER);
            ps.setString(8, m.getIcon());
            ps.setString(9, m.getIDName());
            ps.setString(10, m.getItemTarget());
            ps.setBoolean(11, m.isIsActive());
            ps.setInt(12, m.getMenuID());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMenu(int id) {
        String sql = "DELETE FROM tblMenu WHERE menuID=?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void toggleStatus(int id, boolean status) {
        String sql = "UPDATE tblMenu SET isActive=? WHERE menuID=?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
