package com.ychan;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.*;
import java.util.Arrays;
import java.util.Set;

public class DBManager {
  private static final String DB_PATH = "todo.db";
  private static DBManager instance = null;
  private JedisPool pool;
  private ObjectMapper mapper;

  private DBManager() throws IOException {
    // TODO: life cycle, port
    pool = new JedisPool(new JedisPoolConfig(), "localhost");
    mapper = new ObjectMapper();
  }

  public static DBManager getInstance() {
    if (instance == null) {
      try {
        instance = new DBManager();
      } catch (IOException e) {
        // TODO: exception handling
        e.printStackTrace();
        return null;
      }
    }
    return instance;
  }

  public String put(final String key, final Object value) throws JsonProcessingException {
    final Jedis jedis = pool.getResource();
    final String json = mapper.writeValueAsString(value);
    jedis.set(key, json);
    jedis.save();
    return json;
  }

  public <T> T get(final String key, final Class<T> type)
      throws JsonParseException, JsonMappingException, IOException, NotExistException {
    final Jedis jedis = pool.getResource();
    final String jsonString = jedis.get(key);
    jedis.close();
    if (jsonString == null)
      throw new NotExistException();
    return mapper.readValue(jsonString, type);
  }

  public <T> Object[] getPattern(final String pattern, final Class<T> type) {
    Jedis jedis = pool.getResource();
    final Set<String> keys = jedis.keys(pattern.concat("*"));
    final Object[] values = keys.stream()
        .map(jedis::get)
        .map(json -> {
          // TODO: Not Exist
          Object value = null;
          try {
            value = mapper.readValue(json, type);
          } catch (IOException e) {
            // TODO Auto-generated catch block  /  ERROR
            e.printStackTrace();
          }
          return value;
        }).toArray();
    jedis.close();
    return values;
  }

  public void del(final String... keys) {
    final Jedis jedis = pool.getResource();
    Arrays.stream(keys).map(jedis::del);
    jedis.save();
    jedis.close();
  }

  public void flushAll() {
    final Jedis jedis = pool.getResource();
    jedis.flushAll();
    jedis.close();
  }

  @Override
  protected void finalize() throws Throwable {
    super.finalize();
    pool.destroy();
  }

  public class NotExistException extends Exception {
    private static final long serialVersionUID = 1L;
  }
}