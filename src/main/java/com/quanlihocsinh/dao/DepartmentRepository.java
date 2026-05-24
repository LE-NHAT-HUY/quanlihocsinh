package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.Department;
import com.quanlihocsinh.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepository {

    public List<Department> getAll() {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT DepartmentID, DepartmentName, Description, IsActive FROM tblDepartment ORDER BY DepartmentID DESC";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Department department = new Department();
                department.setDepartmentID(rs.getInt("DepartmentID"));
                department.setDepartmentName(rs.getString("DepartmentName"));
                department.setDescription(rs.getString("Description"));
                department.setIsActive(rs.getBoolean("IsActive"));
                list.add(department);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Department> findAllActive() {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT DepartmentID, DepartmentName, Description, IsActive FROM tblDepartment WHERE IsActive = 1 ORDER BY DepartmentName";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Department department = new Department();
                department.setDepartmentID(rs.getInt("DepartmentID"));
                department.setDepartmentName(rs.getString("DepartmentName"));
                department.setDescription(rs.getString("Description"));
                department.setIsActive(rs.getBoolean("IsActive"));
                list.add(department);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Department getById(int departmentID) {
        String sql = "SELECT DepartmentID, DepartmentName, Description, IsActive FROM tblDepartment WHERE DepartmentID = ?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, departmentID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Department department = new Department();
                    department.setDepartmentID(rs.getInt("DepartmentID"));
                    department.setDepartmentName(rs.getString("DepartmentName"));
                    department.setDescription(rs.getString("Description"));
                    department.setIsActive(rs.getBoolean("IsActive"));
                    return department;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Department findById(int departmentID) {
        return getById(departmentID);
    }

    public void add(Department department) {
        String sql = "INSERT INTO tblDepartment(DepartmentName, Description, IsActive) VALUES(?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, department.getDepartmentName());
            ps.setString(2, department.getDescription());
            ps.setBoolean(3, department.isIsActive());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Department department) {
        String sql = "UPDATE tblDepartment SET DepartmentName=?, Description=?, IsActive=? WHERE DepartmentID=?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, department.getDepartmentName());
            ps.setString(2, department.getDescription());
            ps.setBoolean(3, department.isIsActive());
            ps.setInt(4, department.getDepartmentID());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int departmentID) {
        String sql = "DELETE FROM tblDepartment WHERE DepartmentID = ?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, departmentID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean updateStatus(int departmentID, boolean isActive) {
        String sql = "UPDATE tblDepartment SET IsActive = ? WHERE DepartmentID = ?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, isActive);
            ps.setInt(2, departmentID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}