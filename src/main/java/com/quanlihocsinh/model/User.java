package com.quanlihocsinh.model;

import java.io.Serializable;

public class User implements Serializable {
    private int userID;
    private String username;
    private String password;
    private int roleId; // 1: Admin, 2: Teacher, 3: Student
    private int personId; // Khóa ngoại sang bảng Person

    // --- CÁC TRƯỜNG BỔ SUNG QUAN TRỌNG ---
    private int entityId; // ID thực tế (TeacherID hoặc StudentID)
    private Person profile; // Chứa thông tin chi tiết (Họ tên, ngày sinh...)

    public User() {
    }

    // Getters Setters chuẩn
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public Person getProfile() {
        return profile;
    }

    public void setProfile(Person profile) {
        this.profile = profile;
    }
}