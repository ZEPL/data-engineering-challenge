package com.jihoon.dao;

import com.jihoon.model.Task;

import java.util.List;

/**
 * define TaskDao interface
 */
public interface TaskDao {
    List<Task> getTasks(String todoId);
    Task createTask(String todoId, String name, String description);
    Task getTask(String taskId);
    List<Task> getTasksDone(String todoId);
    List<Task> getTasksNotDone(String todoId);
    Task updateTask(String taskId, String name, String description, String status);
    Boolean deleteTask(String taskId);
}
