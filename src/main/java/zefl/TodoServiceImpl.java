package zefl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class TodoServiceImpl implements TodoService {
    // Todo : replace this using Guice
    static TodoDAO todoDao = new TodoMemDAOImpl();

    @Override
    public List<Todo> getTodos() {
        return todoDao.findAll();
    }

    @Override
    public Todo createTodo(String name) {
        String newId = UUID.randomUUID().toString();
        String created = Util.getFormattedStringOf(LocalDateTime.now());
        Todo newTodo = new Todo(newId, name, created);
        todoDao.upsertTodo(newTodo);
        // Usually, it's better to get the data from storage using newId, but this time, it's not necessary.
        return newTodo;
    }
}
