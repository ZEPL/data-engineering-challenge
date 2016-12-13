package com.ychan.service;

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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.ychan.DBManager;
import com.ychan.dao.Dao;
import com.ychan.dto.Todo;

@Path("/todos")
public class TodoService implements RestService {
  private Dao<Todo> dao;
  private TaskService taskService;
  private ObjectMapper mapper;

  @Inject
  public TodoService(Dao<Todo> dao, TaskService taskService, ObjectMapper mapper) {
    this.dao = dao;
    this.taskService = taskService;
    this.mapper = mapper;
  }

  @GET
  @Path("{id}/tasks")
  @Produces(MediaType.APPLICATION_JSON)
  public Object getTasks(@PathParam("id") String id) {
    return taskService.get();
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Object get(@PathParam("id") String id) {
    return "get by id";
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response get() {
    Object[] todos = (Object[]) DBManager.getInstance().getPattern(Todo.class.getName(), Todo.class);
    String resJson = null;
    try {
      resJson = mapper.writeValueAsString(todos);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return RestService.super.sendError("Database Error");
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
      return RestService.super.sendError();
    }
    if (value.name == null) {
      return RestService.super.sendError(400, "Malformed json");
    }

    String jsonString = null;
    try {
      jsonString = DBManager.getInstance().put(value.getId(), value);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return RestService.super.sendError("Database Error");
    }
    return Response.status(200).entity(jsonString).build();
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response put(String body) {
    // TODO Auto-generated method stub
    return null;
  }

  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  public Response delete() {
    // TODO Auto-generated method stub
    return null;
  }
}