package com.jihoon.service;

import com.google.inject.Inject;
import com.jihoon.dao.*;
import com.jihoon.model.*;
import java.util.List;

public class TodoServiceImpl implements TodoService {

    private TodoDao todoDao;
    private TaskDao taskDao;

    @Inject
    public TodoServiceImpl(TodoDao todoDao, TaskDao taskDao){
        this.todoDao = todoDao;
        this.taskDao = taskDao;
    }

    public List<Todo> getTodos(){
        return todoDao.getTodos();
    }

    public Todo createTodo(String name){
        return todoDao.createTodo(name);
    }

    public List<Task> getTasks(String todoId){
        return taskDao.getTasks(todoId);
    }

    public Task createTask(String todoId, String name, String description){
        return taskDao.createTask(todoId, name, description);
    }

    public Task getTask(String taskId){
        return taskDao.getTask(taskId);
    }

    public List<Task> getTasksDone(String taskId){
        return taskDao.getTasksDone(taskId);
    }

    public List<Task> getTasksNotDone(String taskId){
        return taskDao.getTasksNotDone(taskId);
    }
}
