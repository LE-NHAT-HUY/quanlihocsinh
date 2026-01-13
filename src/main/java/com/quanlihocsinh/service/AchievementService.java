package com.quanlihocsinh.service;

import com.quanlihocsinh.model.Score;
import java.util.List;

public class AchievementService {

    public double calculateAverage(List<Score> scores) {
        if (scores == null || scores.isEmpty())
            return 0.0;
        double sum = 0;
        int count = 0;
        for (Score s : scores) {
            if (s.getAverageScore() != null) {
                sum += s.getAverageScore();
                count++;
            }
        }
        return count > 0 ? Math.round((sum / count) * 10.0) / 10.0 : 0.0;
    }

    public double calculateYearAverage(double avg1, double avg2) {
        if (avg1 == 0 && avg2 == 0)
            return 0.0;
        if (avg2 == 0)
            return avg1;

        double avg = (avg1 + avg2 * 2) / 3;
        return Math.round(avg * 10.0) / 10.0;
    }

    public String classifyAcademic(double avg, List<Score> scores) {
        if (scores == null || scores.isEmpty())
            return "Chưa xếp loại";

        double minScore = 10.0;
        for (Score s : scores) {
            if (s.getAverageScore() != null && s.getAverageScore() < minScore) {
                minScore = s.getAverageScore();
            }
        }

        if (avg >= 8.0) {
            if (minScore >= 6.5)
                return "Giỏi";
            else if (minScore >= 5.0)
                return "Khá";
            else
                return "Trung bình";
        } else if (avg >= 6.5) {
            if (minScore >= 5.0)
                return "Khá";
            else if (minScore >= 3.5)
                return "Trung bình";
            else
                return "Yếu";
        } else if (avg >= 5.0) {
            if (minScore >= 3.5)
                return "Trung bình";
            else
                return "Yếu";
        } else {
            return "Yếu";
        }
    }

    public String getTitle(String academicRank) {
        if ("Giỏi".equals(academicRank))
            return "Học sinh Giỏi";
        if ("Khá".equals(academicRank))
            return "Học sinh Tiên tiến";
        return "Không có";
    }
}