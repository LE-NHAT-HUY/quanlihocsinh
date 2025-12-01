package com.quanlihocsinh.model;

public class Grade {
    private int gradeID;
    private String gradeName;
    private String description;
    private boolean isActive;

    public Grade() {
    }

    public Grade(int gradeID, String gradeName, String description, boolean isActive) {
        this.gradeID = gradeID;
        this.gradeName = gradeName;
        this.description = description;
        this.isActive = isActive;
    }

    public int getGradeID() {
        return gradeID;
    }

    public void setGradeID(int gradeID) {
        this.gradeID = gradeID;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
