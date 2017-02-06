package zefl;

import com.google.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TodoServiceImpl implements TodoService {
    @Inject
    TodoDAO todoDao;

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

    @Override
    public List<Task> getDoneTasks(String todoId) {
        return getTasksAndFilterByStatus(todoId, STATUS_DONE);
    }

    @Override
    public List<Task> getNotDoneTasks(String todoId) {
        return getTasksAndFilterByStatus(todoId, STATUS_NOT_DONE);
    }

    @Override
    public boolean removeTodo(String todoId) {
        return todoDao.deleteTodo(todoId);
    }

    @Override
    public boolean removeTask(String todoId, String taskId) {
        return todoDao.deleteTask(todoId, taskId);
    }

    private List<Task> getTasksAndFilterByStatus(String todoId, String status) {
        List<Task> tasks = todoDao.findTasksByTodoId(todoId);
        return filterTaksByPredicate(tasks, equalsStatus(status));
    }

    private Predicate<Task> equalsStatus(String status) {
        return task -> status.equals(task.getStatus());
    }

    private List<Task> filterTaksByPredicate(List<Task> tasks, Predicate<Task> predicate) {
        if (tasks == null) {
            return null;
        }
        return tasks.stream()
                .filter( predicate )
                .collect(Collectors.toList());
    }

    @Override
    public Task getTask(String todoId, String taskId) {
        return todoDao.findTaskBy(todoId, taskId);
    }

    @Override
    public Task updateTask(String todoId, Task task) {
        if (todoDao.upsertTask(todoId, task)) {
            return task;
        } else {
            return null;
        }
    }

    @Override
    public TodoDAO getTodoDao() {
        return todoDao;
    }

    @Override
    public void setTodoDao(TodoDAO todoDao) {
        this.todoDao = todoDao;
    }
}
