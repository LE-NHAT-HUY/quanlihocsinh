package com.quanlihocsinh.Controller.admin;

import com.quanlihocsinh.dao.PersonDAO;
import com.quanlihocsinh.dao.UserDAO;
import com.quanlihocsinh.model.Person;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/createUser")
public class CreateUserContrller extends HttpServlet {

    private PersonDAO personDAO = new PersonDAO();
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        try {
            List<Person> persons = personDAO.getUnlinkedPersons(type);
            req.setAttribute("persons", persons);
            req.getRequestDispatcher("/WEB-INF/views/admin/createUser.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username").trim();
        String password = req.getParameter("password");
        int roleId = Integer.parseInt(req.getParameter("roleId"));

        // Cho phép personId NULL nếu là admin
        String personRaw = req.getParameter("personId");
        Integer personId = null;

        if (personRaw != null && !personRaw.trim().isEmpty()) {
            personId = Integer.parseInt(personRaw);
        }

        try {
            userDAO.createUserLinkedToPerson(username, password, roleId, personId);
            resp.sendRedirect(req.getContextPath() + "/admin/users?msg=created");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/admin/createUser.jsp").forward(req, resp);
        }
    }

}
