package com.ychan.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.inject.Inject;
import com.ychan.dao.Dao;
import com.ychan.dto.Todo;

@Path("/todos")
public class TodoService{
  private Dao<Todo> dao;
  
  @Inject
  public TodoService(Dao<Todo> dao) {
    this.dao = dao;
  }

  @GET
  public String get() {
    System.out.print("thisis get");
    return "get all";
  }
  
//  @GET
//  @Path("{id}")
//  public String get(@PathParam("id") String id) {
//    return "get some id";
//  }
  
}