package zefl.dao;

import java.util.List;
import java.util.Optional;

import zefl.domain.Task;
import zefl.domain.Todo;

public interface TodoDAO {
    boolean upsertTodo(Todo todo);
    Optional<Todo> findTodoById(String id);
    List<Todo> findAll();

    List<Task> findTasksByTodoId(String todoId);
    boolean upsertTask(String todoId, Task task);

    Task findTaskBy(String todoId, String taskId);

    boolean deleteTodo(String todoId);

    boolean deleteTask(String todoId, String taskId);
}
