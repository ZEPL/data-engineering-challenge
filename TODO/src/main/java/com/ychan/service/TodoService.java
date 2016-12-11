package com.ychan.service;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.inject.Inject;
import com.ychan.dao.Dao;
import com.ychan.dto.Todo;

@Path("/todos")
public class TodoService implements RestService{
  private Dao<Todo> dao;
  private TaskService taskService;
  
  @Inject
  public TodoService(Dao<Todo> dao, TaskService taskService) {
    this.dao = dao;
    this.taskService = taskService;
  }

  @GET
  @Path("{id}/tasks")
  public Object getTasks(@PathParam("id") String id) {
    return taskService.get();
  }
  
  @GET
  @Path("{id}")
  public Object get(@PathParam("id") String id) {
    return "get by id";
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Object get() {
    return "get all";
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Object post() {
    // TODO Auto-generated method stub
    return null;
  }

  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  public Object put() {
    // TODO Auto-generated method stub
    return null;
  }

  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  public Object delete() {
    // TODO Auto-generated method stub
    return null;
  }
}