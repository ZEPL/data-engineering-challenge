package com.jihoon.controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.jihoon.model.Todo;
import com.jihoon.service.TodoService;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("/todos")
@Singleton
public class TodoController {

	private ObjectMapper objectMapper;
    private TodoService todoService;

	@Inject
	public TodoController(ObjectMapper objectMapper, TodoService todoService){
		this.objectMapper = objectMapper;
		this.todoService = todoService;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getTodos() {

		try {

            List<Todo> todoList = todoService.getTodos();
			String jsonInString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(todoList);
			System.out.println(jsonInString);

			return Response.status(200).entity(jsonInString).build();
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GET
	@Path("/{todo_id}")
	public Response getTodo(@PathParam("todo_id") String todoId) {

		String output = "getTodo" + todoId;
		return Response.status(200).entity(output).build();
	}

	@GET
	@Path("/{todo_id}/tasks/{task_id}")
	public Response getTask(@PathParam("todo_id") String todoId, @PathParam("task_id") String taskId) {

		String output = "getTask"+ todoId + taskId;
		return Response.status(200).entity(output).build();
	}

	@GET
	@Path("/{todo_id}/tasks/done")
	public Response getTaskDone(@PathParam("todo_id") String todoId) {

		String output = "getTaskDone"+ todoId;
		return Response.status(200).entity(output).build();
	}

	@GET
	@Path("/{todo_id}/tasks/not-done")
	public Response getTaskNotDone(@PathParam("todo_id") String todoId) {

		String output = "getTaskNotDone";
		return Response.status(200).entity(output).build();
	}

	@POST
	public Response createTodos() {

		String output = "createTodos";
		return Response.status(200).entity(output).build();
	}

	@POST
	@Path("/{todo_id}/tasks")
	public Response createTasks(@PathParam("todo_id") String todoId) {

		String output = "createTasks";
		return Response.status(200).entity(output).build();
	}

	@POST
	@Path("/{todo_id}/tasks/{task_id}")
	public Response createTask(@PathParam("todo_id") String todoId) {

		String output = "createTask";
		return Response.status(200).entity(output).build();
	}


	@DELETE
	@Path("/{todo_id}")
	public Response deleteTodo(@PathParam("todo_id") String todoId) {

		String output = "deleteTodo";
		return Response.status(200).entity(output).build();
	}

	@DELETE
	@Path("/{todo_id}/tasks/{task_id}")
	public Response deleteTask(@PathParam("todo_id") String todoId, @PathParam("task_id") String taskId) {

		String output = "deleteTask";
		return Response.status(200).entity(output).build();
	}

}