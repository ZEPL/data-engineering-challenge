package com.jepl.models;

import com.jepl.enums.*;

import java.io.*;
import java.util.*;

public class Task implements Serializable {

    private String id = "";
    private String name = "";
    private String description = "";
    private TaskStatus status = TaskStatus.NOT_DONE;
    private Date created = null;

    public Task() {
        this("", "");
    }

    public Task(String name, String description) {
        this(UUID.randomUUID().toString(), name, description, TaskStatus.NOT_DONE, new Date());
    }

    public Task(String id, String name, String description, TaskStatus status, Date created) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.created = created;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", created=" + created +
                '}';
    }
}
