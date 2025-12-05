package com.quanlihocsinh.model;

import java.util.Date;

public class Person {
    private int personId;
    private String originalTable;
    private int originalId;
    private String fullname;
    private Date birth;
    private String gender;
    private String address;
    private String phone;
    private String images;
    private String personType; // STUDENT | TEACHER | ADMIN
    private boolean isActive;

    public Person() {
    }

    public Person(int personId, String originalTable, int originalId, String fullname, Date birth,
            String gender, String address, String phone, String images,
            String personType, boolean isActive) {
        this.personId = personId;
        this.originalTable = originalTable;
        this.originalId = originalId;
        this.fullname = fullname;
        this.birth = birth;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.images = images;
        this.personType = personType;
        this.isActive = isActive;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getOriginalTable() {
        return originalTable;
    }

    public void setOriginalTable(String originalTable) {
        this.originalTable = originalTable;
    }

    public int getOriginalId() {
        return originalId;
    }

    public void setOriginalId(int originalId) {
        this.originalId = originalId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", originalTable='" + originalTable + '\'' +
                ", originalId=" + originalId +
                ", fullname='" + fullname + '\'' +
                ", birth=" + birth +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", images='" + images + '\'' +
                ", personType='" + personType + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
