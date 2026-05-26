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
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length());
        System.out.println("[AuthFilter] " + req.getMethod() + " " + path);

        if (path.startsWith("/login")
                || path.startsWith("/logout")
                || path.startsWith("/assets/")
                || path.startsWith("/public/")
                || path.equals("/")) {

            chain.doFilter(request, response);
            return;
        }

        if (path.startsWith("/admin/createUser")) {

            HttpSession session = req.getSession(false);

            if (session == null || session.getAttribute("user") == null) {
                chain.doFilter(request, response);
                return;
            }

            User u = (User) session.getAttribute("user");
            if (u.getRoleId() != 1) {
                resp.sendRedirect(req.getContextPath() + "/access-denied");
                return;
            }

            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            System.out.println("[AuthFilter] Redirect to /login because session/user is missing for path: " + path);
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");

        if (path.startsWith("/admin/") && user.getRoleId() != 1) {
            System.out.println("[AuthFilter] Access denied for admin path: " + path + ", roleId=" + user.getRoleId());
            resp.sendRedirect(req.getContextPath() + "/access-denied");
            return;
        }

        if (path.startsWith("/teacher/") && user.getRoleId() != 2 && user.getRoleId() != 1) {
            System.out.println("[AuthFilter] Access denied for teacher path: " + path + ", roleId=" + user.getRoleId());
            resp.sendRedirect(req.getContextPath() + "/access-denied");
            return;
        }

        if (path.startsWith("/student/") && user.getRoleId() != 3 && user.getRoleId() != 1) {
            System.out.println("[AuthFilter] Access denied for student path: " + path + ", roleId=" + user.getRoleId());
            resp.sendRedirect(req.getContextPath() + "/access-denied");
            return;
        }

        System.out.println("[AuthFilter] Passing through: " + path);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
