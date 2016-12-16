package com.jihoon.service;

import com.google.inject.Inject;
import com.jihoon.dao.*;
import com.jihoon.model.*;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.core.Response;
import java.util.List;

public class TodoServiceImpl implements TodoService {

    private ObjectMapper objectMapper;
    private TodoDao todoDao;
    private TaskDao taskDao;

    @Inject
    public TodoServiceImpl(ObjectMapper objectMapper, TodoDao todoDao, TaskDao taskDao){
        this.objectMapper = objectMapper;
        this.todoDao = todoDao;
        this.taskDao = taskDao;
    }

    public Response getTodos() {

        try {

            List<Todo> todoList = todoDao.getTodos();
            String jsonInString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(todoList);

            return Response.status(200).entity(jsonInString).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }

    public Response createTodo(String name) {
        Todo newTodo = todoDao.createTodo(name);
        return Response.status(201).entity(newTodo).build();
    }

    public Response deleteTodo(String todoId) {
        try {
            Boolean result = todoDao.deleteTodo(todoId);

            if(result)
                return Response.status(200).entity("{}").build();
            else
                return Response.status(500).entity("{}").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }


    public Response getTasks(String todoId){

        try {

            List<Task> taskList = taskDao.getTasks(todoId);
            String jsonInString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(taskList);

            return Response.status(200).entity(jsonInString).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }

    public Response createTask(String todoId, String name, String description){

        Task newTask = taskDao.createTask(todoId, name, description);
        return Response.status(201).entity(newTask).build();
    }

    public Response deleteTask(String taskId) {
        try {
            Boolean result = taskDao.deleteTask(taskId);

            if (result)
                return Response.status(200).entity("{}").build();
            else
                return Response.status(500).entity("{}").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }

    public Response getTask(String taskId){

        try {

            Task task = taskDao.getTask(taskId);
            String jsonInString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(task);

            return Response.status(200).entity(jsonInString).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }

    public Response getTasksDone(String todoId){
        try {

            List<Task> taskList = taskDao.getTasksDone(todoId);
            String jsonInString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(taskList);

            return Response.status(200).entity(jsonInString).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }

    public Response getTasksNotDone(String todoId){

        try {

            List<Task> taskList = taskDao.getTasksNotDone(todoId);
            String jsonInString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(taskList);

            return Response.status(200).entity(jsonInString).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }

    public Response updateTask(String taskId, String name, String description, String status){

        try {

            Task task = taskDao.updateTask(taskId, name , description, status);
            String jsonInString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(task);

            return Response.status(200).entity(jsonInString).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }
}
