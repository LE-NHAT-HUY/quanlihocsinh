package com.quanlihocsinh.Controller.student;

import com.quanlihocsinh.dao.MenuDAO;
import com.quanlihocsinh.model.Notification;
import com.quanlihocsinh.model.NotificationAttachment;
import com.quanlihocsinh.model.User;
import com.quanlihocsinh.model.menu;
import com.quanlihocsinh.service.NotificationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/student/notifications/*")
public class NotificationController extends HttpServlet {

    private NotificationService notificationService;
    private MenuDAO menuDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        notificationService = new NotificationService();
        menuDAO = new MenuDAO();
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
                case "/list":
                default:
                    showList(request, response);
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

        List<menu> menuList = menuDAO.getAllMenus();
        request.setAttribute("menuList", menuList);

        String keyword = trimToEmpty(request.getParameter("q"));
        List<Notification> notifications = new ArrayList<>(
                notificationService.getStudentNotifications(user.getUsername()));
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
        request.setAttribute("notifUnreadCount",
                notificationService.countUnreadForStudent(user.getUserID(), user.getUsername()));
        request.setAttribute("contentPage", "/WEB-INF/views/student/notification/list.jsp");
        request.setAttribute("pageTitle", "Thông báo của tôi");
        request.getRequestDispatcher("/WEB-INF/views/shared/Layout.jsp").forward(request, response);
    }

    private void showDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = session == null ? null : (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<menu> menuList = menuDAO.getAllMenus();
        request.setAttribute("menuList", menuList);

        int id = parseInt(request.getParameter("id"));
        Notification notification = notificationService.getNotificationById(id);
        if (notification == null) {
            setFlashMessage(request, null, "Không tìm thấy thông báo");
            response.sendRedirect(request.getContextPath() + "/student/notifications");
            return;
        }

        notificationService.markAsRead(user.getUserID(), id);
        request.setAttribute("notification", notification);
        request.setAttribute("attachments", notificationService.getNotificationAttachments(id));
        request.setAttribute("notifUnreadCount",
                notificationService.countUnreadForStudent(user.getUserID(), user.getUsername()));
        request.setAttribute("contentPage", "/WEB-INF/views/student/notification/notification-detail.jsp");
        request.setAttribute("pageTitle", "Chi tiết thông báo");
        request.getRequestDispatcher("/WEB-INF/views/shared/Layout.jsp").forward(request, response);
    }

    private void downloadAttachment(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int attachmentId = parseInt(request.getParameter("attachmentId"));
        NotificationAttachment attachment = notificationService.getNotificationAttachmentById(attachmentId);
        if (attachment == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        sendAttachmentFile(request, response, attachment.getFilePath(), attachment.getFileName());
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
