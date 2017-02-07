package zefl.dao;

import java.util.*;

import zefl.domain.Task;
import zefl.domain.Todo;

public class TodoMemDAOImpl implements TodoDAO{
    static Map<String, Todo> todoMap = new HashMap<>();

    @Override
    public boolean upsertTodo(Todo todo) {
        // TODO param check
        todoMap.put(todo.getId(), todo);
        return true;
    }

    @Override
    public Todo findTodoById(String id) {
        // todo Optional?
        return todoMap.get(id);
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
        Todo todo = findTodoById(todoId);
        if (todo == null) {
            return false;
        }
        Map<String, Task> taskMap = todo.getTaskMap();
        taskMap.put(task.getId(), task);
        return true;
    }

    @Override
    public Task findTaskBy(String todoId, String taskId) {
        Todo todo = findTodoById(todoId);
        if (todo == null) {
            return null;
        }
        return todo.getTaskMap().get(taskId);
    }

    @Override
    public boolean deleteTodo(String todoId) {
        return todoMap.remove(todoId) != null;
    }

    @Override
    public boolean deleteTask(String todoId, String taskId) {
        Todo todo = findTodoById(todoId);
        if (todo == null) {
            return false;
        }
        return todo.getTaskMap().remove(taskId) != null;
    }

    public static void resetData() {
        todoMap = new HashMap<>();
    }
}
