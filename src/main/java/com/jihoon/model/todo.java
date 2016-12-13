package com.jihoon.model;

/**
 * Created by USER on 2016-12-13.
 */
public class todo {

    private String id;
    private String name;
    private String created;

    public todo(String id, String name, String created) {
        this.id = id;
        this.name = name;
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
