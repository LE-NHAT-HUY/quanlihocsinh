package com.quanlihocsinh.model;

public class StudentClass {
    private int studentClassID;
    private String studentID;
    private int classID;
    private int cohortID;
    private boolean isActive;
    private int yearSemesterID;

    private Student student;

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

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getYearSemesterID() {
        return yearSemesterID;
    }

    public void setYearSemesterID(int yearSemesterID) {
        this.yearSemesterID = yearSemesterID;
    }

    // THÊM GETTER/SETTER CHO STUDENT
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "StudentClass{" +
                "studentClassID=" + studentClassID +
                ", studentID='" + studentID + '\'' +
                ", classID=" + classID +
                ", cohortID=" + cohortID +
                ", isActive=" + isActive +
                ", yearSemesterID=" + yearSemesterID +
                ", student=" + (student != null ? student.getFullName() : "null") +
                '}';
    }
}