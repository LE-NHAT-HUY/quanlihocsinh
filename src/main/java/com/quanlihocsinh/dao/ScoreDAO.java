package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.Score;
import com.quanlihocsinh.model.StudentClass;
import com.quanlihocsinh.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScoreDAO {

    public List<Score> getByClassSubjectYear(int classID, int subjectID, int yearSemesterID) throws SQLException {
        List<Score> list = new ArrayList<>();

        String sql = "SELECT s.* FROM tblScore s " +
                "WHERE s.SubjectID = ? AND s.YearSemesterID = ? " +
                "AND s.StudentID IN ( " +
                "    SELECT sc.StudentID FROM tblStudentClass sc " +
                "    WHERE sc.ClassID = ? AND sc.isActive = 1 " +
                ") " +
                "ORDER BY s.StudentID";

        try (Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, subjectID);
            ps.setInt(2, yearSemesterID);
            ps.setInt(3, classID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    public Score findByStudentSubjectYear(String studentID, int subjectID, int yearSemesterID) throws SQLException {
        String sql = "SELECT * FROM tblScore WHERE StudentID = ? AND SubjectID = ? AND YearSemesterID = ?";
        try (Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, studentID);
            ps.setInt(2, subjectID);
            ps.setInt(3, yearSemesterID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return mapRow(rs);
            }
        }
        return null;
    }

    public void insert(Score s) throws SQLException {
        String sql = "INSERT INTO tblScore " +
                "(StudentID, SubjectID, YearSemesterID, " +
                "OralScore1, OralScore2, Score15Minute1, Score15Minute2, Average_CA_Score, " +
                "MidtermScore, FinalScore, AverageScore, AcademicRating, CreateDate, Notes, IsActive) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,? ,GETUTCDATE(),?,?)";
        try (Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int idx = setCommonParams(ps, s);
            ps.setString(idx++, s.getNotes());
            ps.setBoolean(idx++, s.isActive());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next())
                    s.setScoreID(rs.getInt(1));
            }
        }
    }

    public void update(Score s) throws SQLException {
        String sql = "UPDATE tblScore SET " +
                "OralScore1=?, OralScore2=?, Score15Minute1=?, Score15Minute2=?, Average_CA_Score=?, " +
                "MidtermScore=?, FinalScore=?, AverageScore=?, AcademicRating=?, Notes=?, IsActive=? " +
                "WHERE ScoreID=?";
        try (Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            int i = 1;
            setDouble(ps, i++, s.getOralScore1());
            setDouble(ps, i++, s.getOralScore2());
            setDouble(ps, i++, s.getScore15Minute1());
            setDouble(ps, i++, s.getScore15Minute2());
            setDouble(ps, i++, s.getAverageCA());
            setDouble(ps, i++, s.getMidtermScore());
            setDouble(ps, i++, s.getFinalScore());
            setDouble(ps, i++, s.getAverageScore());
            ps.setString(i++, s.getAcademicRating());
            ps.setString(i++, s.getNotes());
            ps.setBoolean(i++, s.isActive());
            ps.setInt(i, s.getScoreID());
            ps.executeUpdate();
        }
    }

    public void delete(int scoreID) throws SQLException {
        String sql = "DELETE FROM tblScore WHERE ScoreID = ?";
        try (Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, scoreID);
            ps.executeUpdate();
        }
    }

    private int setCommonParams(PreparedStatement ps, Score s) throws SQLException {
        int i = 1;
        ps.setString(i++, s.getStudentID());
        ps.setInt(i++, s.getSubjectID());
        ps.setInt(i++, s.getYearSemesterID());

        setDouble(ps, i++, s.getOralScore1());
        setDouble(ps, i++, s.getOralScore2());
        setDouble(ps, i++, s.getScore15Minute1());
        setDouble(ps, i++, s.getScore15Minute2());
        setDouble(ps, i++, s.getAverageCA());
        setDouble(ps, i++, s.getMidtermScore());
        setDouble(ps, i++, s.getFinalScore());
        setDouble(ps, i++, s.getAverageScore());

        ps.setString(i++, s.getAcademicRating());
        return i;
    }

    private void setDouble(PreparedStatement ps, int idx, Double v) throws SQLException {
        if (v == null)
            ps.setNull(idx, Types.DOUBLE);
        else
            ps.setDouble(idx, v);
    }

    private Score mapRow(ResultSet rs) throws SQLException {
        Score s = new Score();
        s.setScoreID(rs.getInt("ScoreID"));
        s.setStudentID(rs.getString("StudentID"));
        s.setSubjectID(rs.getInt("SubjectID"));
        s.setYearSemesterID(rs.getInt("YearSemesterID"));

        s.setOralScore1(getNullableDouble(rs, "OralScore1"));
        s.setOralScore2(getNullableDouble(rs, "OralScore2"));
        s.setScore15Minute1(getNullableDouble(rs, "Score15Minute1"));
        s.setScore15Minute2(getNullableDouble(rs, "Score15Minute2"));

        s.setAverageCA(getNullableDouble(rs, "Average_CA_Score"));
        s.setMidtermScore(getNullableDouble(rs, "MidtermScore"));
        s.setFinalScore(getNullableDouble(rs, "FinalScore"));
        s.setAverageScore(getNullableDouble(rs, "AverageScore"));

        s.setAcademicRating(rs.getString("AcademicRating"));

        Timestamp ts = rs.getTimestamp("CreateDate");
        if (ts != null)
            s.setCreateDate(new Date(ts.getTime()));

        s.setNotes(rs.getString("Notes"));
        s.setActive(rs.getBoolean("IsActive"));
        return s;
    }

    private Double getNullableDouble(ResultSet rs, String col) throws SQLException {
        double v = rs.getDouble(col);
        return rs.wasNull() ? null : v;
    }

    // Thêm vào com.quanlihocsinh.dao.ScoreDAO

    public List<Score> getStudentTranscript(String studentID, int yearSemesterID) {
        List<Score> list = new ArrayList<>();
        // Join với bảng Subject để lấy tên môn
        String sql = "SELECT sc.*, s.SubjectName " +
                "FROM tblScore sc " +
                "JOIN tblSubject s ON sc.SubjectID = s.SubjectID " +
                "WHERE sc.StudentID = ? AND sc.YearSemesterID = ?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, studentID);
            ps.setInt(2, yearSemesterID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Score s = new Score();
                s.setScoreID(rs.getInt("ScoreID"));
                s.setStudentID(rs.getString("StudentID"));
                s.setSubjectID(rs.getInt("SubjectID")); // Lưu ý kiểu int/string tùy DB
                s.setYearSemesterID(rs.getInt("YearSemesterID"));

                s.setOralScore1(rs.getObject("OralScore1") != null ? rs.getDouble("OralScore1") : null);
                s.setOralScore2(rs.getObject("OralScore2") != null ? rs.getDouble("OralScore2") : null);
                s.setScore15Minute1(rs.getObject("Score15Minute1") != null ? rs.getDouble("Score15Minute1") : null);
                s.setScore15Minute2(rs.getObject("Score15Minute2") != null ? rs.getDouble("Score15Minute2") : null);
                s.setMidtermScore(rs.getObject("MidtermScore") != null ? rs.getDouble("MidtermScore") : null);
                s.setFinalScore(rs.getObject("FinalScore") != null ? rs.getDouble("FinalScore") : null);
                s.setAverageScore(rs.getObject("AverageScore") != null ? rs.getDouble("AverageScore") : null);
                s.setAcademicRating(rs.getString("AcademicRating"));
                s.setNotes(rs.getString("Notes"));

                // Gán tên môn học vào đối tượng Score (Hoặc tạo thuộc tính phụ trong Model
                // Score)
                // Tạm thời gán vào notes hoặc tạo field mới trong Model Score nếu cần
                // Ở đây tôi giả sử bạn thêm field subjectName vào Model Score hoặc dùng DTO
                s.setSubjectName(rs.getString("SubjectName"));

                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
