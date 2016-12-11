package com.ychan.dao;

import java.util.List;

public interface Dao<T> {
  List<T> getAll();
  T getById(String id);
  void put(String key, T value);
  void del(String key);
}
