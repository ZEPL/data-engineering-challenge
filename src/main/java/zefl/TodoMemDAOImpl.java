package zefl;

import java.util.*;

public class TodoMemDAOImpl implements TodoDAO{
    Map<String, Todo> todoMap = new HashMap<>();

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
            return new ArrayList<>();
        } else {
            return new ArrayList<>(todo.getTaskMap().values());
        }
    }

    @Override
    public boolean upsertTask(String todoId, Task task) {
        Todo todo = findTodoById(todoId);
        Map<String, Task> taskMap = todo.getTaskMap();
        taskMap.put(task.getId(), task);
        return true;
    }

}
