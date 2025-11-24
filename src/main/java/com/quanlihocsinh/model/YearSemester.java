package com.quanlihocsinh.model;

public class YearSemester {
    private int yearSemesterID;
    private String semesterName;
    private String schoolYear;
    private boolean isActive;

    public YearSemester() {
    }

    public YearSemester(int yearSemesterID, String semesterName, String schoolYear, boolean isActive) {
        this.yearSemesterID = yearSemesterID;
        this.semesterName = semesterName;
        this.schoolYear = schoolYear;
        this.isActive = isActive;
    }

    public int getYearSemesterID() {
        return yearSemesterID;
    }

    public void setYearSemesterID(int yearSemesterID) {
        this.yearSemesterID = yearSemesterID;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
