package com.quanlihocsinh.model;

import java.util.Date;

public class User {
    private int userId;
    private String username;
    private String passwordHash;
    private int roleId;
    private int personId;
    private boolean isActive;
    private Date createdAt;

    // optionally include Person object for convenience
    private Person profile;

    public User() {
    }

    public User(int userId, String username, String passwordHash, int roleId,
            int personId, boolean isActive, Date createdAt, Person profile) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.roleId = roleId;
        this.personId = personId;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.profile = profile;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Person getProfile() {
        return profile;
    }

    public void setProfile(Person profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", roleId=" + roleId +
                ", personId=" + personId +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", profile=" + (profile != null ? profile.getFullname() : "null") +
                '}';
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

}
