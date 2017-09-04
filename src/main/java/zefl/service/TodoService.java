package zefl.service;

import java.util.List;
import java.util.Optional;

import zefl.domain.Task;
import zefl.domain.Todo;
import zefl.dao.TodoDAO;

public interface TodoService {
    // todo enum??
    String STATUS_NOT_DONE = "NOT_DONE";
    String STATUS_DONE = "DONE";

    List<Todo> getTodos();
    Todo createTodo(String name);

    Optional<Todo> getTodo(String id);

    Task createTask(String todoId, String name, String description);

    List<Task> getDoneTasks(String id);

    Task getTask(String todoId, String taskId);

    Task updateTask(String todoId, Task task);

    List<Task> getNotDoneTasks(String id);

    boolean removeTodo(String todoId);

    boolean removeTask(String todoId, String taskId);

    TodoDAO getTodoDao();

    void setTodoDao(TodoDAO todoDao);
}
