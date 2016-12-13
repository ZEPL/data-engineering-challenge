package com.ychan.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ychan.DBManager;
import com.ychan.dto.Task;

public class TaskDao implements Dao<Task> {

  @Override
  public Object[] getAll() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Task getById(String id) throws Exception {
    // TODO Auto-generated method stub
    return null;
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
