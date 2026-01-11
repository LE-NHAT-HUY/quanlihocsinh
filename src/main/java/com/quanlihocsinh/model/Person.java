package com.quanlihocsinh.model;

import java.io.Serializable;
import java.sql.Date;

public class Person implements Serializable {
    private int personId;
    private String fullName; // Chữ N viết hoa
    private Date birth;
    private String gender;
    private String address;
    private String phone;
    private String images; // <-- THÊM TRƯỜNG NÀY ĐỂ SỬA LỖI
    private boolean isActive;

    public Person() {
    }

    // --- Getter & Setter ---

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // --- BỔ SUNG GETTER/SETTER CHO IMAGES ---
    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}