package com.ychan.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ychan.DBManager;
import com.ychan.DBManager.NotExistException;
import com.ychan.dto.Todo;

public class TodoDao implements BaseDao<Todo> {

  public Object[] getAll() {
    final DBManager db = DBManager.getInstance();
    return db.getPattern(Todo.class.getName(), Todo.class);
  }

  public Todo getById(final String key) throws NotExistException, Exception {
    return DBManager.getInstance().get(key, Todo.class);
  }

  public void put(final String key, final Todo value) throws JsonProcessingException {
    DBManager.getInstance().put(key, value);
  }

  public void del(final String key) {
    DBManager.getInstance().del(key);
  }

}
