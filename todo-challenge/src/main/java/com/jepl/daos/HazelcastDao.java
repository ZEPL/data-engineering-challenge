package com.jepl.daos;

import com.google.inject.*;
import com.google.inject.name.*;

import com.hazelcast.config.*;
import com.hazelcast.core.*;
import com.jepl.enums.*;
import com.jepl.models.*;

import java.util.*;
import java.util.stream.*;

public class HazelcastDao implements Dao {
    private HazelcastInstance instance;
    private Map<String, Todo> todoMap;

    @Inject
    public HazelcastDao( @Named("backupTodos")  List<Todo> todos) {
        Config cfg = new Config();
        instance = Hazelcast.newHazelcastInstance(cfg);
        Map<String, Todo> todoMap = instance.getMap("todoMap");
        if(todos.size() > 0 ) {
            for (Todo todo : todos) {
                todoMap.put(todo.getId(), todo);
            }
        }

        this.todoMap = todoMap;
    }

    public void destory() {
        instance.shutdown();
    }


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
        todoMap.put(todo.getId(), todo);
        return task;
    }

    @Override
    public void updateStatusToAllTaskInTodo(String todoId, TaskStatus status) {
        Todo todo = getTodoById(todoId);
        for (Task task : todo.getTasks().values()) {
            task.setStatus(status);
        }
        todoMap.put(todo.getId(), todo);
    }

    @Override
    public void updateTask(String todoId, Task paramTask) {
        Todo todo = getTodoById(todoId);
        Task task = todo.getTasks().get(paramTask.getId());
        task.update(paramTask);
        todoMap.put(todo.getId(), todo);
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
        todoMap.put(todo.getId(), todo);
    }

    public void deleteAllTask() {
        todoMap.clear();
    }
}
