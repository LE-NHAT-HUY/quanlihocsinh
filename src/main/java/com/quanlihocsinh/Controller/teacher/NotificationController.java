package com.quanlihocsinh.Controller.teacher;

import com.quanlihocsinh.model.Notification;
import com.quanlihocsinh.model.User;
import com.quanlihocsinh.service.NotificationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/teacher/notifications/*")
public class NotificationController extends HttpServlet {

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
                case "/detail":
                    showDetail(request, response);
                    break;
                case "/download":
                    downloadAttachment(request, response);
                    break;
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
                default:
                    response.sendRedirect(request.getContextPath() + "/teacher/notifications");
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

        String keyword = trimToEmpty(request.getParameter("q"));
        List<Notification> notifications = new ArrayList<>(
                notificationService.getTeacherInboxNotifications(user.getUsername()));
        if (!keyword.isEmpty()) {
            String lowerKeyword = keyword.toLowerCase();
            notifications.removeIf(notification -> !matchesKeyword(notification, lowerKeyword));
        }

        try {
            notificationService.markAllAsRead(user.getUserID(), notifications);
        } catch (Exception e) {
            throw new ServletException("Không thể cập nhật trạng thái đã đọc", e);
        }

        request.setAttribute("notifications", notifications);
        request.setAttribute("searchKeyword", keyword);
        request.setAttribute("senderUsername", user.getUsername());
        request.setAttribute("notifUnreadCount",
                notificationService.countUnreadForTeacher(user.getUserID(), user.getUsername()));
        request.setAttribute("contentPage", "/WEB-INF/views/teacher/notification/list.jsp");
        request.setAttribute("pageTitle", "Thông báo giáo viên");
        request.getRequestDispatcher("/WEB-INF/views/shared/Layoutteacher.jsp").forward(request, response);
    }

    private void showDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = session == null ? null : (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int id = parseInt(request.getParameter("id"));
        Notification notification = notificationService.getNotificationById(id);
        if (notification == null) {
            setFlashMessage(request, null, "Không tìm thấy thông báo");
            response.sendRedirect(request.getContextPath() + "/teacher/notifications");
            return;
        }

        notificationService.markAsRead(user.getUserID(), id);
        request.setAttribute("notification", notification);
        request.setAttribute("attachments", notificationService.getNotificationAttachments(id));
        request.setAttribute("notifUnreadCount",
                notificationService.countUnreadForTeacher(user.getUserID(), user.getUsername()));
        request.setAttribute("contentPage", "/WEB-INF/views/teacher/notification/notification-detail.jsp");
        request.setAttribute("pageTitle", "Chi tiết thông báo");
        request.getRequestDispatcher("/WEB-INF/views/shared/Layoutteacher.jsp").forward(request, response);
    }

    private void downloadAttachment(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int attachmentId = parseInt(request.getParameter("attachmentId"));
        com.quanlihocsinh.model.NotificationAttachment attachment = notificationService
                .getNotificationAttachmentById(attachmentId);
        if (attachment == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        sendAttachmentFile(request, response, attachment.getFilePath(), attachment.getFileName());
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = session == null ? null : (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.setAttribute("classes", notificationService.getAllowedClassesForTeacher(user.getUsername()));
        request.setAttribute("notifUnreadCount",
                notificationService.countUnreadForTeacher(user.getUserID(), user.getUsername()));
        request.setAttribute("contentPage", "/WEB-INF/views/teacher/notification/add.jsp");
        request.setAttribute("pageTitle", "Soạn thông báo lớp");
        request.getRequestDispatcher("/WEB-INF/views/shared/Layoutteacher.jsp").forward(request, response);
    }

    private void createNotification(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        HttpSession session = request.getSession(false);
        User user = session == null ? null : (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Notification notification = new Notification();
        notification.setTitle(trimToEmpty(request.getParameter("title")));
        notification.setContent(trimToEmpty(request.getParameter("content")));
        notification.setSenderUsername(user.getUsername());
        notification.setTargetType(NotificationService.TARGET_CLASS);
        notification.setTargetClassID(parseInt(request.getParameter("targetClassID")));
        notification.setIsActive(true);

        try {
            if (!notificationService.canTeacherSendToClass(user.getUsername(), notification.getTargetClassID())) {
                throw new IllegalArgumentException("Giáo viên chỉ được gửi đến lớp mình đang dạy");
            }

            notificationService.createNotification(notification);
            setFlashMessage(request, "Gửi thông báo thành công", null);
            response.sendRedirect(request.getContextPath() + "/teacher/notifications");
        } catch (Exception ex) {
            request.setAttribute("notification", notification);
            request.setAttribute("errorMessage", ex.getMessage());
            request.setAttribute("classes", notificationService.getAllowedClassesForTeacher(user.getUsername()));
            request.setAttribute("notifUnreadCount",
                    notificationService.countUnreadForTeacher(user.getUserID(), user.getUsername()));
            request.setAttribute("contentPage", "/WEB-INF/views/teacher/notification/add.jsp");
            request.setAttribute("pageTitle", "Soạn thông báo lớp");
            request.getRequestDispatcher("/WEB-INF/views/shared/Layoutteacher.jsp").forward(request, response);
        }
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

    private boolean matchesKeyword(Notification notification, String lowerKeyword) {
        return containsIgnoreCase(notification.getTitle(), lowerKeyword)
                || containsIgnoreCase(notification.getContent(), lowerKeyword)
                || containsIgnoreCase(notification.getSenderFullName(), lowerKeyword)
                || containsIgnoreCase(notification.getSenderUsername(), lowerKeyword)
                || containsIgnoreCase(notification.getSenderDepartment(), lowerKeyword);
    }

    private boolean containsIgnoreCase(String value, String lowerKeyword) {
        return value != null && value.toLowerCase().contains(lowerKeyword);
    }

    private void sendAttachmentFile(HttpServletRequest request, HttpServletResponse response, String filePath,
            String fileName) throws IOException {
        String realPath = request.getServletContext().getRealPath(filePath);
        if (realPath == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        java.io.File file = new java.io.File(realPath);
        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.setContentLengthLong(file.length());

        try (java.io.InputStream inputStream = java.nio.file.Files.newInputStream(file.toPath());
                java.io.OutputStream outputStream = response.getOutputStream()) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    private int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }
}
