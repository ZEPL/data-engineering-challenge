package zefl;

import java.util.List;

public interface TodoService {
    List<Todo> getTodos();
    Todo createTodo(String name);
}
