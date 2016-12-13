package com.ychan.controller;

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
import com.ychan.dto.Todo;
import com.ychan.service.TodoService;

@Path("/todos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TodoController implements BaseController {
  private TodoService todoService;
  private ObjectMapper mapper;

  @Inject
  public TodoController(TodoService todoService, ObjectMapper mapper) {
    this.todoService = todoService;
    this.mapper = mapper;
  }

  @GET
  @Path("/{id}")
  public Response get(@PathParam("id") final String id) {
    String resJson = null;
    try {
      final Todo todo = todoService.getById(id);
      resJson = mapper.writeValueAsString(todo);
    } catch (NotExistException e) {
      return BaseController.super.sendError(400, "No data");
    } catch (Exception e) {
      // JsonProcessingException
      e.printStackTrace();
      return BaseController.super.sendError();
    }
    return Response.status(200).entity(resJson).build();
  }

  @GET
  @Path("/")
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
  @Path("/")
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
  @Produces(MediaType.APPLICATION_JSON)
  public Response delete() {
    // TODO Auto-generated method stub
    return null;
  }
}
