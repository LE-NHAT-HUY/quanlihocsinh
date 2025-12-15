package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.TeacherSubject;
import com.quanlihocsinh.model.Subject;
import com.quanlihocsinh.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherSubjectDAO {

    // Kiểm tra giáo viên có được gán môn này không
    public boolean isAssigned(int teacherID, int subjectID) {
        String sql = "SELECT 1 FROM Teacher_Subject WHERE TeacherID=? AND SubjectID=?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, teacherID);
            ps.setInt(2, subjectID);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Gán giáo viên - môn học
    public boolean assign(int teacherID, int subjectID, String assignedBy) {
        if (isAssigned(teacherID, subjectID))
            return false;

        String sql = "INSERT INTO Teacher_Subject(TeacherID, SubjectID, AssignedBy) VALUES (?,?,?)";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, teacherID);
            ps.setInt(2, subjectID);
            ps.setString(3, assignedBy);
            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Hủy gán
    public boolean unassign(int teacherID, int subjectID) {
        String sql = "DELETE FROM Teacher_Subject WHERE TeacherID=? AND SubjectID=?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, teacherID);
            ps.setInt(2, subjectID);
            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Danh sách môn học của giáo viên
    public List<Subject> findSubjectsByTeacher(int teacherID) throws SQLException {
        List<Subject> list = new ArrayList<>();

        String sql = "SELECT s.SubjectID, s.SubjectName, s.NumberOfLesson, s.Semester, s.isActive " +
                "FROM Teacher_Subject ts " +
                "JOIN tblSubject s ON ts.SubjectID = s.SubjectID " +
                "WHERE ts.TeacherID = ?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, teacherID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Subject s = new Subject();
                    s.setSubjectID(rs.getInt("SubjectID"));
                    s.setSubjectName(rs.getString("SubjectName"));
                    s.setNumberOfLesson(rs.getInt("NumberOfLesson"));
                    s.setSemester(rs.getString("Semester"));
                    s.setIsActive(rs.getBoolean("isActive"));
                    list.add(s);
                }
            }
        }
        return list;
    }

    // Tất cả mapping (admin)
    public List<TeacherSubject> findAll() {
        List<TeacherSubject> list = new ArrayList<>();

        String sql = "SELECT ts.TeacherID, ts.SubjectID, ts.AssignedBy, ts.AssignedAt, " +
                "       t.FullName AS TeacherName, " +
                "       s.SubjectName AS SubjectName " +
                "FROM Teacher_Subject ts " +
                "JOIN tblTeacher t ON ts.TeacherID = t.ID " +
                "JOIN tblSubject s ON ts.SubjectID = s.SubjectID";

        try (Connection conn = DBUtil.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                TeacherSubject ts = new TeacherSubject();
                ts.setTeacherID(rs.getInt("TeacherID"));
                ts.setSubjectID(rs.getInt("SubjectID"));
                ts.setAssignedBy(rs.getString("AssignedBy"));
                ts.setAssignedAt(rs.getTimestamp("AssignedAt"));
                ts.setTeacherName(rs.getString("TeacherName"));
                ts.setSubjectName(rs.getString("SubjectName"));
                list.add(ts);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
