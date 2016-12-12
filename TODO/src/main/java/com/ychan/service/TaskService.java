package com.ychan.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class TaskService implements RestService{

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response get() {
    return Response.status(200).entity("task-get").build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response post(String body) {
    // TODO Auto-generated method stub
    return null;
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
