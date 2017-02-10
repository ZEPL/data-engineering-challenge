package com.ychan.controller;

import java.util.Arrays;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.ychan.DBManager.NotExistException;
import com.ychan.dto.Task;
import com.ychan.dto.Todo;
import com.ychan.service.TaskService;
import com.ychan.service.TodoService;

@Path("/todos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TodoController implements BaseController {
  private TodoService todoService;
  private TaskService taskService;
  private ObjectMapper mapper;

  @Inject
  public TodoController(TodoService todoService, TaskService taskService, ObjectMapper mapper) {
    this.todoService = todoService;
    this.taskService = taskService;
    this.mapper = mapper;
  }

//  @GET
//  @Path("/{id}")
//  public Response get(@PathParam("id") final String id) {
//    String resJson = null;
//    try {
//      final Todo todo = todoService.getById(id);
//      resJson = mapper.writeValueAsString(todo);
//    } catch (NotExistException e) {
//      return BaseController.super.sendError(400, "No data");
//    } catch (Exception e) {
//      // JsonProcessingException
//      e.printStackTrace();
//      return BaseController.super.sendError();
//    }
//    return Response.status(200).entity(resJson).build();
//  }
  @GET
  @Path("/{id}")
  public Response get(@PathParam("id") final String id) {
    String resJson = null;
    try {
      todoService.getById(id); // check todo
      final Task[] tasks = taskService.getAll(id);
      resJson = mapper.writeValueAsString(tasks);
    } catch (NotExistException e) {
      return BaseController.super.sendError(400, "No Todo");
    } catch (Exception e) {
      // JsonProcessingException
      e.printStackTrace();
      return BaseController.super.sendError();
    }
    return Response.status(200).entity(resJson).build();
  }

  @GET
  public Response get() {
    final Todo[] todos = todoService.getAll();
    String resJson = null;
    try {
      resJson = mapper.writeValueAsString(todos);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return BaseController.super.sendError();
    }
    return Response.status(200).entity(resJson).build();
  }

  @POST
  public Response post(final String body) {
    String resJson = null;
    try {
      final Todo value = mapper.readValue(body, Todo.class);
      if (value.name == null) {
        return BaseController.super.sendError(400, "Malformed json");
      }
      todoService.add(value);
      resJson = mapper.writeValueAsString(value);
    } catch (Exception e) {
      // JsonParseException
      // JsonMappingException
      // JsonProcessingException
      // IOException
      e.printStackTrace();
      return BaseController.super.sendError();
    }
    return Response.status(200).entity(resJson).build();
  }

  @DELETE
  @Path("{id}")
  public Response delete(@PathParam("id") final String id) {
    todoService.delete(id);
    Task[] tasks = taskService.getAll(id);
    Arrays.stream(tasks)
        .map(task->task.getId())
        .forEach(taskService::delete);
    return Response.status(200).entity("{}").build();
  }
}
