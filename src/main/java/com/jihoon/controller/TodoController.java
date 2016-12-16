package com.jihoon.controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.jihoon.model.Task;
import com.jihoon.model.Todo;
import com.jihoon.service.TodoService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/Todos")
@Singleton
public class TodoController {

    private TodoService todoService;

	@Inject
	public TodoController(TodoService todoService){
		this.todoService = todoService;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getTodos() {
		return todoService.getTodos();
	}

	@GET
	@Path("/{todo_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getTasks(@PathParam("todo_id") String todoId) {
		return todoService.getTasks(todoId);
	}

	@GET
	@Path("/{todo_id}/tasks/{task_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getTask(@PathParam("todo_id") String todoId, @PathParam("task_id") String taskId) {
		return todoService.getTask(taskId);
	}

	@GET
	@Path("/{todo_id}/tasks/done")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getTaskDone(@PathParam("todo_id") String todoId) {

		return todoService.getTasksDone(todoId);
	}

	@GET
	@Path("/{todo_id}/tasks/not-done")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getTaskNotDone(@PathParam("todo_id") String todoId) {
		return todoService.getTasksNotDone(todoId);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTodo(Todo todoJson) {
		return todoService.createTodo(todoJson.getName());
	}

	@POST
	@Path("/{todo_id}/tasks")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTasks(@PathParam("todo_id") String todoId, Task taskJson) {

		String name = taskJson.getName();
		String description = taskJson.getDescription();

		return todoService.createTask(todoId, name, description);
	}

	@PUT
	@Path("/{todo_id}/tasks/{task_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTask(@PathParam("todo_id") String todoId, @PathParam("task_id") String taskId, Task taskJson) {

		String name = taskJson.getName();
		String description = taskJson.getDescription();
		String status = taskJson.getStatus();

		return todoService.updateTask(taskId, name , description, status);
	}

	@DELETE
	@Path("/{todo_id}")
	public Response deleteTodo(@PathParam("todo_id") String todoId) {
		return todoService.deleteTodo(todoId);
	}

	@DELETE
	@Path("/{todo_id}/tasks/{task_id}")
	public Response deleteTask(@PathParam("todo_id") String todoId, @PathParam("task_id") String taskId) {
		return todoService.deleteTask(taskId);
	}
}