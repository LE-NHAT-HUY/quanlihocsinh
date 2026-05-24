package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.model.Notification;
import com.quanlihocsinh.model.NotificationAttachment;
import com.quanlihocsinh.model.User;
import com.quanlihocsinh.model.tblClass;
import com.quanlihocsinh.service.NotificationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Part;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.File;

@WebServlet("/admin/notifications/*")
@MultipartConfig(maxFileSize = 10 * 1024 * 1024, maxRequestSize = 20 * 1024 * 1024)
public class NotificationController extends HttpServlet {

    private static final String ATTACHMENT_UPLOAD_DIR = "/assets/uploads/notifications";

    private NotificationService notificationService;

    @Override
    public void init() throws ServletException {
        super.init();
        notificationService = new NotificationService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = resolveAction(request);
        try {
            switch (action) {
                case "/add":
                    showAddForm(request, response);
                    break;
                case "/list":
                default:
                    showList(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = resolveAction(request);
        try {
            switch (action) {
                case "/add":
                    createNotification(request, response);
                    break;
                case "/detail":
                    showDetail(request, response);
                    break;
                case "/hide":
                    hideNotification(request, response);
                    break;
                case "/delete":
                    deleteNotification(request, response);
                    break;
                case "/download":
                    downloadAttachment(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/admin/notifications");
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private String resolveAction(HttpServletRequest request) {
        String action = request.getPathInfo();
        if (action != null && !"/".equals(action)) {
            return action;
        }

        String legacyAction = request.getParameter("action");
        if (legacyAction != null && !legacyAction.trim().isEmpty()) {
            return "/" + legacyAction.trim();
        }

        return "/list";
    }

    private void showList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = session == null ? null : (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<Notification> notifications = notificationService.getAdminNotifications();
        try {
            notificationService.markAllAsRead(user.getUserID(), notifications);
        } catch (Exception e) {
            throw new ServletException("Không thể cập nhật trạng thái đã đọc", e);
        }

        request.setAttribute("notifications", notifications);
        request.setAttribute("notifUnreadCount", notificationService.countUnreadForAdmin(user.getUserID()));
        request.getRequestDispatcher("/WEB-INF/views/admin/notification/list.jsp").forward(request, response);
    }

    private void showDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = parseInt(request.getParameter("id"));
        if (id <= 0) {
            response.sendRedirect(request.getContextPath() + "/admin/notifications");
            return;
        }

        Notification notification = notificationService.getNotificationById(id);
        if (notification == null) {
            setFlashMessage(request, null, "Không tìm thấy thông báo");
            response.sendRedirect(request.getContextPath() + "/admin/notifications");
            return;
        }

        request.setAttribute("notification", notification);
        request.setAttribute("attachments", notificationService.getNotificationAttachments(id));
        request.getRequestDispatcher("/WEB-INF/views/admin/notification/detail.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = session == null ? null : (User) session.getAttribute("user");
        if (user != null) {
            request.setAttribute("notifUnreadCount", notificationService.countUnreadForAdmin(user.getUserID()));
        }
        request.setAttribute("classes", notificationService.getActiveClassesForAdmin());
        request.setAttribute("targetTypes", notificationService.getAllowedTargetTypesForAdmin());
        request.getRequestDispatcher("/WEB-INF/views/admin/notification/add.jsp").forward(request, response);
    }

    private void createNotification(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession(false);
        User user = session == null ? null : (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Notification notification = buildNotificationFromRequest(request);
        notification.setSenderUsername(user.getUsername());
        notification.setSenderDepartment(resolveSenderDepartment(request));
        notification.setIsActive(true);

        int createdNotificationId = 0;
        try {
            createdNotificationId = notificationService.createNotification(notification);
            List<NotificationAttachment> attachments = saveUploadedAttachments(request, createdNotificationId);
            notificationService.addNotificationAttachments(createdNotificationId, attachments);
            setFlashMessage(request, "Gửi thông báo thành công", null);
            response.sendRedirect(request.getContextPath() + "/admin/notifications");
        } catch (Exception ex) {
            if (createdNotificationId > 0) {
                notificationService.deleteNotificationHard(createdNotificationId);
            }
            request.setAttribute("notification", notification);
            request.setAttribute("errorMessage", ex.getMessage());
            request.setAttribute("classes", notificationService.getActiveClassesForAdmin());
            request.setAttribute("targetTypes", notificationService.getAllowedTargetTypesForAdmin());

            HttpSession currentSession = request.getSession(false);
            User currentUser = currentSession == null ? null : (User) currentSession.getAttribute("user");
            if (currentUser != null) {
                request.setAttribute("notifUnreadCount",
                        notificationService.countUnreadForAdmin(currentUser.getUserID()));
            }

            request.getRequestDispatcher("/WEB-INF/views/admin/notification/add.jsp").forward(request, response);
        }
    }

    private void downloadAttachment(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int attachmentId = parseInt(request.getParameter("attachmentId"));
        NotificationAttachment attachment = notificationService.getNotificationAttachmentById(attachmentId);
        if (attachment == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String realPath = request.getServletContext().getRealPath(attachment.getFilePath());
        if (realPath == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        File file = new File(realPath);
        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\""
                + URLEncoder.encode(attachment.getFileName(), StandardCharsets.UTF_8.name()).replace("+", "%20")
                + "\"");
        response.setContentLengthLong(file.length());

        try (InputStream inputStream = Files.newInputStream(file.toPath());
                OutputStream outputStream = response.getOutputStream()) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    private void hideNotification(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = parseInt(request.getParameter("id"));
        if (id > 0 && notificationService.updateStatus(id, false)) {
            setFlashMessage(request, "Đã ẩn thông báo", null);
        } else {
            setFlashMessage(request, null, "Không thể ẩn thông báo");
        }
        response.sendRedirect(request.getContextPath() + "/admin/notifications");
    }

    private void deleteNotification(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = parseInt(request.getParameter("id"));
        if (id > 0 && notificationService.deleteNotificationHard(id)) {
            setFlashMessage(request, "Đã xóa thông báo", null);
        } else {
            setFlashMessage(request, null, "Không thể xóa thông báo");
        }
        response.sendRedirect(request.getContextPath() + "/admin/notifications");
    }

    private Notification buildNotificationFromRequest(HttpServletRequest request) {
        Notification notification = new Notification();
        notification.setTitle(trimToEmpty(request.getParameter("title")));
        notification.setContent(trimToEmpty(request.getParameter("content")));
        String targetType = trimToEmpty(request.getParameter("targetType"));
        notification.setTargetType(targetType);

        if (NotificationService.TARGET_CLASS.equalsIgnoreCase(targetType)) {
            int classId = parseInt(request.getParameter("targetClassID"));
            notification.setTargetClassID(classId > 0 ? classId : null);
        } else {
            notification.setTargetClassID(null);
        }

        return notification;
    }

    private String resolveSenderDepartment(HttpServletRequest request) {
        String senderDepartment = trimToEmpty(request.getParameter("senderDepartment"));
        if (!senderDepartment.isEmpty()) {
            return senderDepartment;
        }
        return "Phòng Đào tạo";
    }

    private List<NotificationAttachment> saveUploadedAttachments(HttpServletRequest request, int notificationId)
            throws Exception {
        List<NotificationAttachment> attachments = new ArrayList<>();
        String uploadFolder = request.getServletContext().getRealPath(ATTACHMENT_UPLOAD_DIR);
        if (uploadFolder == null) {
            return attachments;
        }

        Files.createDirectories(Paths.get(uploadFolder));

        for (Part part : request.getParts()) {
            if (!isAttachmentPart(part)) {
                continue;
            }

            String originalFileName = getFileName(part);
            String storedFileName = UUID.randomUUID().toString().replace("-", "") + "_" + originalFileName;
            Path storedPath = Paths.get(uploadFolder, storedFileName);

            try (InputStream inputStream = part.getInputStream()) {
                Files.copy(inputStream, storedPath, StandardCopyOption.REPLACE_EXISTING);
            }

            NotificationAttachment attachment = new NotificationAttachment();
            attachment.setNotificationID(notificationId);
            attachment.setFileName(originalFileName);
            attachment.setFilePath(ATTACHMENT_UPLOAD_DIR + "/" + storedFileName);
            attachment.setFileSizeKB((int) Math.max(1, (part.getSize() + 1023) / 1024));
            attachments.add(attachment);
        }

        return attachments;
    }

    private boolean isAttachmentPart(Part part) {
        if (part == null) {
            return false;
        }
        String submittedFileName = getFileName(part);
        return submittedFileName != null && !submittedFileName.trim().isEmpty() && part.getSize() > 0;
    }

    private String getFileName(Part part) {
        if (part == null) {
            return null;
        }

        String contentDisposition = part.getHeader("content-disposition");
        if (contentDisposition == null) {
            return null;
        }

        for (String headerValue : contentDisposition.split(";")) {
            String trimmedValue = headerValue.trim();
            if (trimmedValue.startsWith("filename")) {
                String fileName = trimmedValue.substring(trimmedValue.indexOf('=') + 1).trim().replace("\"", "");
                if (!fileName.isEmpty()) {
                    return Paths.get(fileName).getFileName().toString();
                }
            }
        }

        return null;
    }

    private void setFlashMessage(HttpServletRequest request, String success, String error) {
        HttpSession session = request.getSession();
        if (success != null) {
            session.setAttribute("flashSuccess", success);
        }
        if (error != null) {
            session.setAttribute("flashError", error);
        }
    }

    private String trimToEmpty(String value) {
        return value == null ? "" : value.trim();
    }

    private int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }
}
