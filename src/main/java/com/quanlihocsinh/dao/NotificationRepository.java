package com.quanlihocsinh.dao;

import com.quanlihocsinh.model.Notification;
import com.quanlihocsinh.model.NotificationAttachment;
import com.quanlihocsinh.model.tblClass;
import com.quanlihocsinh.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class NotificationRepository {

    private boolean isNotificationReadTableMissing(Exception e) {
        if (e == null || e.getMessage() == null) {
            return false;
        }
        String message = e.getMessage().toLowerCase();
        return message.contains("tblnotificationread")
                && (message.contains("invalid object name") || message.contains("khong hop le")
                        || message.contains("kh\u00f4ng h\u1ee3p l\u1ec7"));
    }

    private Notification mapNotification(ResultSet rs) throws Exception {
        Notification notification = new Notification();
        notification.setNotificationID(rs.getInt("NotificationID"));
        notification.setTitle(rs.getString("Title"));
        notification.setContent(rs.getString("Content"));
        notification.setSenderUsername(rs.getString("SenderUsername"));
        notification.setSenderFullName(rs.getString("SenderFullName"));
        notification.setSenderDepartment(rs.getString("SenderDepartment"));
        notification.setTargetType(rs.getString("TargetType"));

        int targetClassId = rs.getInt("TargetClassID");
        if (rs.wasNull()) {
            notification.setTargetClassID(null);
        } else {
            notification.setTargetClassID(targetClassId);
        }

        notification.setTargetClassName(rs.getString("TargetClassName"));
        Timestamp createdDate = rs.getTimestamp("CreatedDate");
        if (createdDate != null) {
            notification.setCreatedDate(new java.util.Date(createdDate.getTime()));
        }
        notification.setIsActive(rs.getBoolean("IsActive"));
        return notification;
    }

    private NotificationAttachment mapAttachment(ResultSet rs) throws Exception {
        NotificationAttachment attachment = new NotificationAttachment();
        attachment.setAttachmentID(rs.getInt("AttachmentID"));
        attachment.setNotificationID(rs.getInt("NotificationID"));
        attachment.setFileName(rs.getString("FileName"));
        attachment.setFilePath(rs.getString("FilePath"));

        int fileSize = rs.getInt("FileSize");
        if (rs.wasNull()) {
            attachment.setFileSizeKB(null);
        } else {
            attachment.setFileSizeKB(fileSize);
        }

        return attachment;
    }

    public int add(Notification notification) throws Exception {
        String sql = "INSERT INTO tblNotification(Title, Content, SenderUsername, SenderDepartment, TargetType, TargetClassID, CreatedDate, IsActive) "
                +
                "VALUES(?, ?, ?, ?, ?, ?, GETDATE(), ?)";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, notification.getTitle());
            ps.setString(2, notification.getContent());
            ps.setString(3, notification.getSenderUsername());
            ps.setString(4, notification.getSenderDepartment());
            ps.setString(5, notification.getTargetType());

            if (notification.getTargetClassID() != null) {
                ps.setInt(6, notification.getTargetClassID());
            } else {
                ps.setNull(6, Types.INTEGER);
            }

            ps.setBoolean(7, notification.isIsActive());

            int affected = ps.executeUpdate();
            if (affected <= 0) {
                throw new Exception("Insert notification failed");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        throw new Exception("Cannot generate notification ID");
    }

    public boolean updateStatus(int notificationID, boolean isActive) {
        String sql = "UPDATE tblNotification SET IsActive = ? WHERE NotificationID = ?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, isActive);
            ps.setInt(2, notificationID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Notification> findAllForAdmin() {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT n.NotificationID, n.Title, n.Content, n.SenderUsername, " +
                "       n.SenderDepartment, n.TargetType, n.TargetClassID, n.CreatedDate, n.IsActive, " +
                "       c.ClassName AS TargetClassName, " +
                "       p.fullname AS SenderFullName " +
                "FROM tblNotification n " +
                "LEFT JOIN tblClass c ON n.TargetClassID = c.ClassID " +
                "LEFT JOIN Users u ON n.SenderUsername = u.username " +
                "LEFT JOIN Person p ON u.person_id = p.person_id " +
                "ORDER BY n.CreatedDate DESC, n.NotificationID DESC";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapNotification(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Notification findById(int notificationID) {
        String sql = "SELECT n.NotificationID, n.Title, n.Content, n.SenderUsername, " +
                "       n.SenderDepartment, n.TargetType, n.TargetClassID, n.CreatedDate, n.IsActive, " +
                "       c.ClassName AS TargetClassName, " +
                "       p.fullname AS SenderFullName " +
                "FROM tblNotification n " +
                "LEFT JOIN tblClass c ON n.TargetClassID = c.ClassID " +
                "LEFT JOIN Users u ON n.SenderUsername = u.username " +
                "LEFT JOIN Person p ON u.person_id = p.person_id " +
                "WHERE n.NotificationID = ?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, notificationID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapNotification(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Notification> findBySenderUsername(String senderUsername) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT n.NotificationID, n.Title, n.Content, n.SenderUsername, " +
                "       n.SenderDepartment, n.TargetType, n.TargetClassID, n.CreatedDate, n.IsActive, " +
                "       c.ClassName AS TargetClassName, " +
                "       p.fullname AS SenderFullName " +
                "FROM tblNotification n " +
                "LEFT JOIN tblClass c ON n.TargetClassID = c.ClassID " +
                "LEFT JOIN Users u ON n.SenderUsername = u.username " +
                "LEFT JOIN Person p ON u.person_id = p.person_id " +
                "WHERE n.SenderUsername = ? " +
                "ORDER BY n.CreatedDate DESC, n.NotificationID DESC";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, senderUsername);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapNotification(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<tblClass> findActiveClasses() {
        List<tblClass> list = new ArrayList<>();
        String sql = "SELECT ClassID, ClassName, GradeID, CohortID, MaxStudents, CurrentStudents, SchoolYear, IsActive "
                +
                "FROM tblClass WHERE IsActive = 1 ORDER BY ClassName";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                tblClass c = new tblClass();
                c.setClassID(rs.getInt("ClassID"));
                c.setClassName(rs.getString("ClassName"));
                c.setGradeID(rs.getInt("GradeID"));
                int cohortId = rs.getInt("CohortID");
                if (rs.wasNull()) {
                    c.setCohortID(null);
                } else {
                    c.setCohortID(cohortId);
                }
                c.setMaxStudents(rs.getInt("MaxStudents"));
                c.setCurrentStudents(rs.getInt("CurrentStudents"));
                c.setSchoolYear(rs.getString("SchoolYear"));
                c.setActive(rs.getBoolean("IsActive"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<tblClass> findTeacherAllowedClasses(String teacherUsername) {
        List<tblClass> list = new ArrayList<>();
        String sql = "SELECT DISTINCT c.ClassID, c.ClassName, c.GradeID, c.CohortID, c.MaxStudents, c.CurrentStudents, c.SchoolYear, c.IsActive "
                +
                "FROM tblSchedule s " +
                "INNER JOIN tblClass c ON s.ClassID = c.ClassID " +
                "WHERE s.TeacherID = ? AND s.IsActive = 1 AND c.IsActive = 1 " +
                "ORDER BY c.ClassName";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, teacherUsername);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tblClass c = new tblClass();
                    c.setClassID(rs.getInt("ClassID"));
                    c.setClassName(rs.getString("ClassName"));
                    c.setGradeID(rs.getInt("GradeID"));
                    int cohortId = rs.getInt("CohortID");
                    if (rs.wasNull()) {
                        c.setCohortID(null);
                    } else {
                        c.setCohortID(cohortId);
                    }
                    c.setMaxStudents(rs.getInt("MaxStudents"));
                    c.setCurrentStudents(rs.getInt("CurrentStudents"));
                    c.setSchoolYear(rs.getString("SchoolYear"));
                    c.setActive(rs.getBoolean("IsActive"));
                    list.add(c);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean isTeacherAllowedForClass(String teacherUsername, int classId) {
        String sql = "SELECT 1 FROM tblSchedule WHERE TeacherID = ? AND ClassID = ? AND IsActive = 1";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, teacherUsername);
            ps.setInt(2, classId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteHard(int notificationID) {
        String deleteAttachmentSql = "DELETE FROM tblNotificationAttachment WHERE NotificationID = ?";
        String deleteReadSql = "DELETE FROM tblNotificationRead WHERE NotificationID = ?";
        String deleteNotificationSql = "DELETE FROM tblNotification WHERE NotificationID = ?";

        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement ps = conn.prepareStatement(deleteAttachmentSql)) {
                    ps.setInt(1, notificationID);
                    ps.executeUpdate();
                } catch (Exception e) {
                    if (!isNotificationReadTableMissing(e) && (e.getMessage() == null
                            || !e.getMessage().toLowerCase().contains("tblnotificationattachment"))) {
                        throw e;
                    }
                }

                try (PreparedStatement ps = conn.prepareStatement(deleteReadSql)) {
                    ps.setInt(1, notificationID);
                    ps.executeUpdate();
                } catch (Exception e) {
                    if (!isNotificationReadTableMissing(e)) {
                        throw e;
                    }
                }

                int affected;
                try (PreparedStatement ps = conn.prepareStatement(deleteNotificationSql)) {
                    ps.setInt(1, notificationID);
                    affected = ps.executeUpdate();
                }

                conn.commit();
                return affected > 0;
            } catch (Exception e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Notification> findForTeacherInbox(String teacherUsername) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT DISTINCT n.NotificationID, n.Title, n.Content, n.SenderUsername, " +
                "       n.SenderDepartment, n.TargetType, n.TargetClassID, n.CreatedDate, n.IsActive, " +
                "       c.ClassName AS TargetClassName, " +
                "       p.fullname AS SenderFullName " +
                "FROM tblNotification n " +
                "LEFT JOIN tblClass c ON n.TargetClassID = c.ClassID " +
                "LEFT JOIN Users u ON n.SenderUsername = u.username " +
                "LEFT JOIN Person p ON u.person_id = p.person_id " +
                "WHERE n.IsActive = 1 AND (n.TargetType IN ('ALL', 'ALL_TEACHER') OR n.TargetClassID IN (" +
                "    SELECT DISTINCT s.ClassID FROM tblSchedule s WHERE s.TeacherID = ? AND s.IsActive = 1" +
                ")) " +
                "ORDER BY n.CreatedDate DESC, n.NotificationID DESC";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, teacherUsername);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapNotification(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Notification> findForStudent(String studentId) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT DISTINCT n.NotificationID, n.Title, n.Content, n.SenderUsername, " +
                "       n.SenderDepartment, n.TargetType, n.TargetClassID, n.CreatedDate, n.IsActive, " +
                "       c.ClassName AS TargetClassName, " +
                "       p.fullname AS SenderFullName " +
                "FROM tblNotification n " +
                "LEFT JOIN tblClass c ON n.TargetClassID = c.ClassID " +
                "LEFT JOIN Users u ON n.SenderUsername = u.username " +
                "LEFT JOIN Person p ON u.person_id = p.person_id " +
                "WHERE n.IsActive = 1 AND (n.TargetType IN ('ALL', 'ALL_STUDENT') OR n.TargetClassID IN (" +
                "    SELECT sc.ClassID FROM tblStudentClass sc WHERE sc.StudentID = ? AND sc.IsActive = 1 AND sc.ClassID > 0"
                +
                ")) " +
                "ORDER BY n.CreatedDate DESC, n.NotificationID DESC";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapNotification(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int countUnreadForAdmin(int userId) {
        String sql = "SELECT COUNT(*) AS unreadCount " +
                "FROM tblNotification n " +
                "WHERE n.IsActive = 1 " +
                "AND n.NotificationID NOT IN (" +
                "    SELECT nr.NotificationID FROM tblNotificationRead nr WHERE nr.UserID = ?" +
                ")";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("unreadCount");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int countUnreadForTeacher(int userId, String teacherUsername) {
        String sql = "SELECT COUNT(DISTINCT n.NotificationID) AS unreadCount " +
                "FROM tblNotification n " +
                "WHERE n.IsActive = 1 " +
                "AND (n.TargetType IN ('ALL', 'ALL_TEACHER') OR n.TargetClassID IN (" +
                "    SELECT DISTINCT s.ClassID FROM tblSchedule s WHERE s.TeacherID = ? AND s.IsActive = 1" +
                ")) " +
                "AND n.NotificationID NOT IN (" +
                "    SELECT nr.NotificationID FROM tblNotificationRead nr WHERE nr.UserID = ?" +
                ")";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, teacherUsername);
            ps.setInt(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("unreadCount");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int countUnreadForStudent(int userId, String studentId) {
        String sql = "SELECT COUNT(DISTINCT n.NotificationID) AS unreadCount " +
                "FROM tblNotification n " +
                "WHERE n.IsActive = 1 " +
                "AND (n.TargetType IN ('ALL', 'ALL_STUDENT') OR n.TargetClassID IN (" +
                "    SELECT sc.ClassID FROM tblStudentClass sc WHERE sc.StudentID = ? AND sc.IsActive = 1 AND sc.ClassID > 0"
                +
                ")) " +
                "AND n.NotificationID NOT IN (" +
                "    SELECT nr.NotificationID FROM tblNotificationRead nr WHERE nr.UserID = ?" +
                ")";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, studentId);
            ps.setInt(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("unreadCount");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public boolean markAsRead(int userId, int notificationId) {
        String sql = "IF NOT EXISTS (SELECT 1 FROM tblNotificationRead WHERE UserID = ? AND NotificationID = ?) " +
                "INSERT INTO tblNotificationRead(UserID, NotificationID, ReadDate) VALUES (?, ?, GETDATE())";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, notificationId);
            ps.setInt(3, userId);
            ps.setInt(4, notificationId);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void markAllAsRead(int userId, List<Notification> notifications) throws SQLException {
        if (notifications == null || notifications.isEmpty()) {
            return;
        }

        String sql = "IF NOT EXISTS (SELECT 1 FROM tblNotificationRead WHERE UserID = ? AND NotificationID = ?) " +
                "INSERT INTO tblNotificationRead(UserID, NotificationID, ReadDate) VALUES (?, ?, GETDATE())";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Notification n : notifications) {
                ps.setInt(1, userId);
                ps.setInt(2, n.getNotificationID());
                ps.setInt(3, userId);
                ps.setInt(4, n.getNotificationID());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            if (isNotificationReadTableMissing(e)) {
                return;
            }
            throw e;
        }
    }

    public List<NotificationAttachment> findAttachmentsByNotificationId(int notificationID) {
        List<NotificationAttachment> attachments = new ArrayList<>();
        String sql = "SELECT AttachmentID, NotificationID, FileName, FilePath, FileSize " +
                "FROM tblNotificationAttachment WHERE NotificationID = ? ORDER BY AttachmentID ASC";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, notificationID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    attachments.add(mapAttachment(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return attachments;
    }

    public List<NotificationAttachment> getAttachmentsByNotificationId(int notificationID) {
        return findAttachmentsByNotificationId(notificationID);
    }

    public NotificationAttachment findAttachmentById(int attachmentID) {
        String sql = "SELECT AttachmentID, NotificationID, FileName, FilePath, FileSize " +
                "FROM tblNotificationAttachment WHERE AttachmentID = ?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, attachmentID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapAttachment(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public int addAttachment(NotificationAttachment attachment) throws Exception {
        String sql = "INSERT INTO tblNotificationAttachment(NotificationID, FileName, FilePath, FileSize) VALUES(?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, attachment.getNotificationID());
            ps.setString(2, attachment.getFileName());
            ps.setString(3, attachment.getFilePath());

            if (attachment.getFileSizeKB() != null) {
                ps.setInt(4, attachment.getFileSizeKB());
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            int affected = ps.executeUpdate();
            if (affected <= 0) {
                throw new Exception("Insert notification attachment failed");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        throw new Exception("Cannot generate attachment ID");
    }

    public void addAttachments(int notificationID, List<NotificationAttachment> attachments) throws Exception {
        if (attachments == null || attachments.isEmpty()) {
            return;
        }
        for (NotificationAttachment attachment : attachments) {
            attachment.setNotificationID(notificationID);
            addAttachment(attachment);
        }
    }
}