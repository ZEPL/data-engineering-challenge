package zefl;

import java.util.List;

public interface TodoDAO {
    boolean insertTodo(Todo todo);
    List<Todo> findAll();
}
