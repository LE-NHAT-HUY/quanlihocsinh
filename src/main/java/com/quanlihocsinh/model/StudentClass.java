package com.quanlihocsinh.model;

public class StudentClass {
    private int studentClassID; // Khóa chính
    private String studentID; // ID học sinh
    private int classID; // ID lớp
    private int cohortID; // Khóa học
    private boolean isActive; // Trạng thái
    private int yearSemesterID; // ID năm học/học kỳ

    // Constructor không tham số
    public StudentClass() {
    }

    // Constructor đầy đủ
    public StudentClass(int studentClassID, String studentID, int classID, int cohortID, boolean isActive,
            int yearSemesterID) {
        this.studentClassID = studentClassID;
        this.studentID = studentID;
        this.classID = classID;
        this.cohortID = cohortID;
        this.isActive = isActive;
        this.yearSemesterID = yearSemesterID;
    }

    // Getter và Setter
    public int getStudentClassID() {
        return studentClassID;
    }

    public void setStudentClassID(int studentClassID) {
        this.studentClassID = studentClassID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public int getCohortID() {
        return cohortID;
    }

    public void setCohortID(int cohortID) {
        this.cohortID = cohortID;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getYearSemesterID() {
        return yearSemesterID;
    }

    public void setYearSemesterID(int yearSemesterID) {
        this.yearSemesterID = yearSemesterID;
    }
}
