package com.quanlihocsinh.model;

import java.util.Date;

public class Score {

    private int scoreID;
    private String studentID;
    private int subjectID;
    private int yearSemesterID;

    private Double oralScore1;
    private Double oralScore2;
    private Double score15Minute1;
    private Double score15Minute2;
    private Double averageCA;

    private Double midtermScore;
    private Double finalScore;
    private Double averageScore;

    private String academicRating;
    private Date createDate;
    private String notes;
    private boolean isActive;

    // Constructor không tham số
    public Score() {
    }

    // Constructor đầy đủ tham số
    public Score(int scoreID, String studentID, int subjectID, int yearSemesterID,
            Double oralScore1, Double oralScore2,
            Double score15Minute1, Double score15Minute2, Double averageCA,
            Double midtermScore, Double finalScore, Double averageScore,
            String academicRating, Date createDate, String notes, boolean isActive) {

        this.scoreID = scoreID;
        this.studentID = studentID;
        this.subjectID = subjectID;
        this.yearSemesterID = yearSemesterID;
        this.oralScore1 = oralScore1;
        this.oralScore2 = oralScore2;
        this.score15Minute1 = score15Minute1;
        this.score15Minute2 = score15Minute2;
        this.averageCA = averageCA;
        this.midtermScore = midtermScore;
        this.finalScore = finalScore;
        this.averageScore = averageScore;
        this.academicRating = academicRating;
        this.createDate = createDate;
        this.notes = notes;
        this.isActive = isActive;
    }

    public int getScoreID() {
        return scoreID;
    }

    public void setScoreID(int scoreID) {
        this.scoreID = scoreID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public int getYearSemesterID() {
        return yearSemesterID;
    }

    public void setYearSemesterID(int yearSemesterID) {
        this.yearSemesterID = yearSemesterID;
    }

    public Double getOralScore1() {
        return oralScore1;
    }

    public void setOralScore1(Double oralScore1) {
        this.oralScore1 = oralScore1;
    }

    public Double getOralScore2() {
        return oralScore2;
    }

    public void setOralScore2(Double oralScore2) {
        this.oralScore2 = oralScore2;
    }

    public Double getScore15Minute1() {
        return score15Minute1;
    }

    public void setScore15Minute1(Double score15Minute1) {
        this.score15Minute1 = score15Minute1;
    }

    public Double getScore15Minute2() {
        return score15Minute2;
    }

    public void setScore15Minute2(Double score15Minute2) {
        this.score15Minute2 = score15Minute2;
    }

    public Double getAverageCA() {
        return averageCA;
    }

    public void setAverageCA(Double averageCA) {
        this.averageCA = averageCA;
    }

    public Double getMidtermScore() {
        return midtermScore;
    }

    public void setMidtermScore(Double midtermScore) {
        this.midtermScore = midtermScore;
    }

    public Double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(Double finalScore) {
        this.finalScore = finalScore;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    public String getAcademicRating() {
        return academicRating;
    }

    public void setAcademicRating(String academicRating) {
        this.academicRating = academicRating;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
