package com.quanlihocsinh.Controller.advice;

import com.quanlihocsinh.dao.AdminMenuDAO;
import com.quanlihocsinh.model.AdminMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class AdminMenuAvice {

    @Autowired
    private AdminMenuDAO adminMenuDAO;

    @ModelAttribute("AdminMenu")
    public List<AdminMenu> populateMenu() {
        return adminMenuDAO.getMenus();
    }
}
