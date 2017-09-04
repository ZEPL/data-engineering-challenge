package dhrim.zeplchallenge.todo;

import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
@Slf4j
public class TodoService {

    @Inject
    private TodoRepo todoRepo;

    public List<Todo> getTodoList() {
        return todoRepo.getTodoList();
    }

    public List<Todo> getTodoAsList(String todoId) {
        validateTodoId(todoId);
        Todo todo = todoRepo.getTodo(todoId);
        List<Todo> todoList = new ArrayList<>();
        if(todo!=null) { todoList.add(todo); }
        return todoList;
    }

    public Task getTask(String todoId, String taskId) {
        validateTodoExist(todoId);
        validateTaskId(taskId);
        return todoRepo.getTask(todoId, taskId);
    }

    public List<Task> getTaskList(String todoId, Task.Status status) {
        List<Task> taskList = getTaskList(todoId);
        validateStatus(status);
        return taskList.stream().filter(task -> task.getStatus()==status).collect(Collectors.toList());
    }


    public List<Task> getTaskList(String todoId) {
        validateTodoExist(todoId);
        return todoRepo.getTaskList(todoId);
    }

    public Todo createTodo(Todo todo) {

        validateTodo(todo);

        todo.setId(UUID.randomUUID().toString());
        todo.setCreated(new Date());
        todo = todoRepo.saveOrUpdate(todo);
        log.debug("New Todo created. todo={}", todo);

        return todo;

    }

    public Task createTask(String todoId, Task task) {

        validateTodoExist(todoId);
        validateTask(task);

        task.setId(UUID.randomUUID().toString());
        task.setCreated(new Date());
        task.setStatus(Task.Status.NOT_DONE);
        task = todoRepo.saveOrUpdate(todoId, task);

        log.debug("New Task created. task={}", task);

        return task;

    }

    public Task updateTask(String todoId, String taskId, Task newTask) {
        validateTask(newTask);
        Task oldTask = getTask(todoId, taskId);
        oldTask.setName(newTask.getName());
        oldTask.setDescription(newTask.getDescription());
        oldTask.setStatus(newTask.getStatus());
        todoRepo.saveOrUpdate(todoId, oldTask);
        return todoRepo.getTask(todoId, taskId);
    }

    public void deleteTodo(String todoId) {
        validateTodoExist(todoId);
        todoRepo.removeTodo(todoId);
    }

    public void deleteTask(String todoId, String taskId) {
        Task task = getTask(todoId, taskId);
        if(task==null) { throw new IllegalArgumentException("task not exist for todoId "+todoId+" and taskId "+taskId); }
        todoRepo.removeTask(todoId, taskId);
    }



    private void validateTodoId(String todoId) {
        if(todoId==null) { throw new IllegalArgumentException("todoId is null."); }
    }

    private void validateTaskId(String taskId) {
        if(taskId==null) { throw new IllegalArgumentException("taskId is null."); }
    }

    private void validateTodoExist(String todoId) {
        validateTodoId(todoId);
        Todo todo = todoRepo.getTodo(todoId);
        if(todo==null) { throw new IllegalArgumentException("todo not exist for todoId "+todoId); }
    }

    private void validateTodo(Todo todo) {
        if(todo==null) { throw new IllegalArgumentException("todo is null."); }
        if(todo.getName()==null) { throw new IllegalArgumentException("todo is empty."); }
    }

    private void validateTask(Task task) {
        if(task==null) { throw new IllegalArgumentException("task is null."); }
        if(task.getName()==null && task.getDescription()==null && task.getStatus()==null) { throw new IllegalArgumentException("task is empty."); }
    }

    private void validateStatus(Task.Status status) {
        if (status == null) {
            throw new IllegalArgumentException("status is null.");
        }
    }

}
