package com.quanlihocsinh.service;

import com.quanlihocsinh.dao.NotificationRepository;
import com.quanlihocsinh.model.NotificationAttachment;
import com.quanlihocsinh.model.Notification;
import com.quanlihocsinh.model.tblClass;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationService {

    public static final String TARGET_ALL = "ALL";
    public static final String TARGET_ALL_TEACHER = "ALL_TEACHER";
    public static final String TARGET_ALL_STUDENT = "ALL_STUDENT";
    public static final String TARGET_CLASS = "CLASS";

    private final NotificationRepository notificationRepository;

    public NotificationService() {
        this.notificationRepository = new NotificationRepository();
    }

    public int createNotification(Notification notification) throws Exception {
        validateNotification(notification);
        return notificationRepository.add(notification);
    }

    public List<Notification> getAdminNotifications() {
        return notificationRepository.findAllForAdmin();
    }

    public Notification getNotificationById(int notificationId) {
        if (notificationId <= 0) {
            return null;
        }
        return notificationRepository.findById(notificationId);
    }

    public List<NotificationAttachment> getNotificationAttachments(int notificationId) {
        if (notificationId <= 0) {
            return new ArrayList<>();
        }
        return notificationRepository.getAttachmentsByNotificationId(notificationId);
    }

    public NotificationAttachment getNotificationAttachmentById(int attachmentId) {
        if (attachmentId <= 0) {
            return null;
        }
        return notificationRepository.findAttachmentById(attachmentId);
    }

    public int addNotificationAttachment(NotificationAttachment attachment) throws Exception {
        if (attachment == null) {
            throw new IllegalArgumentException("Attachment is required");
        }
        if (attachment.getNotificationID() <= 0) {
            throw new IllegalArgumentException("Notification ID is required");
        }
        if (attachment.getFileName() == null || attachment.getFileName().trim().isEmpty()) {
            throw new IllegalArgumentException("Attachment file name is required");
        }
        if (attachment.getFilePath() == null || attachment.getFilePath().trim().isEmpty()) {
            throw new IllegalArgumentException("Attachment file path is required");
        }
        return notificationRepository.addAttachment(attachment);
    }

    public void addNotificationAttachments(int notificationId, List<NotificationAttachment> attachments)
            throws Exception {
        if (notificationId <= 0 || attachments == null || attachments.isEmpty()) {
            return;
        }
        notificationRepository.addAttachments(notificationId, attachments);
    }

    public List<Notification> getTeacherNotifications(String senderUsername) {
        if (senderUsername == null || senderUsername.trim().isEmpty()) {
            throw new IllegalArgumentException("Sender username is required");
        }
        return notificationRepository.findBySenderUsername(senderUsername.trim());
    }

    public List<Notification> getStudentNotifications(String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID is required");
        }
        return notificationRepository.findForStudent(studentId.trim());
    }

    public List<Notification> getTeacherInboxNotifications(String teacherUsername) {
        if (teacherUsername == null || teacherUsername.trim().isEmpty()) {
            throw new IllegalArgumentException("Teacher username is required");
        }
        return notificationRepository.findForTeacherInbox(teacherUsername.trim());
    }

    public List<tblClass> getActiveClassesForAdmin() {
        return notificationRepository.findActiveClasses();
    }

    public List<tblClass> getAllowedClassesForTeacher(String teacherUsername) {
        if (teacherUsername == null || teacherUsername.trim().isEmpty()) {
            throw new IllegalArgumentException("Teacher username is required");
        }
        return notificationRepository.findTeacherAllowedClasses(teacherUsername.trim());
    }

    public boolean updateStatus(int notificationID, boolean isActive) {
        if (notificationID <= 0) {
            throw new IllegalArgumentException("Notification ID is required");
        }
        return notificationRepository.updateStatus(notificationID, isActive);
    }

    public boolean deleteNotificationHard(int notificationID) {
        if (notificationID <= 0) {
            throw new IllegalArgumentException("Notification ID is required");
        }
        return notificationRepository.deleteHard(notificationID);
    }

    public int countUnreadForAdmin(int userId) {
        if (userId <= 0) {
            return 0;
        }
        return notificationRepository.countUnreadForAdmin(userId);
    }

    public int countUnreadForTeacher(int userId, String teacherUsername) {
        if (userId <= 0 || teacherUsername == null || teacherUsername.trim().isEmpty()) {
            return 0;
        }
        return notificationRepository.countUnreadForTeacher(userId, teacherUsername.trim());
    }

    public int countUnreadForStudent(int userId, String studentId) {
        if (userId <= 0 || studentId == null || studentId.trim().isEmpty()) {
            return 0;
        }
        return notificationRepository.countUnreadForStudent(userId, studentId.trim());
    }

    public boolean markAsRead(int userId, int notificationId) {
        if (userId <= 0 || notificationId <= 0) {
            return false;
        }
        return notificationRepository.markAsRead(userId, notificationId);
    }

    public void markAllAsRead(int userId, List<Notification> notifications) throws SQLException {
        if (userId <= 0 || notifications == null || notifications.isEmpty()) {
            return;
        }
        notificationRepository.markAllAsRead(userId, notifications);
    }

    public List<String> getAllowedTargetTypesForAdmin() {
        List<String> types = new ArrayList<>();
        types.add(TARGET_ALL);
        types.add(TARGET_ALL_TEACHER);
        types.add(TARGET_ALL_STUDENT);
        types.add(TARGET_CLASS);
        return types;
    }

    public boolean canTeacherSendToClass(String teacherUsername, int classId) {
        if (teacherUsername == null || teacherUsername.trim().isEmpty() || classId <= 0) {
            return false;
        }
        return notificationRepository.isTeacherAllowedForClass(teacherUsername.trim(), classId);
    }

    private void validateNotification(Notification notification) throws SQLException {
        if (notification == null) {
            throw new IllegalArgumentException("Notification is required");
        }
        if (notification.getTitle() == null || notification.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Notification title is required");
        }
        if (notification.getContent() == null || notification.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Notification content is required");
        }
        if (notification.getSenderUsername() == null || notification.getSenderUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Sender username is required");
        }
        if (notification.getTargetType() == null || notification.getTargetType().trim().isEmpty()) {
            throw new IllegalArgumentException("Target type is required");
        }

        String targetType = notification.getTargetType().trim().toUpperCase();
        notification.setTargetType(targetType);

        if (!TARGET_ALL.equals(targetType)
                && !TARGET_ALL_TEACHER.equals(targetType)
                && !TARGET_ALL_STUDENT.equals(targetType)
                && !TARGET_CLASS.equals(targetType)) {
            throw new IllegalArgumentException("Target type must be ALL, ALL_TEACHER, ALL_STUDENT or CLASS");
        }

        if (TARGET_CLASS.equals(targetType)) {
            if (notification.getTargetClassID() == null || notification.getTargetClassID() <= 0) {
                throw new IllegalArgumentException("Target class is required when target type is CLASS");
            }
        } else {
            notification.setTargetClassID(null);
        }

        if (!notification.isIsActive()) {
            notification.setIsActive(true);
        }
    }
}