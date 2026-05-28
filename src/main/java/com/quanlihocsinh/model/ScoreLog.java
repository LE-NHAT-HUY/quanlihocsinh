package com.quanlihocsinh.model;

import java.util.Date;

public class ScoreLog {
    private int logID;
    private int teacherID;
    private int classID;
    private String studentID;
    private int subjectID;
    private int semesterID;
    private String actionType;
    private String changeContent;
    private Date changeDate;

    public ScoreLog() {
    }

    public ScoreLog(int teacherID, int classID, String studentID, int subjectID, int semesterID, String actionType,
            String changeContent) {
        this.teacherID = teacherID;
        this.classID = classID;
        this.studentID = studentID;
        this.subjectID = subjectID;
        this.semesterID = semesterID;
        this.actionType = actionType;
        this.changeContent = changeContent;
    }

    // Getter và Setter...
    public int getLogID() {
        return logID;
    }

    public void setLogID(int logID) {
        this.logID = logID;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
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

    public int getSemesterID() {
        return semesterID;
    }

    public void setSemesterID(int semesterID) {
        this.semesterID = semesterID;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getChangeContent() {
        return changeContent;
    }

    public void setChangeContent(String changeContent) {
        this.changeContent = changeContent;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }
}