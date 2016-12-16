package com.jepl.daos;

import com.jepl.enums.*;
import com.jepl.models.*;
import com.sun.jersey.api.*;

import java.util.*;
import java.util.stream.*;

public class SimpleDao implements Dao {
    public Map<String, Todo> todoMap = new HashMap<>();

    @Override
    public List<Todo> getAllTodo() {
        return todoMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public Todo getTodoById(String id) {
        Todo todo = todoMap.get(id);
        checkNull(todo);
        return todo;
    }

    @Override
    public List<Task> getAllTaskByTodoId(String todoId) {
        Todo todo = getTodoById(todoId);
        return toList(todo.getTasks());
    }


    @Override
    public Task getTaskByTodoIdAndTaskId(String todoId, String taskId) {
        Task task = getTodoById(todoId).getTasks().get(taskId);
        checkNull(task);
        return task;
    }

    @Override
    public Todo createTodo(String name) {
        Todo todo = new Todo(name);
        todoMap.put(todo.getId(), todo);
        return todo;
    }

    @Override
    public Task createTask(String todoId, String name, String description) {
        Todo todo = getTodoById(todoId);
        Task task = new Task(name, description);
        todo.getTasks().put(task.getId(), task);
        return task;
    }

    @Override
    public void updateStatusToAllTaskInTodo(String todoId, TaskStatus status) {
        Todo todo = getTodoById(todoId);
        for (Task task : todo.getTasks().values()) {
            task.setStatus(status);
        }
    }

    @Override
    public void updateTask(String todoId, Task paramTask) {
        Todo todo = getTodoById(todoId);
        Task task = todo.getTasks().get(paramTask.getId());
        task.update(paramTask);
    }

    @Override
    public void deleteTodo(String todoId) {
        Todo todo = getTodoById(todoId);
        todoMap.remove(todo.getId());
    }

    @Override
    public void deleteTask(String todoId, String taskId) {
        getTaskByTodoIdAndTaskId(todoId, taskId);
        Todo todo = getTodoById(todoId);
        todo.getTasks().remove(taskId);
    }
}
