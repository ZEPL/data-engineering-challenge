package com.jepl.models;

import java.io.*;
import java.util.*;

public class Todo implements Serializable{
    public Todo() {
        this("", "",null);
    }

    public Todo(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.created = new Date();
    }


    public Todo(String id, String name, Date created) {
        this.id = id;
        this.name = name;
        this.created = created;
    }

    private String id = "";
    private String name = "";
    private Date created = null;
    private Map<String, Task> tasks = new HashMap<>();


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

    public Date getCreated() {
        return created;
    }

    public Map<String, Task> getTasks() {
        return tasks;
    }

    public void setTasks(Map<String, Task> tasks) {
        this.tasks = tasks;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", created=" + created +
                ", tasks=" + tasks +
                '}';
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
}
