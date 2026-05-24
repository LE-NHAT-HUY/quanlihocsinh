package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.dao.DepartmentRepository;
import com.quanlihocsinh.model.Department;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/department/*")
public class DepartmentController extends HttpServlet {

    private DepartmentRepository departmentRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        departmentRepository = new DepartmentRepository();
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
                    request.getRequestDispatcher("/WEB-INF/views/admin/department/add.jsp").forward(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/delete":
                    deleteDepartment(request, response);
                    break;
                default:
                    listDepartments(request, response);
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
                    insertDepartment(request, response);
                    break;
                case "/edit":
                    updateDepartment(request, response);
                    break;
                case "/toggleStatus":
                    toggleStatus(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/admin/department/list");
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void listDepartments(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Department> list = departmentRepository.getAll();
        request.setAttribute("departments", list);
        request.getRequestDispatcher("/WEB-INF/views/admin/department/list.jsp").forward(request, response);
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

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        Department department = departmentRepository.getById(id);
        request.setAttribute("department", department);
        request.getRequestDispatcher("/WEB-INF/views/admin/department/edit.jsp").forward(request, response);
    }

    private void insertDepartment(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Department department = new Department();
        department.setDepartmentName(request.getParameter("departmentName"));
        department.setDescription(request.getParameter("description"));
        department.setIsActive(request.getParameter("isActive") != null);

        departmentRepository.add(department);
        response.sendRedirect(request.getContextPath() + "/admin/department/list");
    }

    private void updateDepartment(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Department department = new Department();
        department.setDepartmentID(Integer.parseInt(request.getParameter("departmentID")));
        department.setDepartmentName(request.getParameter("departmentName"));
        department.setDescription(request.getParameter("description"));
        department.setIsActive(request.getParameter("isActive") != null);

        departmentRepository.update(department);
        response.sendRedirect(request.getContextPath() + "/admin/department/list");
    }

    private void deleteDepartment(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        departmentRepository.delete(id);
        response.sendRedirect(request.getContextPath() + "/admin/department/list");
    }

    private void toggleStatus(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean status = request.getParameter("isActive") != null;
        departmentRepository.updateStatus(id, status);
        response.sendRedirect(request.getContextPath() + "/admin/department/list");
    }
}