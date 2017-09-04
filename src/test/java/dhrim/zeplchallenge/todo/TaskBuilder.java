package dhrim.zeplchallenge.todo;

import java.util.Date;

public class TaskBuilder {

    private String id = null;
    private String name ="default_task_name";
    private String description;
    private Task.Status status;
    private Date created;


    public Task build() {
        Task task = new Task();
        task.setId(id);
        task.setName(name);
        task.setDescription(description);
        task.setStatus(status);
        task.setCreated(created);
        return task;
    }

    public TaskBuilder id(String id) {
        this.id = id;
        return this;
    }

    public TaskBuilder name(String name) {
        this.name = name;
        return this;
    }

    public TaskBuilder description(String description) {
        this.description = description;
        return this;
    }

    public TaskBuilder status(Task.Status status) {
        this.status = status;
        return this;
    }

    public TaskBuilder created(Date created) {
        this.created = created;
        return this;
    }

}
