package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.TeacherSubject;
import com.quanlihocsinh.model.Subject;
import com.quanlihocsinh.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherSubjectDAO {

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

    public List<Integer> findSubjectIdsByTeacher(int teacherID) {
        List<Integer> subjectIds = new ArrayList<>();
        String sql = "SELECT SubjectID FROM Teacher_Subject WHERE TeacherID = ? ORDER BY SubjectID";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, teacherID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    subjectIds.add(rs.getInt("SubjectID"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return subjectIds;
    }

    public void replaceSubjectsForTeacher(int teacherID, String[] subjectIDs) {
        String deleteSql = "DELETE FROM Teacher_Subject WHERE TeacherID = ?";
        String insertSql = "INSERT INTO Teacher_Subject(TeacherID, SubjectID, AssignedBy) VALUES (?,?,?)";

        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement deletePs = conn.prepareStatement(deleteSql)) {
                deletePs.setInt(1, teacherID);
                deletePs.executeUpdate();
            }

            if (subjectIDs != null && subjectIDs.length > 0) {
                try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
                    for (String subjectIdStr : subjectIDs) {
                        if (subjectIdStr == null || subjectIdStr.trim().isEmpty()) {
                            continue;
                        }

                        insertPs.setInt(1, teacherID);
                        insertPs.setInt(2, Integer.parseInt(subjectIdStr.trim()));
                        insertPs.setString(3, "admin");
                        insertPs.addBatch();
                    }
                    insertPs.executeBatch();
                }
            }

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
