package com.quanlihocsinh.model;

public class Cohort {
    private int CohortID;
    private int CohortName;
    private int StartYear;
    private int EndYear;
    private boolean IsActive; // giữ nguyên tên biến để map DB

    // getter / setter
    public int getCohortID() {
        return CohortID;
    }

    public void setCohortID(int cohortID) {
        this.CohortID = cohortID;
    }

    public int getCohortName() {
        return CohortName;
    }

    public void setCohortName(int cohortName) {
        this.CohortName = cohortName;
    }

    public int getStartYear() {
        return StartYear;
    }

    public void setStartYear(int startYear) {
        this.StartYear = startYear;
    }

    public int getEndYear() {
        return EndYear;
    }

    public void setEndYear(int endYear) {
        this.EndYear = endYear;
    }

    // boolean getter đúng chuẩn JavaBean
    public boolean getIsActive() {
        return IsActive;
    }

    public void setIsActive(boolean isActive) {
        this.IsActive = isActive;
    }
}
