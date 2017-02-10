package com.ychan.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ychan.DBManager;
import com.ychan.DBManager.NotExistException;
import com.ychan.dto.Task;

public class TaskDao implements BaseDao<Task> {

  @Override
  public Object[] getAll() {
    final DBManager db = DBManager.getInstance();
    return db.getPattern(Task.class.getName(), Task.class);
  }

  @Override
  public Task getById(final String id) throws NotExistException, Exception {
    return DBManager.getInstance().get(id, Task.class);
  }

  @Override
  public void put(final String key, final Task value) throws JsonProcessingException {
    DBManager.getInstance().put(key, value);
  }

  @Override
  public void del(final String key) {
    DBManager.getInstance().del(key);
  }
}
