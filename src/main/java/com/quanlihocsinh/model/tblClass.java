package com.quanlihocsinh.model;

public class tblClass {
    private int classID;
    private String className;
    private int gradeID;
    private Integer cohortID;
    private int maxStudents;
    private int currentStudents;
    private String schoolYear;
    private boolean isActive;

    public tblClass() {
    }

    public tblClass(int classID, String className, int gradeID, Integer cohortID, int maxStudents, int currentStudents,
            String schoolYear, boolean isActive) {
        this.classID = classID;
        this.className = className;
        this.gradeID = gradeID;
        this.cohortID = cohortID;
        this.maxStudents = maxStudents;
        this.currentStudents = currentStudents;
        this.schoolYear = schoolYear;
        this.isActive = isActive;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getGradeID() {
        return gradeID;
    }

    public void setGradeID(int gradeID) {
        this.gradeID = gradeID;
    }

    public Integer getCohortID() {
        return cohortID;
    }

    public void setCohortID(Integer cohortID) {
        this.cohortID = cohortID;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }

    public int getCurrentStudents() {
        return currentStudents;
    }

    public void setCurrentStudents(int currentStudents) {
        this.currentStudents = currentStudents;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
