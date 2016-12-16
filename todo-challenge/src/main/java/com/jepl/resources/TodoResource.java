package com.jepl.resources;

import com.google.inject.servlet.*;

import com.fasterxml.jackson.core.type.*;
import com.jepl.annotations.*;
import com.jepl.enums.*;
import com.jepl.models.*;
import com.jepl.utils.*;

import org.slf4j.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;


import static org.eclipse.jetty.http.HttpStatus.NOT_FOUND_404;

@Path("/")
@RequestScoped
public class TodoResource {
    static final Logger logger = LoggerFactory.getLogger(TodoResource.class);
    public static LinkedHashMap<String, Todo> todos;

    static {
        try {
            if(new File("/tmp/todos.json").exists()) {
                String jsonString = FileUtil.readString("/tmp/todos.json");
                todos = JsonUtil.readValue(jsonString, new TypeReference<LinkedHashMap<String, Todo>>() {});
            } else {
                todos = new LinkedHashMap<>();
            }
        } catch (FileNotFoundException e) {
            logger.error("file not found exeption {}", e);
        } catch (IOException e) {
            logger.error("io exeption {}", e);
        }
    }

    @GET
    @Path("/todos")
    @Produces(MediaType.APPLICATION_JSON)
    @EventLogger
    public Response todos() {
        List<Todo> resultTodos = todos.values().stream().map(x -> x).collect(Collectors.toList());
        return ResponseUtil.okBuild(resultTodos);
    }


    @GET
    @Path("/todos/{todo_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @EventLogger
    public Response getTodoByTodoId(@PathParam("todo_id") String todo_id) {
        Todo todo = todos.get(todo_id);

        ResponseUtil.checkNull(todo);

        return ResponseUtil.okBuild(todo);
    }

    @GET
    @Path("/todos/{todo_id}/tasks/{task_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @EventLogger
    public Response getTodoByTodoIdAndTasksByTaskId(@PathParam("todo_id") String todo_id,
                                                    @PathParam("task_id") String task_id) {
        Todo todo = todos.get(todo_id);

        ResponseUtil.checkNull(todo);

        Task task = todo.getTasks().get(task_id);
        if (Objects.isNull(task)) {
            logger.error("Invalid task id");
            return ResponseUtil.errorBuild(NOT_FOUND_404, new ErrorModel("Invalid task id"));
        }

        return ResponseUtil.okBuild(task);
    }

    @GET
    @Path("/todos/{todo_id}/tasks/done")
    @Produces(MediaType.APPLICATION_JSON)
    @EventLogger
    public Response setDoneToTask(@PathParam("todo_id") String todoId) {
        Todo todo = todos.get(todoId);
        if (Objects.isNull(todo)) {
            logger.error("Invalid todo id");
            return ResponseUtil.errorBuild(NOT_FOUND_404, new ErrorModel("Invalid todo id"));
        }
        List<Task> tasks = getAllTaskUpdatedStatus(todo, TaskStatus.DONE);

        return ResponseUtil.okBuild(tasks);
    }

    private List<Task> getAllTaskUpdatedStatus(Todo todo, TaskStatus status) {
        return todo.getTasks().values().stream().map(task -> {
            task.setStatus(status);
            return task;
        }).collect(Collectors.toList());
    }

    @GET
    @Path("/todos/{todo_id}/tasks/not-done")
    @Produces(MediaType.APPLICATION_JSON)
    @EventLogger
    public Response setNotDoneToTask(@PathParam("todo_id") String todoId) {
        Todo todo = todos.get(todoId);
        ResponseUtil.checkNull(todo);

        List<Task> tasks = getAllTaskUpdatedStatus(todo, TaskStatus.NOT_DONE);
        return ResponseUtil.okBuild(tasks);
    }

    @POST
    @Path("/todos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @EventLogger
    public Response createTodo(Todo paramTodo) {
        ResponseUtil.checkNull(paramTodo);

        Todo todo = new Todo(paramTodo.getName());
        todo.setName(paramTodo.getName());
        todos.put(todo.getId(), todo);
        return ResponseUtil.okBuild(todo);
    }

    @POST
    @Path("/todos/{todo_id}/tasks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @EventLogger
    public Response createTask(@PathParam("todo_id") String todoId,
                               Task paramTask) {
        ResponseUtil.checkNull(paramTask);

        Todo todo = todos.get(todoId);
        ResponseUtil.checkNull(todo);

        Map<String, Task> tasks = todo.getTasks();
        Task task = new Task(paramTask.getName(), paramTask.getDescription());
        tasks.put(task.getId(), task);
        todo.getTasks().put(task.getId(), task);
        todos.put(task.getId(), todo);
        return ResponseUtil.okBuild(task);
    }

    @PUT
    @Path("/todos/{todo_id}/tasks/{task_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @EventLogger
    public Response updateTask(@PathParam("todo_id") String todoId,
                               @PathParam("task_id") String taskId,
                               Task paramTask) {
        ResponseUtil.checkNull(paramTask);

        Todo todo = todos.get(todoId);
        ResponseUtil.checkNull(todo);

        Map<String, Task> tasks = todo.getTasks();
        Task task = tasks.get(taskId);
        task.setName(paramTask.getName());
        task.setDescription(paramTask.getDescription());
        task.setStatus(paramTask.getStatus());
        return ResponseUtil.okBuild(task);
    }

    @DELETE
    @Path("/todos/{todo_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @EventLogger
    public Response deleteTodo(@PathParam("todo_id") String todoId) {
        if (!todos.containsKey(todoId)) {
            throw new NotFoundException();
        }
        todos.remove(todoId);
        return ResponseUtil.okBuild(Collections.EMPTY_MAP);
    }

    @DELETE
    @Path("/todos/{todo_id}/tasks/{task_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @EventLogger
    public Response deleteTodo(@PathParam("todo_id") String todoId,
                               @PathParam("task_id") String taskId) {
        if (!todos.containsKey(todoId)) {
            throw new NotFoundException();
        }
        Map<String, Task> tasks = todos.get(todoId).getTasks();

        if (!tasks.containsKey(taskId)) {
            return ResponseUtil.errorBuild(NOT_FOUND_404, new ErrorModel("Invalid task id"));
        }
        tasks.remove(taskId);
        return ResponseUtil.okBuild(Collections.EMPTY_MAP);
    }
}




