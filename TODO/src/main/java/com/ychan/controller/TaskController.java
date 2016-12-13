package com.ychan.controller;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.ychan.DBManager.NotExistException;
import com.ychan.dto.Task;
import com.ychan.service.TaskService;
import com.ychan.service.TodoService;

@Path("/todos/{todoId}/tasks")
public class TaskController implements BaseController {
  private TodoService todoService;
  private TaskService taskService;
  private ObjectMapper mapper;

  @Inject
  public TaskController(TodoService todoService, TaskService taskService, ObjectMapper mapper) {
    this.todoService = todoService;
    this.taskService = taskService;
    this.mapper = mapper;
  }

  private boolean existTodo(String todoId) throws Exception {
    try {
      todoService.getById(todoId);
    } catch (NotExistException e) {
      return false;
    }
    return true;
  }

  @GET
  @Path("{taskId}")
  public Response getTasks(@PathParam("todoId") final String todoId, @PathParam("taskId") final String taskid) {
    String resJson = null;
    try {
      if (!existTodo(todoId))
        return BaseController.super.sendError(400, "No Todo");
      final Task todo = taskService.getById(taskid);
      resJson = mapper.writeValueAsString(todo);
    } catch (NotExistException e) {
      return BaseController.super.sendError(400, "No task");
    } catch (Exception e) {
      // JsonProcessingException
      e.printStackTrace();
      return BaseController.super.sendError();
    }
    return Response.status(200).entity(resJson).build();
  }

//  @GET
//  public Response get(@PathParam("todoId") final String todoId) {
//    String resJson = null;
//    try {
//      if (!existTodo(todoId))
//        return BaseController.super.sendError(400, "No Todo");
//      final Task[] tasks = taskService.getAll(todoId);
//
//      resJson = mapper.writeValueAsString(tasks);
//    } catch (Exception e) {
//      // JsonProcessingException
//      e.printStackTrace();
//      return BaseController.super.sendError();
//    }
//    return Response.status(200).entity(resJson).build();
//  }

  @POST
  @Path("/")
  public Response post(@PathParam("todoId") final String todoId, final String body) {
    String resJson = null;
    try {
      if (!existTodo(todoId))
        return BaseController.super.sendError(400, "No Todo");
      final Task value = mapper.readValue(body, Task.class);
      if (value.name == null || value.description == null) {
        return BaseController.super.sendError(400, "Malformed json");
      }
      value.setTodoId(todoId);
      value.setStatus(Task.NOT_DONE);

      taskService.add(value);

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
}
