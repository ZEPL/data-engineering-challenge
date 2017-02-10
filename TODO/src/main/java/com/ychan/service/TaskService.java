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

  public Task getById(final String key) throws NotExistException, Exception {
    return dao.getById(key);
  }

  public Task[] getAll(final String todoId) {
    final Object[] contents = dao.getAll();
    final Task[] tasks = Arrays.stream(contents).toArray(Task[]::new);
    return Arrays.stream(tasks)
        .filter(task -> task.todoId.equals(todoId))
        .toArray(Task[]::new);
  }

  public Task[] getAllWithStatus(final String todoId, final String status) {
    final Task[] tasks = getAll(todoId);
    return Arrays.stream(tasks)
        .filter(task -> task.status.equals(status))
        .toArray(Task[]::new);
  }

  public void delete(final String taskId) {
    dao.del(taskId);
  }
}
