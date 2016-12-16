package com.jepl.daos;

import com.hazelcast.config.*;
import com.hazelcast.core.*;
import com.jepl.enums.*;
import com.jepl.models.*;

import java.util.*;
import java.util.stream.*;

import javax.ws.rs.*;

public interface Dao {

    List<Todo> getAllTodo();

    Todo getTodoById(String id);

    List<Task> getAllTaskByTodoId(String todoId);

    Task getTaskByTodoIdAndTaskId(String todoId, String taskId);

    Todo createTodo(String name);


    default void checkNull(Object obj) {
        if(Objects.isNull(obj)) {
            throw new NotFoundException();
        }
    }

    default <T> List<T> toList(Map<String, T> map) {
        return map.values().stream().collect(Collectors.toList());
    }

    Task createTask(String todoId, String name, String description);

    void updateStatusToAllTaskInTodo(String todoId, TaskStatus done);

    void updateTask(String todoId, Task task);

    void deleteTodo(String id);

    void deleteTask(String id, String id1);

    void init(Map<String, Todo> backupData);

}
