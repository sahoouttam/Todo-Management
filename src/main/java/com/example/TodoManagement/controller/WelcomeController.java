package com.example.TodoManagement.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WelcomeController {

    private String getLoggedInUsername() {
        Object object = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (object instanceof UserDetails) {
            return ((UserDetails) object).getUsername();
        }
        return object.toString();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showWelcomePage(ModelMap modelMap) {
        modelMap.put("name", getLoggedInUsername());
        return "Welcome!";
    }
}
