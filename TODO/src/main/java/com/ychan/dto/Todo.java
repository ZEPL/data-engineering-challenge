package com.ychan.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Todo implements BaseDto{
  public String id;
  public String name;
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.SSS")
  public Date created;
  
  public Todo(@JsonProperty("name") String name) {
    this.id = makeId(this.getClass().getName());
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