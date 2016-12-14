package com.jihoon.dao;

import com.jihoon.model.Task;
import com.jihoon.model.Todo;

import java.util.List;

public interface TaskDao {
    public List<Task> getTasks(String todoId);
    public Task createTask(String todoId, String name, String description);
    public Task getTask(String taskId);
    public List<Task> getTasksDone(String todoId);
    public List<Task> getTasksNotDone(String todoId);
}
