package zefl;

import java.util.HashMap;
import java.util.Map;

public class Todo {
    String id;
    String name;
    // String is enough for the requirements now.
    String created;
    Map<String, Task> tasks;

    public Todo() {
        // Only for jackson - to avoid com.fasterxml.jackson.databind.JsonMappingException
    }

    public Todo(String id, String name, String created, Map<String, Task> tasks) {
        this.id = id;
        this.name = name;
        this.created = created;
        if (tasks == null) {
            this.tasks = new HashMap<>();
        } else {
            this.tasks = tasks;
        }
    }

    public Todo(String id, String name, String created) {
        this(id, name, created, null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Map<String, Task> getTaskMap() {
        return tasks;
    }

    public void setTasks(Map<String, Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Todo todo = (Todo) o;

        if (id != null ? !id.equals(todo.id) : todo.id != null) return false;
        if (name != null ? !name.equals(todo.name) : todo.name != null) return false;
        if (created != null ? !created.equals(todo.created) : todo.created != null) return false;
        return tasks != null ? tasks.equals(todo.tasks) : todo.tasks == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (tasks != null ? tasks.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", created='" + created + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
