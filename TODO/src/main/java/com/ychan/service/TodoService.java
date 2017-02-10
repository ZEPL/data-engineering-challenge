package com.ychan.service;

import java.util.Arrays;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import com.ychan.DBManager.NotExistException;
import com.ychan.dao.TodoDao;
import com.ychan.dto.Todo;

public class TodoService implements BaseService{
  private TodoDao dao;

  @Inject
  public TodoService(TodoDao dao) {
    this.dao = dao;
  }

  public Todo getById(final String id) throws NotExistException, Exception {
    return dao.getById(id);
  }

  public Todo[] getAll() {
    final Object[] contents = dao.getAll();
    return Arrays.stream(contents).toArray(Todo[]::new);
  }

  public void add(Todo todo) throws JsonProcessingException {
    dao.put(todo.id, todo);
  }

  public void delete(final String todoId) {
    dao.del(todoId);
  }
}