package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.ScoreLogDTO;
import com.quanlihocsinh.model.ScoreLog;
import com.quanlihocsinh.util.DBUtil;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreLogDAO {

    public void insert(Connection conn, ScoreLog log) throws SQLException {
        insertBatch(conn, Collections.singletonList(log));
    }

    public void insertBatch(Connection conn, List<ScoreLog> logs) throws SQLException {
        if (logs == null || logs.isEmpty()) {
            return;
        }

        boolean hasClassID = hasColumn(conn, "ScoreLog", "ClassID");
        String sql = hasClassID
                ? "INSERT INTO ScoreLog (ClassID, TeacherID, StudentID, SubjectID, SemesterID, ActionType, ChangeContent, ChangeDate) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, GETDATE())"
                : "INSERT INTO ScoreLog (TeacherID, StudentID, SubjectID, SemesterID, ActionType, ChangeContent, ChangeDate) "
                        + "VALUES (?, ?, ?, ?, ?, ?, GETDATE())";

        // Debug logging
        System.out.println("[ScoreLogDAO] insertBatch called; hasClassID=" + hasClassID + ", logsSize=" + logs.size());
        System.out.println("[ScoreLogDAO] SQL=" + sql);

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (ScoreLog log : logs) {
                int index = 1;
                if (hasClassID) {
                    ps.setInt(index++, log.getClassID());
                }
                ps.setInt(index++, log.getTeacherID());
                ps.setString(index++, log.getStudentID());
                ps.setInt(index++, log.getSubjectID());
                ps.setInt(index++, log.getSemesterID());
                ps.setString(index++, log.getActionType());
                ps.setString(index++, log.getChangeContent());
                // per-log debug
                System.out.println("[ScoreLogDAO] addBatch params: classID="
                        + (hasClassID ? String.valueOf(log.getClassID()) : "<no-col>")
                        + ", teacherID=" + log.getTeacherID() + ", studentID=" + log.getStudentID()
                        + ", subjectID=" + log.getSubjectID() + ", semesterID=" + log.getSemesterID()
                        + ", actionType=" + log.getActionType());
                ps.addBatch();
            }
            int[] results = ps.executeBatch();
            System.out.println(
                    "[ScoreLogDAO] executeBatch completed; results length=" + (results != null ? results.length : 0));
        }
    }

    public List<ScoreLogDTO> getAllLogs() {
        return getFilteredLogs(0, 0, 0);
    }

    public List<ScoreLogDTO> getFilteredLogs(int classID, int subjectID, int yearSemesterID) {
        List<ScoreLogDTO> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT TOP 1000 "
                + "sl.LogID, "
                + "CASE "
                + "WHEN LTRIM(RTRIM(CAST(sl.TeacherID AS NVARCHAR(50)))) = '0' THEN N'Quản trị viên' "
                + "WHEN NULLIF(LTRIM(RTRIM(CAST(sl.TeacherID AS NVARCHAR(50)))), '') IS NULL THEN N'Quản trị viên' "
                + "WHEN NULLIF(LTRIM(RTRIM(t.FullName)), '') IS NULL THEN N'Quản trị viên' "
                + "ELSE t.FullName "
                + "END AS TeacherNameStr, "
                + "COALESCE(NULLIF(LTRIM(RTRIM(s.FullName)), ''), N'Chưa rõ') AS StudentNameStr, "
                + "COALESCE(NULLIF(LTRIM(RTRIM(sub.SubjectName)), ''), N'Chưa rõ') AS SubjectNameStr, "
                + "COALESCE(NULLIF(LTRIM(RTRIM(ys.SemesterName)), ''), N'Chưa rõ') + ' (' + COALESCE(NULLIF(LTRIM(RTRIM(ys.SchoolYear)), ''), '') + ')' AS SemesterName, "
                + "sl.ActionType, sl.ChangeContent, sl.ChangeDate "
                + "FROM ScoreLog sl "
                + "LEFT JOIN tblTeacher t ON sl.TeacherID = t.ID "
                + "LEFT JOIN tblStudent s ON sl.StudentID = s.StudentID "
                + "LEFT JOIN tblSubject sub ON sl.SubjectID = sub.SubjectID "
                + "LEFT JOIN tblYearSemester ys ON sl.SemesterID = ys.YearSemesterID "
                + "WHERE 1=1 ");

        if (subjectID > 0) {
            sql.append(" AND sl.SubjectID = ?");
        }
        if (yearSemesterID > 0) {
            sql.append(" AND sl.SemesterID = ?");
        }
        // Temporarily disabled classID filter to narrow debugging surface
        // if (classID > 0) {
        // sql.append(" AND sl.StudentID IN (SELECT StudentID FROM tblStudentClass WHERE
        // ClassID = ?)");
        // }

        sql.append(" ORDER BY sl.ChangeDate DESC");

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            System.out.println("[ScoreLogDAO] getFilteredLogs SQL=" + sql);
            int idx = 1;
            if (subjectID > 0) {
                ps.setInt(idx++, subjectID);
            }
            if (yearSemesterID > 0) {
                ps.setInt(idx++, yearSemesterID);
            }
            // classID binding temporarily disabled while debugging
            // if (classID > 0) {
            // ps.setInt(idx++, classID);
            // }

            System.out.println("[ScoreLogDAO] getFilteredLogs params count=" + (idx - 1));
            System.out.println("=== EXECUTING SQL: " + sql.toString());
            System.out.println(
                    "=== PARAMS: SubjectID=" + subjectID + ", SemesterID=" + yearSemesterID + ", ClassID=" + classID);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    ScoreLogDTO dto = new ScoreLogDTO();
                    dto.setLogID(rs.getInt("LogID"));
                    dto.setTeacherName(defaultString(rs.getString("TeacherNameStr"), "Quản trị viên"));
                    dto.setStudentName(defaultString(rs.getString("StudentNameStr"), "Chưa rõ"));
                    dto.setSubjectName(defaultString(rs.getString("SubjectNameStr"), "Chưa rõ"));
                    dto.setSemesterName(rs.getString("SemesterName"));
                    dto.setActionType(rs.getString("ActionType"));
                    dto.setChangeContent(rs.getString("ChangeContent"));
                    dto.setChangeDate(rs.getTimestamp("ChangeDate"));
                    list.add(dto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private boolean hasColumn(Connection conn, String tableName, String columnName) throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        try (ResultSet rs = metaData.getColumns(null, null, tableName, columnName)) {
            return rs.next();
        }
    }

    private boolean hasColumn(String tableName, String columnName) {
        try (Connection conn = DBUtil.getConnection()) {
            return hasColumn(conn, tableName, columnName);
        } catch (SQLException e) {
            return false;
        }
    }

    private String defaultString(String value, String defaultValue) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        return value;
    }
}