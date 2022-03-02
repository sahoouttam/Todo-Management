package com.example.TodoManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller("error")
public class ErrorController {

    public ModelAndView handleException(HttpServletRequest httpServletRequest, Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", exception.getLocalizedMessage());
        modelAndView.addObject("url", httpServletRequest.getRequestURL());

        modelAndView.setViewName("error");
        return modelAndView;
    }
}
