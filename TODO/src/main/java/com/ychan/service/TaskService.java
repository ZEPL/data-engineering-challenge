package com.ychan.service;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class TaskService implements RestService{

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Object get() {
    // TODO Auto-generated method stub
    return null;
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
