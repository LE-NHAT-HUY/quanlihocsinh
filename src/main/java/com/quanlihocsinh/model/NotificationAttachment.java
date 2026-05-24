package com.quanlihocsinh.model;

public class NotificationAttachment {
    private int attachmentID;
    private int notificationID;
    private String fileName;
    private String filePath;
    private Integer fileSizeKB;

    public int getAttachmentID() {
        return attachmentID;
    }

    public void setAttachmentID(int attachmentID) {
        this.attachmentID = attachmentID;
    }

    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getFileSizeKB() {
        return fileSizeKB;
    }

    public void setFileSizeKB(Integer fileSizeKB) {
        this.fileSizeKB = fileSizeKB;
    }
}