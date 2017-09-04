package dhrim.zeplchallenge.todo;

import com.google.common.annotations.VisibleForTesting;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Singleton
@Slf4j
public abstract class AbstractMapBasedTodoRepo implements TodoRepo {

    // todo.id -> Todo
    private Map<String, Todo> todoMap;

    // todo.id -> Map<taskId, Task>
    private Map<String, Map<String, Task>> taskMapMap;

    /** return Map which store todoId to Todo */
    protected abstract Map<String, Todo> getTodoMapInstance();

    /** return Map which store todoId to Map<taskId, Task> */
    protected abstract Map<String, Map<String, Task>> getTaskMapMapInstance();

    protected void initIfNot() {
        if(todoMap !=null) { return; }
        todoMap = getTodoMapInstance();
        taskMapMap = getTaskMapMapInstance();
    }

    @VisibleForTesting
    void clear_for_test() {
        // could be null if initIfNot() not called.
        if(todoMap ==null) { return; }
        todoMap.clear();
        taskMapMap.clear();
    }

    @Override
    public List<Todo> getTodoList() {
        initIfNot();
        return new ArrayList(todoMap.values());
    }

    @Override
    public Todo getTodo(String todoId) {
        initIfNot();
        if(todoId==null) { throw new IllegalArgumentException("todoId is null."); }
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
        if(todoId==null) { throw new IllegalArgumentException("todoId is null."); }
        return new ArrayList(taskMapMap.get(todoId).values());
    }

    @Override
    public Task getTask(String todoId, String taskId) {
        initIfNot();
        if(todoId==null) { throw new IllegalArgumentException("todoId is null."); }
        if(taskId==null) { throw new IllegalArgumentException("taskId is null."); }
        Map<String, Task> taskMap = taskMapMap.get(todoId);
        if(taskMap==null) { return null; }
        Task task = taskMap.get(taskId);
        if(task==null) { throw new IllegalArgumentException("task not found. todoId="+todoId+", taskId="+taskId); }
        return task;
    }

    @Override
    public Task saveOrUpdate(String todoId, Task task) {
        initIfNot();
        if(todoId==null) { throw new IllegalArgumentException("todoId is null."); }
        if(task.getId()==null) { throw new IllegalArgumentException("task.id is null. task="+task); }
        Map<String, Task> taskMap = taskMapMap.get(todoId);
        if(taskMap==null) {
            taskMap = new HashMap<>();
        }
        taskMap.put(task.getId(), task);
        // TODO : it's not good specific code related with MapDb.
        // taskMapMap is clone instance when using MapDb.
        taskMapMap.put(todoId, taskMap);
        return getTask(todoId, task.getId());
    }


    @Override
    public void removeTodo(String todoId) {
        initIfNot();
        if(todoId==null) { throw new IllegalArgumentException("todoId is null."); }
        todoMap.remove(todoId);
    }

    @Override
    public void removeTask(String todoId, String taskId) {
        initIfNot();
        if(todoId==null) { throw new IllegalArgumentException("todoId is null."); }
        if(taskId==null) { throw new IllegalArgumentException("taskId is null."); }
        Map<String, Task> taskMap = taskMapMap.get(todoId);
        if(taskMap==null) { return; }
        taskMap.remove(taskId);
    }

}
