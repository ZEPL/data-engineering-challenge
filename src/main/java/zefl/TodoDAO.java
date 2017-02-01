package zefl;

import java.util.List;

public interface TodoDAO {
    boolean upsertTodo(Todo todo);
    Todo findTodoById(String id);
    List<Todo> findAll();

    List<Task> findTasksByTodoId(String todoId);
    boolean upsertTask(String todoId, Task task);
}
