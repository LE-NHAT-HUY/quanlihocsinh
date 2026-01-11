package com.quanlihocsinh.model;

import java.io.Serializable;

public class YearAchievement implements Serializable {
    private String schoolYear;
    private double avgHK1;
    private double avgHK2;
    private double avgYear;
    private String rankHK1;
    private String rankHK2;
    private String rankYear;
    private String titleYear;

    // Constructor mặc định
    public YearAchievement() {
    }

    // Constructor đầy đủ
    public YearAchievement(String schoolYear, double avgHK1, double avgHK2, double avgYear,
            String rankHK1, String rankHK2, String rankYear, String titleYear) {
        this.schoolYear = schoolYear;
        this.avgHK1 = avgHK1;
        this.avgHK2 = avgHK2;
        this.avgYear = avgYear;
        this.rankHK1 = rankHK1;
        this.rankHK2 = rankHK2;
        this.rankYear = rankYear;
        this.titleYear = titleYear;
    }

    // --- GETTERS & SETTERS ---

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public double getAvgHK1() {
        return avgHK1;
    }

    public void setAvgHK1(double avgHK1) {
        this.avgHK1 = avgHK1;
    }

    public double getAvgHK2() {
        return avgHK2;
    }

    public void setAvgHK2(double avgHK2) {
        this.avgHK2 = avgHK2;
    }

    public double getAvgYear() {
        return avgYear;
    }

    public void setAvgYear(double avgYear) {
        this.avgYear = avgYear;
    }

    public String getRankHK1() {
        return rankHK1;
    }

    public void setRankHK1(String rankHK1) {
        this.rankHK1 = rankHK1;
    }

    public String getRankHK2() {
        return rankHK2;
    }

    public void setRankHK2(String rankHK2) {
        this.rankHK2 = rankHK2;
    }

    public String getRankYear() {
        return rankYear;
    }

    public void setRankYear(String rankYear) {
        this.rankYear = rankYear;
    }

    public String getTitleYear() {
        return titleYear;
    }

    public void setTitleYear(String titleYear) {
        this.titleYear = titleYear;
    }
}