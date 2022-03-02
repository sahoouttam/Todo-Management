package com.example.TodoManagement.service;

import com.example.TodoManagement.model.Todo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TodoService {

    List<Todo> getTodoByName(String name);

    Optional<Todo> getTodoById(long id);

    void updateTodo(Todo todo);

    void addTodo(String name, String description, Date date, boolean isDone);

    void deleteTodo(long id);

    void saveTodo(Todo todo);
}
