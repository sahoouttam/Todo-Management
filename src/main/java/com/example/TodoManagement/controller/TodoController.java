package com.example.TodoManagement.controller;

import com.example.TodoManagement.model.Todo;
import com.example.TodoManagement.service.TodoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;

@Controller
public class TodoController {

    @Autowired
    private TodoServiceImpl todoService;

    private String getLoggedInUsername(ModelMap modelMap) {
        Object object = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (object instanceof UserDetails) {
            return ((UserDetails) object).getUsername();
        }
        return object.toString();
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        webDataBinder.registerCustomEditor(Data.class, new CustomDateEditor(simpleDateFormat, false));
    }

    @RequestMapping(value = "/list-todos", method = RequestMethod.GET)
    public String getTodosByName(ModelMap modelMap) {
        String name = getLoggedInUsername(modelMap);
        modelMap.put("todos", todoService.getTodoByName(name));
        return "Todo-List";
    }

    @RequestMapping(value = "/add-todo", method = RequestMethod.POST)
    public String showAddTodoPage(ModelMap modelMap) {
        modelMap.addAttribute("todo", new Todo());
        return "todo";
    }

    @RequestMapping(value = "/delete-todo", method = RequestMethod.GET)
    public String deleteTodoById(@RequestParam long id) {
        todoService.deleteTodo(id);
        return "redirect:/Todo-List";
    }

    @RequestMapping(value = "/update-todo", method = RequestMethod.GET)
    public String showUpdateTodoPage(@RequestParam long id, ModelMap modelMap) {
        Todo todo = todoService.getTodoById(id).get();
        modelMap.put("todo", todo);
        return "todo";
    }

    @RequestMapping(value = "/update-todo", method = RequestMethod.POST)
    public String updateTodo(ModelMap modelMap, Todo todo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "todo";
        }

        todo.setName(getLoggedInUsername(modelMap));
        todoService.updateTodo(todo);
        return "redirect:/list-todos";
    }

    @RequestMapping(value = "/add-todo", method = RequestMethod.POST)
    public String addTodo(ModelMap modelMap, Todo todo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "todo";
        }

        todo.setName(getLoggedInUsername(modelMap));
        todoService.saveTodo(todo);
        return "redirect:/list-todos";
    }
}
