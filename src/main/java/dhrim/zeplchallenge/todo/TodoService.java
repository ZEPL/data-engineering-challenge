package dhrim.zeplchallenge.todo;

import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Singleton
@Slf4j
public class TodoService {

    @Inject
    private TodoRepo todoRepo;

    public List<Todo> getTodoList() {
        return todoRepo.getTodoList();
    }

    public Todo getTodoList(String todoId) {
        throw new RuntimeException("not implemented");
    }

    public List<Task> getTaskList(String todoId) {
        throw new RuntimeException("not implemented");
    }

    public Task getTodoTask(String todoId, String taskId) {
        throw new RuntimeException("not implemented");
    }

    public List<Task> getTodoTaskList(String todoId, Task.Status done) {
        throw new RuntimeException("not implemented");
    }

    /**
     * Create new Todo instance,
     *
     * id and created are overwritten with auto generated.
     *
     * @param todo
     * @return created new instance
     */
    public Todo createTodo(Todo todo) {
        if(todo==null) { throw new IllegalArgumentException("todo instance is null."); }
        todo.setId(UUID.randomUUID().toString());
        todo.setCreated(new Date());
        return todoRepo.saveOrUpdate(todo);
    }

    public Task createTask(String todoId, Task task) {
        throw new RuntimeException("not implemented");
    }

    public void deleteTodo(String todoId) {
        throw new RuntimeException("not implemented");
    }

    public void deleteTask(String todoId, String taskId) {
        throw new RuntimeException("not implemented");
    }
}
