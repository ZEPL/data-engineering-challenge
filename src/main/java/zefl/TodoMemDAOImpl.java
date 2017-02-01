package zefl;

import java.util.*;

public class TodoMemDAOImpl implements TodoDAO{
    Map<String, Todo> todoMap = new HashMap<>();

    @Override
    public boolean insertTodo(Todo todo) {
        // TODO param check
        todoMap.put(todo.getId(), todo);
        return true;
    }

    @Override
    public List<Todo> findAll() {
        return new ArrayList<>(todoMap.values());
    }

    @Override
    public List<Task> findTasksByTodoId(String todoId) {
        return new ArrayList<>();
    }
}
