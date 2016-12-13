package com.ychan.controller;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.ychan.DBManager.NotExistException;
import com.ychan.dto.Task;
import com.ychan.service.TaskService;
import com.ychan.service.TodoService;

@Path("/todos/{todoId}/tasks")
public class TaskController implements BaseController {
  private TodoService todoService;
  private TaskService taskService;
  private ObjectMapper mapper;

  @Inject
  public TaskController(TodoService todoService, TaskService taskService, ObjectMapper mapper) {
    this.todoService = todoService;
    this.taskService = taskService;
    this.mapper = mapper;
  }

  private boolean existTodo(String todoId) throws Exception {
    try {
      todoService.getById(todoId);
    } catch (NotExistException e) {
      return false;
    }
    return true;
  }

  @GET
  @Path("{taskId}")
  public Response getTasks(final String todoId, @PathParam("taskId") final String taskid) {
    return Response.status(200).entity("hihi").build();
  }

  @GET
  public Response getTasks() {
    return Response.status(200).entity("hihi").build();
  }

  @POST
  @Path("/")
  public Response post(@PathParam("todoId") final String todoId, String body) {
    try {
      if (!existTodo(todoId)) {
        return BaseController.super.sendError(400, "No Todo");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    Task value = null;
    try {
      value = mapper.readValue(body, Task.class);
      value.setTodoId(todoId);
      value.setStatus(Task.NOT_DONE);
    } catch (JsonParseException e) {
      e.printStackTrace();
      return BaseController.super.sendError();
    } catch (JsonMappingException e) {
      e.printStackTrace();
      return BaseController.super.sendError();
    } catch (IOException e) {
      e.printStackTrace();
      return BaseController.super.sendError();
    }
    if (value.name == null || value.description == null) {
      return BaseController.super.sendError(400, "Malformed json");
    }
    try {
      taskService.add(value);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return BaseController.super.sendError();
    }
    String jsonString = null;
    try {
      jsonString = mapper.writeValueAsString(value);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return BaseController.super.sendError();
    }

    return Response.status(200).entity(jsonString).build();
  }
}
