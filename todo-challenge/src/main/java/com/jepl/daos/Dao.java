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


////    private Concurrent<String, Todo> todos;
//    public static void main(String[] args) {
//
//        Config cfg = new Config();
//        HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
//        Map<Integer, String> mapCustomers = instance.getMap("customers");
//        mapCustomers.put(1, "Joe");
//        mapCustomers.put(2, "Ali");
//        mapCustomers.put(3, "Avi");
//
//        instance.getList("")
//
//        mapCustomers.values().forEach(System.out::println);
//
//
//    }

}
