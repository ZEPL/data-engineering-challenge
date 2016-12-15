package com.jihoon.service;

import com.jihoon.model.Task;
import com.jihoon.model.Todo;

import java.util.List;

public interface TodoService {
    List<Todo> getTodos();
    Todo createTodo(String name);
    Boolean deleteTodo(String todoId);

    List<Task> getTasks(String todoId);
    Task createTask(String todoId, String name, String description);
    Boolean deleteTask(String todoId);

    Task getTask(String taskId);
    List<Task> getTasksDone(String todoId);
    List<Task> getTasksNotDone(String todoId);
    Task updateTask(String taskId, String name, String description, String status);
}
