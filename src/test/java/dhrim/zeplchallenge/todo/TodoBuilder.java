package dhrim.zeplchallenge.todo;

import java.util.Date;

public class TodoBuilder {

    private String id = null;
    private String name = "default_name";
    private Date created = null;

    public Todo build() {
        Todo todo = new Todo();
        todo.setId(id);
        todo.setName(name);
        todo.setCreated(created);
        return todo;
    }

    public TodoBuilder id(String id) {
        this.id = id;
        return this;
    }

    public TodoBuilder name(String name) {
        this.name = name;
        return this;
    }

    public TodoBuilder created(Date created) {
        this.created = created;
        return this;
    }

}
