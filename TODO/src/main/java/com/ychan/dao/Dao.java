package com.ychan.dao;

public interface Dao<T> {
  Object[] getAll();
  T getById(String id) throws Exception;
  void put(String key, T value) throws Exception;
  void del(String key);
}
