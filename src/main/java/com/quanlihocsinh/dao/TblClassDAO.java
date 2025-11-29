package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.tblClass;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TblClassDAO {
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

    private tblClass mapResultSetToClass(ResultSet rs) throws SQLException {
        tblClass c = new tblClass();
        c.setClassID(rs.getInt("ClassID"));
        c.setClassName(rs.getString("ClassName"));
        c.setGradeID(rs.getInt("GradeID"));

        int cohort = rs.getInt("CohortID");
        c.setCohortID(rs.wasNull() ? null : cohort);

        c.setMaxStudents(rs.getInt("MaxStudents"));
        c.setCurrentStudents(rs.getInt("CurrentStudents"));
        c.setSchoolYear(rs.getString("SchoolYear"));
        c.setActive(rs.getBoolean("IsActive"));
        return c;
    }

    // Get all
    public List<tblClass> getAll() {
        List<tblClass> list = new ArrayList<>();
        String sql = "SELECT * FROM dbo.tblClass ORDER BY ClassName";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToClass(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Get by ID
    public tblClass getById(int id) {
        tblClass c = null;
        String sql = "SELECT * FROM dbo.tblClass WHERE ClassID=?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    c = mapResultSetToClass(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    // Add
    public void add(tblClass c) {
        String sql = "INSERT INTO dbo.tblClass(ClassName, GradeID, CohortID, MaxStudents, CurrentStudents, SchoolYear, IsActive) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getClassName());
            ps.setInt(2, c.getGradeID());

            if (c.getCohortID() != null)
                ps.setInt(3, c.getCohortID());
            else
                ps.setNull(3, Types.INTEGER);

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
        String sql = "UPDATE dbo.tblClass SET ClassName=?, GradeID=?, CohortID=?, MaxStudents=?, CurrentStudents=?, SchoolYear=?, IsActive=? WHERE ClassID=?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getClassName());
            ps.setInt(2, c.getGradeID());

            if (c.getCohortID() != null)
                ps.setInt(3, c.getCohortID());
            else
                ps.setNull(3, Types.INTEGER);

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
        String sql = "DELETE FROM dbo.tblClass WHERE ClassID=?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Toggle status
    public void toggleStatus(int id, boolean status) {
        String sql = "UPDATE dbo.tblClass SET IsActive=? WHERE ClassID=?";
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
