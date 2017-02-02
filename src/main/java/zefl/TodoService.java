package zefl;

import java.util.List;

public interface TodoService {
    List<Todo> getTodos();
    Todo createTodo(String name);

    Todo getTodo(String id);

    Task createTask(String todoId, String name, String description);
}
