package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.tblClass;
import com.quanlihocsinh.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TblClassDAO {

    // Map ResultSet → tblClass object
    private tblClass map(ResultSet rs) throws SQLException {
        tblClass c = new tblClass();
        c.setClassID(rs.getInt("ClassID"));
        c.setClassName(rs.getString("ClassName"));
        c.setGradeID(rs.getInt("GradeID"));

        Integer cohort = (Integer) rs.getObject("CohortID");
        c.setCohortID(cohort);

        c.setMaxStudents(rs.getInt("MaxStudents"));
        c.setCurrentStudents(rs.getInt("CurrentStudents"));
        c.setSchoolYear(rs.getString("SchoolYear"));
        c.setActive(rs.getBoolean("IsActive"));
        return c;
    }

    // Get all
    public List<tblClass> getAll() {
        List<tblClass> list = new ArrayList<>();
        String sql = "SELECT * FROM tblClass ORDER BY ClassName";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Get by ID
    public tblClass getById(int id) {
        tblClass c = null;
        String sql = "SELECT * FROM tblClass WHERE ClassID=?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    c = map(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return c;
    }

    // Add
    public void add(tblClass c) {
        String sql = "INSERT INTO tblClass(ClassName, GradeID, CohortID, MaxStudents, CurrentStudents, SchoolYear, IsActive) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getClassName());
            ps.setInt(2, c.getGradeID());
            ps.setObject(3, c.getCohortID(), Types.INTEGER);
            ps.setInt(4, c.getMaxStudents());
            ps.setInt(5, c.getCurrentStudents());
            ps.setString(6, c.getSchoolYear());
            ps.setBoolean(7, c.isActive());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update
    public void update(tblClass c) {
        String sql = "UPDATE tblClass SET ClassName=?, GradeID=?, CohortID=?, MaxStudents=?, CurrentStudents=?, SchoolYear=?, IsActive=? "
                +
                "WHERE ClassID=?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getClassName());
            ps.setInt(2, c.getGradeID());
            ps.setObject(3, c.getCohortID(), Types.INTEGER);
            ps.setInt(4, c.getMaxStudents());
            ps.setInt(5, c.getCurrentStudents());
            ps.setString(6, c.getSchoolYear());
            ps.setBoolean(7, c.isActive());
            ps.setInt(8, c.getClassID());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete
    public void delete(int id) {
        String sql = "DELETE FROM tblClass WHERE ClassID=?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Toggle status
    public void toggleStatus(int id, boolean status) {
        String sql = "UPDATE tblClass SET IsActive=? WHERE ClassID=?";

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
