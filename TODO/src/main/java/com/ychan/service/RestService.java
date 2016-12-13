package com.ychan.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface RestService {
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response get();

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response post(String body);

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response put(String body);

  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  public Response delete();

  default public Response sendError() {
    return sendError(500, "Internal Server Error");
  }

  default public Response sendError(String message) {
    return sendError(500, message);
  }

  default public Response sendError(int statusCode, String message) {
    return Response.status(500).entity(message).build();
  }
}
