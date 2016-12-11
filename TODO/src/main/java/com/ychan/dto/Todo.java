package com.ychan.dto;

import java.util.Date;

public class Todo {
  public String id;
  public String name;
  public String description;
  public String status;
  public Date created;
  
  public Todo() {}

  public String getId() { return id; }
  public String getName() { return name; }
  public String getDescription() { return description; }
  public String getStaus() { return status; }
  public Date getCreated() { return created; }

  public void setId(String id) { this.id = id; }
  public void setName(String name) { this.name = name; }
  public void setDescription(String description) { this.description = description; }
  public void setStatus(String status) { this.status = status; }
  public void setCreated(Date created) { this.created = created; }
}