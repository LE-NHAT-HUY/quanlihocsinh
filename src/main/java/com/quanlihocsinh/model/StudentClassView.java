package com.quanlihocsinh.model;

public class StudentClassView {
    private int id; // StudentClassID
    private String studentID;
    private String studentName;
    private int classID;
    private String className;
    private Integer cohortID;
    private boolean active;

    public StudentClassView() {
    }

    // Getter & Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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

    public Integer getCohortID() {
        return cohortID;
    }

    public void setCohortID(Integer cohortID) {
        this.cohortID = cohortID;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "StudentClassView{" +
                "id=" + id +
                ", studentID='" + studentID + '\'' +
                ", studentName='" + studentName + '\'' +
                ", classID=" + classID +
                ", className='" + className + '\'' +
                ", cohortID=" + cohortID +
                ", active=" + active +
                '}';
    }
}
