package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.dao.RoleDAO;
import com.quanlihocsinh.dao.UserDAO;
import com.quanlihocsinh.model.Role;
import com.quanlihocsinh.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/admin/assign-role/*")
public class RoleAssignController extends HttpServlet {

    private UserDAO userDAO = new UserDAO();
    private RoleDAO roleDAO = new RoleDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        String id = req.getParameter("id");

        // Nếu chưa chọn user → hiện danh sách user
        if (id == null) {
            List<User> users = userDAO.getAllUsers();

            // Tạo map userId → list role của user
            Map<Integer, List<Role>> userRolesMap = new HashMap<>();
            for (User u : users) {
                List<Role> roles = roleDAO.getRolesOfUser(u.getUserID());
                userRolesMap.put(u.getUserID(), roles);
            }

            req.setAttribute("users", users);
            req.setAttribute("userRolesMap", userRolesMap);

            req.getRequestDispatcher("/WEB-INF/views/admin/role/role-assign-users.jsp").forward(req, resp);
            return;
        }

        int userId = Integer.parseInt(id);

        // Lấy thông tin user
        User user = userDAO.getUserById(userId);

        // Lấy tất cả roles trong hệ thống
        List<Role> allRoles = roleDAO.getAllRoles();

        // Lấy role mà user đã có
        List<Integer> assignedRoles = roleDAO.getRoleIdsOfUser(userId);

        req.setAttribute("user", user);
        req.setAttribute("roles", allRoles);
        req.setAttribute("assigned", assignedRoles);

        req.getRequestDispatcher("/WEB-INF/views/admin/role/role-assign.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        int userId = Integer.parseInt(req.getParameter("userId"));

        // Các role được tick
        String[] selectedRoleIds = req.getParameterValues("role");

        List<Integer> newRoles = new ArrayList<>();

        if (selectedRoleIds != null) {
            for (String r : selectedRoleIds) {
                newRoles.add(Integer.parseInt(r));
            }
        }

        // Cập nhật quyền
        roleDAO.updateUserRoles(userId, newRoles);

        resp.sendRedirect(req.getContextPath() + "/admin/assign-role");
    }
}
