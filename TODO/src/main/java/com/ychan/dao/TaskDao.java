package com.ychan.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ychan.DBManager;
import com.ychan.DBManager.NotExistException;
import com.ychan.dto.Task;

public class TaskDao implements Dao<Task> {

  @Override
  public Object[] getAll() {
    final DBManager db = DBManager.getInstance();
    return db.getPattern(Task.class.getName(), Task.class);
  }

  @Override
  public Task getById(String id) throws NotExistException, Exception {
    return DBManager.getInstance().get(id, Task.class);
  }

  @Override
  public void put(String key, Task value) throws JsonProcessingException {
    DBManager.getInstance().put(key, value);
  }

  @Override
  public void del(String key) {
    // TODO Auto-generated method stub
    
  }

}
