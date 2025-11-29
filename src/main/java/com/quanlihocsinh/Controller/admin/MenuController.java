package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.dao.MenuDAO;
import com.quanlihocsinh.model.menu;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "MenuController", urlPatterns = { "/admin/menu" })
public class MenuController extends HttpServlet {
    private MenuDAO dao = new MenuDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        if (action == null)
            action = "list";

        switch (action) {
            case "add":
                showAddForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteMenu(request, response);
                break;
            default:
                listMenu(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        if (action == null)
            action = "list";

        switch (action) {
            case "add":
                menu mAdd = getMenuFromRequest(request);
                dao.addMenu(mAdd);
                response.sendRedirect(request.getContextPath() + "/admin/menu");
                break;
            case "edit":
                menu mEdit = getMenuFromRequest(request);
                mEdit.setMenuID(Integer.parseInt(request.getParameter("menuID")));
                dao.updateMenu(mEdit);
                response.sendRedirect(request.getContextPath() + "/admin/menu");
                break;
            case "toggleStatus":
                int id = Integer.parseInt(request.getParameter("id"));
                boolean status = request.getParameter("isActive") != null;
                dao.toggleStatus(id, status);
                response.sendRedirect(request.getContextPath() + "/admin/menu");
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/admin/menu");
        }
    }

    private void listMenu(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<menu> menus = dao.getAllMenus();
        request.setAttribute("menus", menus);
        request.getRequestDispatcher("/WEB-INF/views/admin/menu/list.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/admin/menu/add.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        menu m = dao.getMenuById(id);
        request.setAttribute("menu", m);
        request.getRequestDispatcher("/WEB-INF/views/admin/menu/edit.jsp").forward(request, response);
    }

    private void deleteMenu(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        dao.deleteMenu(id);
        response.sendRedirect(request.getContextPath() + "/admin/menu");
    }

    private menu getMenuFromRequest(HttpServletRequest request) {
        menu m = new menu();
        m.setMenuName(request.getParameter("menuName"));
        m.setControllerName(request.getParameter("controllerName"));
        m.setActionName(request.getParameter("actionName"));
        m.setLevels(parseIntSafe(request.getParameter("levels")));
        m.setParentID(parseIntSafe(request.getParameter("parentID")));
        m.setMenuOrder(parseIntSafe(request.getParameter("menuOrder")));
        m.setPosition(parseIntSafe(request.getParameter("position")));
        m.setIcon(request.getParameter("icon"));
        m.setIDName(request.getParameter("idName"));
        m.setItemTarget(request.getParameter("itemTarget"));
        m.setIsActive(request.getParameter("isActive") != null);
        return m;
    }

    private Integer parseIntSafe(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return null;
        }
    }
}
