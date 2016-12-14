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
}
