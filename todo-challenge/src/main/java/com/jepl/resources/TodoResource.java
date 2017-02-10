package com.jepl.resources;

import com.google.inject.*;
import com.google.inject.servlet.*;

import com.jepl.annotations.*;
import com.jepl.daos.*;
import com.jepl.enums.*;
import com.jepl.models.*;
import com.jepl.utils.*;

import java.util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/")
@RequestScoped
public class TodoResource {
    private final Dao dao;
    private final ResponseUtil responseUtil;

    @Inject
    public TodoResource(Dao dao, ResponseUtil responseUtil) {
        this.dao = dao;
        this.responseUtil = responseUtil;
    }

    @GET
    @Path("/todos")
    @Produces(MediaType.APPLICATION_JSON)
    @EventLogger
    public Response todos() {
        List<Todo> resultTodos = dao.getAllTodo();
        return responseUtil.ok(resultTodos);
    }


    @GET
    @Path("/todos/{todo_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @EventLogger
    public Response getTodoByTodoId(@PathParam("todo_id") String todoId) {
        Todo todo = dao.getTodoById(todoId);
        return responseUtil.ok(todo);
    }

    @GET
    @Path("/todos/{todo_id}/tasks/{task_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @EventLogger
    public Response getTodoByTodoIdAndTasksByTaskId(@PathParam("todo_id") String todoId,
                                                    @PathParam("task_id") String taskId) {
        Task task = dao.getTaskByTodoIdAndTaskId(todoId, taskId);
        return responseUtil.ok(task);
    }

    @GET
    @Path("/todos/{todo_id}/tasks/done")
    @Produces(MediaType.APPLICATION_JSON)
    @EventLogger
    public Response setDoneToTask(@PathParam("todo_id") String todoId) {
        dao.updateStatusToAllTaskInTodo(todoId, TaskStatus.DONE);
        List<Task> tasks = dao.getAllTaskByTodoId(todoId);
        return responseUtil.ok(tasks);
    }

    @GET
    @Path("/todos/{todo_id}/tasks/not-done")
    @Produces(MediaType.APPLICATION_JSON)
    @EventLogger
    public Response setNotDoneToTask(@PathParam("todo_id") String todoId) {
        dao.updateStatusToAllTaskInTodo(todoId, TaskStatus.NOT_DONE);
        List<Task> tasks = dao.getAllTaskByTodoId(todoId);
        return responseUtil.ok(tasks);
    }

    @POST
    @Path("/todos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @EventLogger
    public Response createTodo(Todo paramTodo) {
        Todo todo = dao.createTodo(paramTodo.getName());
        return responseUtil.ok(todo);
    }

    @POST
    @Path("/todos/{todo_id}/tasks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @EventLogger
    public Response createTask(@PathParam("todo_id") String todoId,
                               Task paramTask) {
        Task task = dao.createTask(todoId, paramTask.getName(), paramTask.getDescription());
        return responseUtil.ok(task);
    }

    @PUT
    @Path("/todos/{todo_id}/tasks/{task_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @EventLogger
    public Response updateTask(@PathParam("todo_id") String todoId,
                               @PathParam("task_id") String taskId,
                               Task paramTask) {
        paramTask.setId(taskId);
        dao.updateTask(todoId, paramTask);
        Task task = dao.getTaskByTodoIdAndTaskId(todoId, taskId);
        return responseUtil.ok(task);
    }

    @DELETE
    @Path("/todos/{todo_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @EventLogger
    public Response deleteTodo(@PathParam("todo_id") String todoId) {
        dao.deleteTodo(todoId);
        return responseUtil.ok(Collections.EMPTY_MAP);
    }

    @DELETE
    @Path("/todos/{todo_id}/tasks/{task_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @EventLogger
    public Response deleteTodo(@PathParam("todo_id") String todoId,
                               @PathParam("task_id") String taskId) {
        dao.deleteTask(todoId, taskId);
        return responseUtil.ok(Collections.EMPTY_MAP);
    }
}




