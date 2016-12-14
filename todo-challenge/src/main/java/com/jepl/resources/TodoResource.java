package com.jepl.resources;

import com.jepl.enums.*;
import com.jepl.models.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

import javax.ws.rs.*;
import javax.ws.rs.container.*;
import javax.ws.rs.core.*;

import jersey.repackaged.com.google.common.collect.*;

@Path("/")
public class TodoResource {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static final LinkedHashMap<String, Todo> todos = new LinkedHashMap<>();

    @GET
    @Path("/todos")
    @Produces(MediaType.APPLICATION_JSON)
    public void todo(@Suspended final AsyncResponse asyncResponse) {
        executorService.execute(() -> asyncResponse.resume(todos.values().stream().map(x->x).collect(Collectors.toList())));
    }

    @GET
    @Path("/todos/{todo_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void todo(@Suspended final AsyncResponse asyncResponse, @PathParam("todo_id") String todo_id) {
        executorService.execute(() -> asyncResponse.resume(todos.get(todo_id)));
    }


    ///todos/:todo_id/tasks/:task_id
    @GET
    @Path("/todos/{todo_id}/tasks/{task_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void getTodoByTodoIdAndTasksByTaskId(@Suspended final AsyncResponse asyncResponse,
            @PathParam("todo_id") String todo_id,
            @PathParam("task_id") String task_id ) {
        executorService.execute(() -> asyncResponse.resume(Optional.ofNullable(todos.get(todo_id)).orElse(new Todo()).getTasks().get(task_id)));
    }

    //GET /todos/:todo_id/tasks/done
    @GET
    @Path("/todos/{todo_id}/tasks/done")
    @Produces(MediaType.APPLICATION_JSON)
    public void setDoneToTask(@Suspended final AsyncResponse asyncResponse,
            @PathParam("todo_id") String todo_id) {
        executorService.execute(() -> asyncResponse.resume(Optional.ofNullable(todos.get(todo_id)).orElse(new Todo()).getTasks().values().stream().map(x -> {x.setStatus(TaskStatus.DONE); return x;}).collect(Collectors.toList()) ));
    }

    // /todos/:todo_id/tasks/not-done
    @GET
    @Path("/todos/{todo_id}/tasks/not-done")
    @Produces(MediaType.APPLICATION_JSON)
    public void setNotDoneToTask(@Suspended final AsyncResponse asyncResponse,
            @PathParam("todo_id") String todo_id) {
        executorService.execute(() -> asyncResponse.resume(Optional.ofNullable(todos.get(todo_id)).orElse(new Todo()).getTasks().values().stream().map(x -> {x.setStatus(TaskStatus.NOT_DONE); return x;}).collect(Collectors.toList()) ));
    }

    // POST /todos
    @POST
    @Path("/todos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void createTodo(@Suspended final AsyncResponse asyncResponse,
                           Todo todo) {
        if(Objects.isNull(todo)) {
            executorService.execute(() -> asyncResponse.resume(Lists.newArrayList()));
        }
        Todo aTodo = new Todo(todo.getName());

        aTodo.setName(todo.getName());
        todos.put(aTodo.getId(), aTodo);
        executorService.execute(() -> asyncResponse.resume(aTodo));
    }

    //POST /todos/:todo_id/tasks
    @POST
    @Path("/todos/{todo_id}/tasks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void createTask(@Suspended final AsyncResponse asyncResponse,
                           @PathParam("todo_id") String todoId,
                           Task task) {
        if(Objects.isNull(task)) {
            executorService.execute(() -> asyncResponse.resume(null));
        }
        Todo todo = Optional.ofNullable(todos.get(todoId)).orElse(new Todo());
        Map<String, Task> tasks = todo.getTasks();
        Task aTask = new Task(task.getName(), task.getDescription());
        tasks.put(aTask.getId(), aTask);
        todo.getTasks().put(aTask.getId(), aTask);
        todos.put(aTask.getId(), todo);
        executorService.execute(() -> asyncResponse.resume( aTask ));
    }

    //PUT /todos/:todo_id/tasks/:task_id
    @PUT
    @Path("/todos/{todo_id}/tasks/{task_id}")
    //     /todos/4f0e5e79-dce7-473a-b7a6-c8120c29b7c9/tasks/969f73d8-1cd7-44da-8513-0e4961cb1fad
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void updateTask(@Suspended final AsyncResponse asyncResponse,
                           @PathParam("todo_id") String todoId,
                           @PathParam("task_id") String taskId,
                           Task paramTask) {
        if(Objects.isNull(paramTask)) {
            executorService.execute(() -> asyncResponse.resume(null));
        }
        Todo todo = Optional.ofNullable(todos.get(todoId)).orElse(new Todo());
        Map<String, Task> tasks = todo.getTasks();
        Task aTask = tasks.get(taskId);
        aTask.setName(paramTask.getName());
        aTask.setDescription(paramTask.getDescription());
        aTask.setStatus(paramTask.getStatus());
        executorService.execute(() -> asyncResponse.resume( aTask ));
    }

//    DELETE /todos/:todo_id
    @DELETE
    @Path("/todos/{todo_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteTodo(@Suspended final AsyncResponse asyncResponse,
                           @PathParam("todo_id") String todoId ) {
        if(!todos.containsKey(todoId)) {
           throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);

        }
        todos.remove(todoId);
        executorService.execute(() -> asyncResponse.resume( new HashMap()));
    }

    //DELETE /todos/:todo_id/tasks/:task_id
    @DELETE
    @Path("/todos/{todo_id}/tasks/{task_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteTodo(@Suspended final AsyncResponse asyncResponse,
                           @PathParam("todo_id") String todoId ,
                           @PathParam("task_id") String taskId ) {
        Map<String, Task> tasks = Optional.ofNullable(todos.get(todoId)).orElse(new Todo()).getTasks();
        if(!tasks.containsKey(taskId)) {
            throw new NotFoundException("can't found todo or task.");
        }
        tasks.remove(taskId);
        executorService.execute(() -> asyncResponse.resume( new HashMap()));
    }


}
