package com.ychan.service;

import java.util.Arrays;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import com.ychan.DBManager.NotExistException;
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

  public Task getById(final String id) throws NotExistException, Exception {
    return dao.getById(id);
  }

  public Task[] getAll() {
    final Object[] contents = dao.getAll();
    return Arrays.stream(contents).toArray(Task[]::new);
  }
}
