package dhrim.zeplchallenge.todo;

import com.google.common.annotations.VisibleForTesting;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Singleton
@Slf4j
public abstract class AbstractMapBasedTodoRepo implements TodoRepo {

    // todo.id -> Todo
    private Map<String, Todo> todoMap;

    // todo.id -> List<Task>
    private Map<String, Map<String, Task>> tasksMap;

    protected abstract Map<String, Todo> getTodoMapInstance();
    protected abstract Map<String, Map<String, Task>> getTasksMapInstance();

    protected void initIfNot() {
        if(todoMap!=null) { return; }
        todoMap = getTodoMapInstance();
        tasksMap = getTasksMapInstance();
    }

    @VisibleForTesting
    void clear_for_test() {
        // could be null if initIfNot() not called.
        if(todoMap==null) { return; }
        todoMap.clear();
        tasksMap.clear();
    }

    @Override
    public List<Todo> getTodoList() {
        initIfNot();
        return new ArrayList(todoMap.values());
    }

    @Override
    public Todo getTodo(String todoId) {
        initIfNot();
        if(todoId==null) { throw new IllegalArgumentException("todoId is null"); }
        return todoMap.get(todoId);
    }

    @Override
    public Todo saveOrUpdate(Todo todo) {
        initIfNot();
        if(todo.getId()==null) { throw new IllegalArgumentException("todo.id is null. todo="+todo); }
        todoMap.put(todo.getId(), todo);
        return getTodo(todo.getId());
    }


    @Override
    public List<Task> getTaskList(String todoId) {
        initIfNot();
        if(todoId==null) { throw new IllegalArgumentException("todoId is null"); }
        return new ArrayList(tasksMap.get(todoId).values());
    }

    @Override
    public Task getTask(String todoId, String taskId) {
        initIfNot();
        if(todoId==null) { throw new IllegalArgumentException("todoId is null"); }
        if(taskId==null) { throw new IllegalArgumentException("taskId is null"); }
        Map<String, Task> aTaskMap = tasksMap.get(todoId);
        return aTaskMap.get(taskId);
    }

    @Override
    public Task saveOrUpdate(String todoId, Task task) {
        initIfNot();
        if(todoId==null) { throw new IllegalArgumentException("todoId is null"); }
        if(task.getId()==null) { throw new IllegalArgumentException("task.id is null. task="+task); }
        Map<String, Task> aTaskMap = tasksMap.get(todoId);
        if(aTaskMap==null) { return null; }
        aTaskMap.put(task.getId(), task);
        return getTask(todoId, task.getId());
    }

}
