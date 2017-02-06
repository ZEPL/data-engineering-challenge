package zefl;

import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.glassfish.jersey.client.JerseyClientBuilder.createClient;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SimpleIntegrationTodoResourceTest {

    @ClassRule
    public static ServerRule serverRule = new ServerRule();

    private Client client = createClient();

    private Todo callCreateTodo() {
        final Map<String, String> data = new HashMap<>();
        data.put("name", "todo");
        Todo newTodo = client.target(serverRule.getBaseUri()+"todos").request().post(Entity.json(data), Todo.class);
        assertNotNull(newTodo.getId());
        return newTodo;
    }

    private Task callCreateTask(Todo todo) {
        final Map<String, String> taskData = new HashMap<>();
        taskData.put("name", "task10");
        taskData.put("description", "task-desc10");
        Task task = client.target(serverRule.getBaseUri()+"todos/" + todo.getId() + "/tasks")
                .request()
                .post(Entity.json(taskData), Task.class);
        assertNotNull(task.getId());
        return task;
    }

    private Task callUpdateTask(Todo todo, Task task) {
        final Map<String, String> taskData = new HashMap<>();
        taskData.put("name", "task1010");
        taskData.put("description", "task-desc1010");
        taskData.put("status", "DONE");
        Task newTask = client.target(serverRule.getBaseUri()+"todos/" + todo.getId() + "/tasks/" + task.getId())
                .request()
                .put(Entity.json(taskData), Task.class);
        assertNotNull(newTask.getId());
        assertEquals("DONE", newTask.getStatus());
        return newTask;
    }

    @Test
    public void testGetDoneTasksWith1DoneTask() throws Exception {
        final Todo todo = callCreateTodo();
        final Task task = callCreateTask(todo);
        final Task newTask = callUpdateTask(todo, task);

        assertEquals("todo", todo.getName());

        final List<Task> tasks = client.target(serverRule.getBaseUri()+"todos/" + todo.getId() + "/tasks/done")
                .request().get(new GenericType<List<Task>>() {
                });
        assertEquals(1, tasks.size());
        assertEquals(TodoService.STATUS_DONE, tasks.get(0).getStatus());
    }
}