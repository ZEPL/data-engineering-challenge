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
    jedis.bgsave();
    return json;
  }

  public <T> T get(final String key, final Class<T> type) throws JsonParseException, JsonMappingException, IOException {
    Jedis jedis = pool.getResource();
    final String raw = jedis.get(key);
    return mapper.readValue(raw, type);
  }

  public void del(final String... keys) {
    Jedis jedis = pool.getResource();
    Arrays.stream(keys).map(jedis::del);
    jedis.bgsave();
  }

  @Override
  protected void finalize() throws Throwable {
    super.finalize();
    pool.destroy();
  }
}