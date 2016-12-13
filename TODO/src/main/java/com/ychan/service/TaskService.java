package com.ychan.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import com.ychan.dao.TaskDao;
import com.ychan.dto.Task;

public class TaskService implements BaseService {
  private TaskDao dao;

  @Inject
  public TaskService(TaskDao dao) {
    this.dao = dao;
  }

  public void add(Task task) throws JsonProcessingException {
    dao.put(task.id, task);
  }
}
