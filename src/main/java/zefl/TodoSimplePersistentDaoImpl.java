package zefl;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class TodoSimplePersistentDaoImpl extends TodoMemDAOImpl {

    private final String filePath;

    @Inject
    public TodoSimplePersistentDaoImpl(@Named("dbFilename") String filePath) {
        this.filePath = filePath;
        try {
            todoMap = (Map<String, Todo>) Util.loadMapObjectFromJsonFile(filePath);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            todoMap = new HashMap<>();
            //Nothing we can do. It's just fresh start
        }
    }

    @Override
    public boolean upsertTodo(Todo todo) {
        return super.upsertTodo(todo) && save();
    }

    @Override
    public boolean upsertTask(String todoId, Task task) {
        return super.upsertTask(todoId, task) && save();
    }

    @Override
    public boolean deleteTodo(String todoId) {
        return super.deleteTodo(todoId) && save();
    }

    @Override
    public boolean deleteTask(String todoId, String taskId) {
        return super.deleteTask(todoId, taskId) && save();
    }

    synchronized private boolean save() {
        try {
            Util.saveMapObjectToJsonFile(todoMap, filePath);
        } catch (IOException e) {
            //todo logging
            return false;
        }
        return true;
    }
}
