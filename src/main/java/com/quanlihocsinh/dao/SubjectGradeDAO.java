package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.Subject;
import com.quanlihocsinh.model.SubjectGrade;
import com.quanlihocsinh.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SubjectGradeDAO {

    private String lastErrorMessage;

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }

    private void setLastError(SQLException e) {
        lastErrorMessage = e.getClass().getSimpleName() + ": " + e.getMessage();
        if (e.getSQLState() != null) {
            lastErrorMessage += " (SQLState=" + e.getSQLState() + ", ErrorCode=" + e.getErrorCode() + ")";
        }
        e.printStackTrace();
    }

    private SubjectGrade map(ResultSet rs) throws SQLException {
        SubjectGrade sg = new SubjectGrade();
        sg.setSubjectID(rs.getInt("SubjectID"));
        sg.setSubjectName(rs.getString("SubjectName"));
        sg.setNumberOfLesson((Integer) rs.getObject("NumberOfLesson"));
        sg.setSemester(rs.getString("Semester"));
        sg.setIsActive(rs.getBoolean("IsActive"));
        sg.setDepartmentID((Integer) rs.getObject("DepartmentID"));
        sg.setGradeID(rs.getInt("GradeID"));
        sg.setPeriods(rs.getInt("Periods"));
        return sg;
    }

    private void ensureTableExists(Connection conn) throws SQLException {
        String sql = "IF OBJECT_ID('dbo.tblSubject_Grade', 'U') IS NULL " +
                "BEGIN " +
                "CREATE TABLE dbo.tblSubject_Grade (" +
                "SubjectID INT NOT NULL, " +
                "GradeID INT NOT NULL, " +
                "Periods INT NOT NULL, " +
                "CONSTRAINT PK_tblSubject_Grade PRIMARY KEY (SubjectID, GradeID), " +
                "CONSTRAINT FK_tblSubject_Grade_Subject FOREIGN KEY (SubjectID) REFERENCES dbo.tblSubject (SubjectID) ON DELETE CASCADE, "
                +
                "CONSTRAINT FK_tblSubject_Grade_Grade FOREIGN KEY (GradeID) REFERENCES dbo.tblGrade (GradeID) ON DELETE CASCADE"
                +
                "); " +
                "CREATE INDEX IX_tblSubject_Grade_GradeID ON dbo.tblSubject_Grade (GradeID); " +
                "END";

        try (Statement st = conn.createStatement()) {
            st.execute(sql);
        }
    }

    public List<SubjectGrade> getSubjectsByGrade(int gradeID) {
        List<SubjectGrade> list = new ArrayList<>();
        String sql = "SELECT sg.SubjectID, sg.GradeID, sg.Periods, s.SubjectName, s.NumberOfLesson, s.Semester, s.IsActive, s.DepartmentID "
                +
                "FROM dbo.tblSubject_Grade sg " +
                "JOIN dbo.tblSubject s ON sg.SubjectID = s.SubjectID " +
                "WHERE sg.GradeID = ? " +
                "ORDER BY s.SubjectName";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ensureTableExists(conn);
            ps.setInt(1, gradeID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        } catch (SQLException e) {
            setLastError(e);
        }

        return list;
    }

    public boolean assignSubjectToGrade(int subjectID, int gradeID, int periods) {
        String updateSql = "UPDATE dbo.tblSubject_Grade SET Periods = ? WHERE SubjectID = ? AND GradeID = ?";
        String insertSql = "INSERT INTO dbo.tblSubject_Grade (SubjectID, GradeID, Periods) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection()) {
            ensureTableExists(conn);
            conn.setAutoCommit(false);

            boolean updated;
            try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                ps.setInt(1, periods);
                ps.setInt(2, subjectID);
                ps.setInt(3, gradeID);
                updated = ps.executeUpdate() > 0;
            }

            if (!updated) {
                try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                    ps.setInt(1, subjectID);
                    ps.setInt(2, gradeID);
                    ps.setInt(3, periods);
                    ps.executeUpdate();
                }
            }

            conn.commit();
            lastErrorMessage = null;
            return true;
        } catch (SQLException e) {
            setLastError(e);
        }

        return false;
    }

    public boolean removeSubjectFromGrade(int subjectID, int gradeID) {
        String sql = "DELETE FROM dbo.tblSubject_Grade WHERE SubjectID = ? AND GradeID = ?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ensureTableExists(conn);
            ps.setInt(1, subjectID);
            ps.setInt(2, gradeID);
            boolean removed = ps.executeUpdate() > 0;
            if (removed) {
                lastErrorMessage = null;
            }
            return removed;
        } catch (SQLException e) {
            setLastError(e);
        }
        return false;
    }

    public List<Subject> getSubjectsNotInGrade(int gradeID) {
        List<Subject> list = new ArrayList<>();
        String sql = "SELECT * FROM dbo.tblSubject WHERE SubjectID NOT IN (SELECT SubjectID FROM dbo.tblSubject_Grade WHERE GradeID = ?) ORDER BY SubjectName";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ensureTableExists(conn);
            ps.setInt(1, gradeID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Subject subject = new Subject();
                    subject.setSubjectID(rs.getInt("SubjectID"));
                    subject.setSubjectName(rs.getString("SubjectName"));
                    subject.setNumberOfLesson((Integer) rs.getObject("NumberOfLesson"));
                    subject.setSemester(rs.getString("Semester"));
                    subject.setIsActive(rs.getBoolean("IsActive"));
                    subject.setDepartmentID((Integer) rs.getObject("DepartmentID"));
                    list.add(subject);
                }
            }
        } catch (SQLException e) {
            setLastError(e);
        }

        return list;
    }

    public List<Integer> getSubjectIdsByGrade(int gradeID) {
        List<Integer> list = new ArrayList<>();
        String sql = "SELECT SubjectID FROM dbo.tblSubject_Grade WHERE GradeID = ? ORDER BY SubjectID";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ensureTableExists(conn);
            ps.setInt(1, gradeID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getInt("SubjectID"));
                }
            }
        } catch (SQLException e) {
            setLastError(e);
        }

        return list;
    }
}
