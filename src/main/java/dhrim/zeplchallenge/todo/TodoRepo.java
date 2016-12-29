package dhrim.zeplchallenge.todo;

import java.util.List;

public interface TodoRepo {
    List<Todo> getTodoList();
    Todo getTodo(String todoId);
    Todo saveOrUpdate(Todo todo);

    List<Task> getTaskList(String todoId);
    Task getTask(String todoId, String taskId);
    Task saveOrUpdate(String todoId, Task task);
}
