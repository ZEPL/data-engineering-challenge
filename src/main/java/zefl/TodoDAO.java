package zefl;

import java.util.List;

public interface TodoDAO {
    boolean insertTodo(Todo todo);
    //boolean insertTask(Task task);
    List<Todo> findAll();

    List<Task> findTasksByTodoId(String todoId);
}
