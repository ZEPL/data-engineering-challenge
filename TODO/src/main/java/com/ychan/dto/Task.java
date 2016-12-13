package com.ychan.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Task implements Dto{
  public String id;
  public String name;
  public String description;
  public String status;
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.SSS")
  public Date created;

  public Task(@JsonProperty("name") String name, 
      @JsonProperty("description") String description,
      @JsonProperty("status") String status) {
    this.id = makeId(this.getClass().getName());
    this.created = new Date();
    this.name = name;
    this.description = description;
    this.status = status;
  }

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