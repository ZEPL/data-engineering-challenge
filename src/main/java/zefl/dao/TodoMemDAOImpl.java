package zefl.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import zefl.domain.Task;
import zefl.domain.Todo;

public class TodoMemDAOImpl implements TodoDAO{
    static Map<String, Todo> todoMap = new HashMap<>();

    @Override
    public boolean upsertTodo(Todo todo) {
        todoMap.put(todo.getId(), todo);
        return true;
    }

    @Override
    public Optional<Todo> findTodoById(String id) {
        Todo result = todoMap.get(id);
        if (result == null) {
            return Optional.empty();
        } else {
            return Optional.of(result);
        }
    }

    @Override
    public List<Todo> findAll() {
        return new ArrayList<>(todoMap.values());
    }

    @Override
    public List<Task> findTasksByTodoId(String todoId) {
        Todo todo = todoMap.get(todoId);
        if (todo == null) {
            return null;
        } else {
            return new ArrayList<>(todo.getTaskMap().values());
        }
    }

    @Override
    public boolean upsertTask(String todoId, Task task) {
        Optional<Todo> maybeTodo = findTodoById(todoId);

        return maybeTodo.map( todo -> {
            Map<String, Task> taskMap = todo.getTaskMap();
            taskMap.put(task.getId(), task);
            return true;
        }).orElse(false);
    }

    @Override
    public Task findTaskBy(String todoId, String taskId) {
        Optional<Todo> maybeTodo = findTodoById(todoId);
        return maybeTodo.map(todo -> todo.getTaskMap().get(taskId))
                .orElse(null);
    }

    @Override
    public boolean deleteTodo(String todoId) {
        return todoMap.remove(todoId) != null;
    }

    @Override
    public boolean deleteTask(String todoId, String taskId) {
        Optional<Todo> maybeTodo = findTodoById(todoId);
        return maybeTodo.map(todo -> null != todo.getTaskMap().remove(taskId))
                .orElse(false);
    }

    public static void resetData() {
        todoMap = new HashMap<>();
    }
}
