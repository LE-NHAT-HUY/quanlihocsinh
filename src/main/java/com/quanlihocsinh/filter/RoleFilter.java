package com.quanlihocsinh.filter;

import com.quanlihocsinh.model.Role;
import com.quanlihocsinh.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter("/*")
public class RoleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String uri = req.getRequestURI();

        if (uri.contains("/assets/") || uri.contains("/css/") ||
                uri.contains("/js/") || uri.contains("/img/") ||
                uri.endsWith(".png") || uri.endsWith(".jpg") ||
                uri.endsWith(".jpeg") || uri.endsWith(".gif") ||
                uri.endsWith(".svg")) {

            chain.doFilter(request, response);
            return;
        }

        if (uri.endsWith("login") || uri.endsWith("register") || uri.endsWith("logout")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            chain.doFilter(request, response);
            return;
        }

        if (hasRole(user, "ADMIN")) {
            chain.doFilter(request, response);
            return;
        }

        if (uri.contains("/teacher/")) {
            if (hasRole(user, "TEACHER")) {
                chain.doFilter(request, response);
                return;
            }
            resp.sendRedirect(req.getContextPath() + "/403.jsp");
            return;
        }

        if (uri.contains("/student/")) {
            if (hasRole(user, "STUDENT")) {
                chain.doFilter(request, response);
                return;
            }
            resp.sendRedirect(req.getContextPath() + "/403.jsp");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private boolean hasRole(User user, String roleName) {
        if (user.getRoles() == null)
            return false;

        for (Role r : user.getRoles()) {
            if (r.getRoleName().equalsIgnoreCase(roleName)) {
                return true;
            }
        }
        return false;
    }
}
