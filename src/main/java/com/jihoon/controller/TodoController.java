package com.jihoon.controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.jihoon.model.Task;
import com.jihoon.model.Todo;
import com.jihoon.service.TodoService;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("/Todos")
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
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GET
	@Path("/{todo_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getTodo(@PathParam("todo_id") String todoId) {

		try {

			List<Task> taskList = todoService.getTasks(todoId);
			String jsonInString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(taskList);
			System.out.println(jsonInString);

			return Response.status(200).entity(jsonInString).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GET
	@Path("/{todo_id}/tasks/{task_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getTask(@PathParam("todo_id") String todoId, @PathParam("task_id") String taskId) {

		try {

			System.out.println("todo_id : " + todoId);
			System.out.println("task_id : " + taskId);

			Task task = todoService.getTask(taskId);
			String jsonInString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(task);

			return Response.status(200).entity(jsonInString).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GET
	@Path("/{todo_id}/tasks/done")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getTaskDone(@PathParam("todo_id") String todoId) {

		try {

			List<Task> taskList = todoService.getTasksDone(todoId);
			String jsonInString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(taskList);
			System.out.println(jsonInString);

			return Response.status(200).entity(jsonInString).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@GET
	@Path("/{todo_id}/tasks/not-done")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getTaskNotDone(@PathParam("todo_id") String todoId) {

		try {

			List<Task> taskList = todoService.getTasksNotDone(todoId);
			String jsonInString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(taskList);
			System.out.println(jsonInString);

			return Response.status(200).entity(jsonInString).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTodo(Todo todoJson) {

		String name = todoJson.getName();
		Todo newTodo = todoService.createTodo(name);

		return Response.status(201).entity(newTodo).build();
	}

	@POST
	@Path("/{todo_id}/tasks")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTasks(@PathParam("todo_id") String todoId, Task taskJson) {

		String name = taskJson.getName();
		String description = taskJson.getDescription();
		Task newTask = todoService.createTask(todoId, name, description);

		return Response.status(201).entity(newTask).build();
	}

	@PUT
	@Path("/{todo_id}/tasks/{task_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTask(@PathParam("todo_id") String todoId, @PathParam("task_id") String taskId, Task taskJson) {

		try {

			String name = taskJson.getName();
			String description = taskJson.getDescription();
			String status = taskJson.getStatus();

			System.out.println("todo_id : " + todoId);
			System.out.println("task_id : " + taskId);
			System.out.println("taskJson : " + taskJson);
			System.out.println("name : " + name);
			System.out.println("description : " + description);
			System.out.println("status : " + status);

			Task task = todoService.updateTask(taskId, name , description, status);
			String jsonInString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(task);

			return Response.status(200).entity(jsonInString).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@DELETE
	@Path("/{todo_id}")
	public Response deleteTodo(@PathParam("todo_id") String todoId) {

		try {
			Boolean result = todoService.deleteTodo(todoId);

			if(result)
				return Response.status(200).entity("{}").build();
			else
				return Response.status(500).entity("{}").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}

	@DELETE
	@Path("/{todo_id}/tasks/{task_id}")
	public Response deleteTask(@PathParam("todo_id") String todoId, @PathParam("task_id") String taskId) {

		try {
			Boolean result = todoService.deleteTask(taskId);

			if(result)
				return Response.status(200).entity("{}").build();
			else
				return Response.status(500).entity("{}").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
}