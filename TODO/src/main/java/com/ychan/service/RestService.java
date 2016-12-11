package com.ychan.service;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public interface RestService {
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Object get();

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Object post();

  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  public Object put();

  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  public Object delete();
}
