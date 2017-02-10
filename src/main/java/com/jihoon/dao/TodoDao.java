package com.jihoon.dao;

import com.jihoon.model.Todo;
import java.util.List;

/**
 * define TodoDao interface
 */
public interface TodoDao {
    List<Todo> getTodos();
    Todo createTodo(String name);
    Boolean deleteTodo(String todoId);
}