package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.ChartData;
import com.quanlihocsinh.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StatisticsDAO {

    private int countActiveTable(String tableName) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE IsActive = 1";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public int countStudents() {
        return countActiveTable("tblStudent");
    }

    public int countTeachers() {
        return countActiveTable("tblTeacher");
    }

    public int countClasses() {
        return countActiveTable("tblClass");
    }

    public int countSubjects() {
        return countActiveTable("tblSubject");
    }

    public List<ChartData> getClassSizeData() {
        List<ChartData> data = new ArrayList<>();
        String sql = "SELECT c.ClassName, COUNT(sc.StudentClassID) AS StudentCount " +
                "FROM tblClass c " +
                "LEFT JOIN tblStudentClass sc ON sc.ClassID = c.ClassID AND sc.isActive = 1 " +
                "WHERE c.IsActive = 1 " +
                "GROUP BY c.ClassID, c.ClassName " +
                "ORDER BY c.ClassName";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                data.add(new ChartData(rs.getString("ClassName"), rs.getInt("StudentCount")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    public List<ChartData> getAcademicRatingData() {
        List<ChartData> data = new ArrayList<>();
        String[] labels = { "Giỏi", "Khá", "Trung bình", "Yếu", "Kém" };
        Map<String, Double> counts = new LinkedHashMap<>();

        for (String label : labels) {
            counts.put(label, 0.0);
        }

        String sql = "SELECT AcademicRating, COUNT(*) AS Total " +
                "FROM tblScore " +
                "WHERE IsActive = 1 AND AcademicRating IS NOT NULL " +
                "GROUP BY AcademicRating";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String rating = rs.getString("AcademicRating");
                if (counts.containsKey(rating)) {
                    counts.put(rating, (double) rs.getInt("Total"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String label : labels) {
            data.add(new ChartData(label, counts.get(label)));
        }

        return data;
    }

    public List<ChartData> getScoreDistributionData() {
        List<ChartData> data = new ArrayList<>();
        String[] labels = { "< 5", "5 - 7", "7 - 8", "8 - 9", "9 - 10" };
        Map<String, Double> counts = new LinkedHashMap<>();

        for (String label : labels) {
            counts.put(label, 0.0);
        }

        String scoreRangeCase = "CASE " +
                "WHEN AverageScore < 5 THEN '< 5' " +
                "WHEN AverageScore >= 5 AND AverageScore < 7 THEN '5 - 7' " +
                "WHEN AverageScore >= 7 AND AverageScore < 8 THEN '7 - 8' " +
                "WHEN AverageScore >= 8 AND AverageScore < 9 THEN '8 - 9' " +
                "ELSE '9 - 10' END";

        String sql = "SELECT " + scoreRangeCase + " AS ScoreRange, COUNT(*) AS Total " +
                "FROM tblScore " +
                "WHERE IsActive = 1 AND AverageScore IS NOT NULL " +
                "GROUP BY " + scoreRangeCase;

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String scoreRange = rs.getString("ScoreRange");
                if (counts.containsKey(scoreRange)) {
                    counts.put(scoreRange, (double) rs.getInt("Total"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String label : labels) {
            data.add(new ChartData(label, counts.get(label)));
        }

        return data;
    }
}