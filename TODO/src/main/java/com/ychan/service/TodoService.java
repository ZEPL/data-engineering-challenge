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
import com.ychan.dao.Dao;
import com.ychan.dto.Todo;

@Path("/todos")
public class TodoService implements RestService{
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
    return Response.status(200).entity("todo-get").build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response post(String body) {
    Todo value = null;
    try {
      value = mapper.readValue(body, Todo.class);
    } catch (JsonParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (JsonMappingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    if (value.name == null) {
      return Response.status(400).entity("Malformed json").build();
    }
    
    // TODO: put DB
    
    String jsonString = null;
    try {
      jsonString = mapper.writeValueAsString(value);
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
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