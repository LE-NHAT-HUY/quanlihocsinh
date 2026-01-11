package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.ScoreLogDTO;
import com.quanlihocsinh.model.ScoreLog;
import com.quanlihocsinh.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScoreLogDAO {

    // Hàm Insert (Không thay đổi)
    public void insert(Connection conn, ScoreLog log) throws SQLException {
        String sql = "INSERT INTO ScoreLog (TeacherID, StudentID, SubjectID, SemesterID, ActionType, ChangeContent, ChangeDate) VALUES (?, ?, ?, ?, ?, ?, GETDATE())";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, log.getTeacherID());
            ps.setString(2, log.getStudentID());
            ps.setInt(3, log.getSubjectID());
            ps.setInt(4, log.getSemesterID());
            ps.setString(5, log.getActionType());
            ps.setString(6, log.getChangeContent());
            ps.executeUpdate();
        }
    }

    public List<ScoreLogDTO> getAllLogs() {
        List<ScoreLogDTO> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT l.LogID, ");

        // 1. Tên Giáo viên (Bảng tblTeacher cột FullName)
        sql.append(" COALESCE(t.FullName, 'GV-' + CAST(l.TeacherID AS VARCHAR)) as TeacherNameStr, ");

        // 2. Tên Học sinh (Bảng tblStudent cột FullName)
        sql.append(" COALESCE(s.FullName, l.StudentID) as StudentNameStr, ");

        // 3. Tên Môn học
        sql.append(" sub.SubjectName, ");

        // 4. Tên Học kỳ (Ghép SemesterName và SchoolYear cho đẹp)
        // Ví dụ kết quả: "Học kỳ 1 (2025-2026)"
        sql.append(" (ys.SemesterName + ' (' + ys.SchoolYear + ')') as SemesterName, ");

        sql.append(" l.ActionType, l.ChangeContent, l.ChangeDate ");
        sql.append(" FROM ScoreLog l ");

        // JOIN ĐÚNG TÊN BẢNG
        sql.append(" LEFT JOIN tblTeacher t ON l.TeacherID = t.TeacherID ");
        sql.append(" LEFT JOIN tblStudent s ON l.StudentID = s.StudentID ");
        sql.append(" LEFT JOIN tblSubject sub ON l.SubjectID = sub.SubjectID ");
        sql.append(" LEFT JOIN tblYearSemester ys ON l.SemesterID = ys.YearSemesterID ");

        sql.append(" ORDER BY l.ChangeDate DESC ");

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql.toString());
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ScoreLogDTO dto = new ScoreLogDTO();
                dto.setLogID(rs.getInt("LogID"));
                dto.setTeacherName(rs.getString("TeacherNameStr"));
                dto.setStudentName(rs.getString("StudentNameStr"));
                dto.setSubjectName(rs.getString("SubjectName"));
                dto.setSemesterName(rs.getString("SemesterName"));
                dto.setActionType(rs.getString("ActionType"));
                dto.setChangeContent(rs.getString("ChangeContent"));
                dto.setChangeDate(rs.getTimestamp("ChangeDate"));
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}