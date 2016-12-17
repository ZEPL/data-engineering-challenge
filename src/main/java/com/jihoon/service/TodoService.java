package com.jihoon.service;

import javax.ws.rs.core.Response;

/**
 * TodoService interface
 */
public interface TodoService {
    Response getTodos();
    Response createTodo(String name);
    Response deleteTodo(String todoId);

    Response getTasks(String todoId);
    Response createTask(String todoId, String name, String description);
    Response getTask(String taskId);
    Response getTasksDone(String todoId);
    Response getTasksNotDone(String todoId);
    Response updateTask(String taskId, String name, String description, String status);
    Response deleteTask(String todoId);
}
