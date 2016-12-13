package com.ychan.controller;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.ychan.DBManager.NotExistException;
import com.ychan.dto.Todo;
import com.ychan.service.TodoService;

@Path("/todos")
public class TodoController implements BaseController{
  private TaskController taskController;
  private TodoService todoService;
  private ObjectMapper mapper;
  
  @Inject
  public TodoController(TaskController taskController, TodoService todoService, ObjectMapper mapper) {
    this.taskController = taskController;
    this.todoService = todoService;
    this.mapper = mapper;
  }

  @GET
  @Path("{id}/tasks")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getTasks(@PathParam("id") final String id) {
    return null; // taskService.get();
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response get(@PathParam("id") final String id) {
    Todo todo = null;
    try {
      todo = todoService.getById(id);
    } catch (NotExistException e1) {
      return BaseController.super.sendError(400, "No data");
    } catch (Exception e1) {
      e1.printStackTrace();
      return BaseController.super.sendError();
    }

    String resJson = null;
    try {
      resJson = mapper.writeValueAsString(todo);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return BaseController.super.sendError();
    }
    return Response.status(200).entity(resJson).build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response get() {
    Todo[] todos = null;
    todos = todoService.getAll();
    
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
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response post(String body) {
    Todo value = null;
    try {
      value = mapper.readValue(body, Todo.class);
    } catch (Exception e) {
      e.printStackTrace();
      return BaseController.super.sendError();
    }
    if (value.name == null) {
      return BaseController.super.sendError(400, "Malformed json");
    }
    
    try {
      todoService.add(value);
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
//
//  @PUT
//  @Consumes(MediaType.APPLICATION_JSON)
//  @Produces(MediaType.APPLICATION_JSON)
//  public Response put(String body) {
//    // TODO Auto-generated method stub
//    return null;
//  }
//
//  @DELETE
//  @Produces(MediaType.APPLICATION_JSON)
//  public Response delete() {
//    // TODO Auto-generated method stub
//    return null;
//  }
}
