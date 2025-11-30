package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.Schedule;
import com.quanlihocsinh.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {

    public List<Schedule> getAll() {
        List<Schedule> list = new ArrayList<>();
        String sql = "SELECT * FROM tblSchedule ORDER BY ScheduleID DESC";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Schedule s = new Schedule();
                s.setScheduleID(rs.getInt("ScheduleID"));
                s.setSemesterID(rs.getInt("SemesterID"));
                s.setClassID(rs.getInt("ClassID"));
                s.setSubjectID(rs.getInt("SubjectID"));
                s.setTeacherID(rs.getString("TeacherID"));
                s.setNotes(rs.getString("Notes"));
                s.setIsActive(rs.getBoolean("IsActive"));
                s.setStartDate(rs.getDate("StartDate"));
                s.setEndDate(rs.getDate("EndDate"));
                s.setPeriod(rs.getInt("Period"));
                s.setDayOfWeek(rs.getInt("DayOfWeek"));
                s.setHomeroomTeacher(rs.getString("HomeroomTeacher").charAt(0));
                s.setWeekNumber(rs.getInt("WeekNumber"));

                list.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public Schedule getById(int id) {
        String sql = "SELECT * FROM tblSchedule WHERE ScheduleID=?";
        Schedule s = null;
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    s = new Schedule();
                    s.setScheduleID(rs.getInt("ScheduleID"));
                    s.setSemesterID(rs.getInt("SemesterID"));
                    s.setClassID(rs.getInt("ClassID"));
                    s.setSubjectID(rs.getInt("SubjectID"));
                    s.setTeacherID(rs.getString("TeacherID"));
                    s.setNotes(rs.getString("Notes"));
                    s.setIsActive(rs.getBoolean("IsActive"));
                    s.setStartDate(rs.getDate("StartDate"));
                    s.setEndDate(rs.getDate("EndDate"));
                    s.setPeriod(rs.getInt("Period"));
                    s.setDayOfWeek(rs.getInt("DayOfWeek"));
                    s.setHomeroomTeacher(rs.getString("HomeroomTeacher").charAt(0));
                    s.setWeekNumber(rs.getInt("WeekNumber"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return s;
    }

    public void add(Schedule s) {
        String sql = "INSERT INTO tblSchedule (SemesterID, ClassID, SubjectID, TeacherID, Notes, IsActive, StartDate, EndDate, Period, DayOfWeek, HomeroomTeacher, WeekNumber) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, s.getSemesterID());
            ps.setInt(2, s.getClassID());
            ps.setInt(3, s.getSubjectID());
            ps.setString(4, s.getTeacherID());
            ps.setString(5, s.getNotes());
            ps.setBoolean(6, s.isActive());
            ps.setDate(7, s.getStartDate() != null ? new java.sql.Date(s.getStartDate().getTime()) : null);
            ps.setDate(8, s.getEndDate() != null ? new java.sql.Date(s.getEndDate().getTime()) : null);
            ps.setInt(9, s.getPeriod());
            ps.setInt(10, s.getDayOfWeek());
            ps.setString(11, String.valueOf(s.getHomeroomTeacher()));
            ps.setInt(12, s.getWeekNumber());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Schedule s) {
        String sql = "UPDATE tblSchedule SET SemesterID=?, ClassID=?, SubjectID=?, TeacherID=?, Notes=?, IsActive=?, StartDate=?, EndDate=?, Period=?, DayOfWeek=?, HomeroomTeacher=?, WeekNumber=? WHERE ScheduleID=?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, s.getSemesterID());
            ps.setInt(2, s.getClassID());
            ps.setInt(3, s.getSubjectID());
            ps.setString(4, s.getTeacherID());
            ps.setString(5, s.getNotes());
            ps.setBoolean(6, s.isActive());
            ps.setDate(7, s.getStartDate() != null ? new java.sql.Date(s.getStartDate().getTime()) : null);
            ps.setDate(8, s.getEndDate() != null ? new java.sql.Date(s.getEndDate().getTime()) : null);
            ps.setInt(9, s.getPeriod());
            ps.setInt(10, s.getDayOfWeek());
            ps.setString(11, String.valueOf(s.getHomeroomTeacher()));
            ps.setInt(12, s.getWeekNumber());
            ps.setInt(13, s.getScheduleID());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM tblSchedule WHERE ScheduleID=?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void toggleStatus(int id, boolean status) {
        String sql = "UPDATE tblSchedule SET IsActive=? WHERE ScheduleID=?";
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
