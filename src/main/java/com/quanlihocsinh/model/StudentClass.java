package com.quanlihocsinh.model;

public class StudentClass {
    private int studentClassID;
    private int studentID;
    private int classID;
    private Integer cohortID;
    private boolean isActive;
    private int yearSemesterID;

    // Getter và Setter
    public int getStudentClassID() {
        return studentClassID;
    }

    public void setStudentClassID(int studentClassID) {
        this.studentClassID = studentClassID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public Integer getCohortID() {
        return cohortID;
    }

    public void setCohortID(Integer cohortID) {
        this.cohortID = cohortID;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getYearSemesterID() {
        return yearSemesterID;
    }

    public void setYearSemesterID(int yearSemesterID) {
        this.yearSemesterID = yearSemesterID;
    }
}
