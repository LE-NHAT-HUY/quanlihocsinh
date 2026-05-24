package com.quanlihocsinh.model;

import java.util.Date;
import java.util.List;

public class Notification {
    private int notificationID;
    private String title;
    private String content;
    private String senderUsername;
    private String senderFullName;
    private String senderDepartment;
    private String targetType;
    private Integer targetClassID;
    private String targetClassName;
    private Date createdDate;
    private boolean isActive;
    private List<NotificationAttachment> attachments;

    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getSenderFullName() {
        return senderFullName;
    }

    public void setSenderFullName(String senderFullName) {
        this.senderFullName = senderFullName;
    }

    public String getSenderDepartment() {
        return senderDepartment;
    }

    public void setSenderDepartment(String senderDepartment) {
        this.senderDepartment = senderDepartment;
    }

    public String getTargetType() {
        return targetType;
    }

    public NotificationTargetType getTargetTypeEnum() {
        if (targetType == null || targetType.trim().isEmpty()) {
            return null;
        }
        return NotificationTargetType.valueOf(targetType.trim().toUpperCase());
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public void setTargetType(NotificationTargetType targetType) {
        this.targetType = targetType == null ? null : targetType.name();
    }

    public Integer getTargetClassID() {
        return targetClassID;
    }

    public void setTargetClassID(Integer targetClassID) {
        this.targetClassID = targetClassID;
    }

    public String getTargetClassName() {
        return targetClassName;
    }

    public void setTargetClassName(String targetClassName) {
        this.targetClassName = targetClassName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public List<NotificationAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<NotificationAttachment> attachments) {
        this.attachments = attachments;
    }
}