package zefl;

import java.time.LocalDateTime;
import java.util.List;

public class TodoServiceImpl implements TodoService {
    // todo enum??
    public static final String STATUS_NOT_DONE = "NOT_DONE";
    public static final String STATUS_DONE = "DONE";

    // Todo : replace this using Guice
    static TodoDAO todoDao = new TodoMemDAOImpl();

    @Override
    public List<Todo> getTodos() {
        return todoDao.findAll();
    }

    @Override
    public Todo createTodo(String name) {
        String newId = Util.getUUIDString();
        String created = Util.getFormattedStringOf(LocalDateTime.now());
        Todo newTodo = new Todo(newId, name, created);
        todoDao.upsertTodo(newTodo);
        // Usually, it's better to get the data from storage using newId, but this time, it's not necessary.
        return newTodo;
    }

    @Override
    public Todo getTodo(String id) {
        return todoDao.findTodoById(id);
    }

    @Override
    public Task createTask(String todoId, String name, String description) {
        String newId = Util.getUUIDString();
        String created = Util.getFormattedStringOf(LocalDateTime.now());
        Task newTask = new Task(newId, name, description, STATUS_NOT_DONE, created);
        if (!todoDao.upsertTask(todoId, newTask)) {
            // Failed to upsert. Maybe the Todo is gone!
            return null;
        }
        return newTask;
    }

    /**
     * only for testing!!
     */
    public static void resetDao() {
        todoDao = new TodoMemDAOImpl();
    }
}
