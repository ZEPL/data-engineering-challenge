package com.ychan.dto;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Todo {
  public String id;
  public String name;
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.SSS")
  public Date created;
  
  public Todo(@JsonProperty("name") String name) {
    Object[] params = new Object[]{
        this.getClass().getName(),
        UUID.randomUUID().toString()};
    this.id = MessageFormat.format("{0}-{1}", params);
    this.created = new Date();
    this.name = name;
  }

  public String getId() { return id; }
  public String getName() { return name; }
  public Date getCreated() { return created; }

  public void setId(String id) { this.id = id; }
  public void setName(String name) { this.name = name; }
  public void setCreated(Date created) { this.created = created; }
}