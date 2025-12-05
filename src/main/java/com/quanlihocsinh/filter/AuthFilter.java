package com.quanlihocsinh.filter;

import com.quanlihocsinh.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Không cần xử lý
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length());

        // Các đường dẫn công khai
        if (path.startsWith("/login")
                || path.startsWith("/logout")
                || path.startsWith("/assets/")
                || path.startsWith("/public/")
                || path.equals("/")) {

            chain.doFilter(request, response);
            return;
        }

        // Cho phép tạo admin đầu tiên (chưa login)
        if (path.startsWith("/admin/createUser")) {

            HttpSession session = req.getSession(false);

            // Nếu chưa login -> cho phép để tạo admin đầu tiên
            if (session == null || session.getAttribute("user") == null) {
                chain.doFilter(request, response);
                return;
            }

            // Nếu đã login -> chỉ admin mới được dùng createUser
            User u = (User) session.getAttribute("user");
            if (u.getRoleId() != 1) {
                resp.sendRedirect(req.getContextPath() + "/access-denied");
                return;
            }

            chain.doFilter(request, response);
            return;
        }

        // Kiểm tra session cho tất cả đường dẫn khác
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");

        // Phân quyền
        if (path.startsWith("/admin/") && user.getRoleId() != 1) {
            resp.sendRedirect(req.getContextPath() + "/access-denied");
            return;
        }

        if (path.startsWith("/teacher/") && user.getRoleId() != 2 && user.getRoleId() != 1) {
            resp.sendRedirect(req.getContextPath() + "/access-denied");
            return;
        }

        if (path.startsWith("/student/") && user.getRoleId() != 3 && user.getRoleId() != 1) {
            resp.sendRedirect(req.getContextPath() + "/access-denied");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Không cần xử lý
    }
}
