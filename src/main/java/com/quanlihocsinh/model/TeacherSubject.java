package com.quanlihocsinh.model;

import java.sql.Timestamp;

public class TeacherSubject {

    private int teacherID;
    private int subjectID;
    private String assignedBy;
    private Timestamp assignedAt;

    // Dùng để hiển thị (JOIN)
    private String teacherName;
    private String subjectName;

    public TeacherSubject() {
    }

    public TeacherSubject(int teacherID, int subjectID, String assignedBy) {
        this.teacherID = teacherID;
        this.subjectID = subjectID;
        this.assignedBy = assignedBy;
    }

    // ===== Getter / Setter =====

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public String getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(String assignedBy) {
        this.assignedBy = assignedBy;
    }

    public Timestamp getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(Timestamp assignedAt) {
        this.assignedAt = assignedAt;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
