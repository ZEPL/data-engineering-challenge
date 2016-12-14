package com.jihoon.dao;

import com.jihoon.model.Task;
import com.jihoon.model.Todo;

import java.util.List;

public interface TodoDao {
    public List<Todo> getTodos();
    public Todo createTodo(String name);
}
