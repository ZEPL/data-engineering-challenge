package com.ychan.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.jersey.api.client.ClientResponse;
import com.ychan.DBManager;
import com.ychan.DBManager.NotExistException;
import com.ychan.dto.Todo;

public class TodoServiceTest extends BaseServiceTest{
  // mock data
  final Todo mockTodoToday = new Todo("today");
  final Todo mockTodoTomorrow = new Todo("tomorrow");

  @Test
  public void testGetAll() {
    Todo[] expected = { mockTodoToday, mockTodoTomorrow };
    Arrays.sort(expected, (Object a, Object b) -> {
      return ((Todo) a).name.compareTo(((Todo) b).name);
    });
    DBManager db = DBManager.getInstance();
    db.flushAll();
    Arrays.stream(expected).forEach(todo -> {
      try {
        db.put(todo.id, todo);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
        fail();
      }
    });

    final ClientResponse res = sendRequest("todos", "GET");
    final String json = res.getEntity(String.class);
    Todo[] responsed = null;
    try {
      responsed = mapper.readValue(json, Todo[].class);
    } catch (IOException e) {
      e.printStackTrace();
      fail();
    }
    Arrays.sort(responsed, (Object a, Object b) -> {
      return ((Todo) a).name.compareTo(((Todo) b).name);
    });

    assertEquals(200, res.getStatus());
    for (int i = 0; i < responsed.length; i++) {
      assertEquals(expected[i].name, responsed[i].name);
    }
  }

  @Test
  public void testGetById() {
    Todo expected = mockTodoToday;
    try {
      DBManager.getInstance().put(expected.id, expected);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      fail();
    }

    final ClientResponse res = sendRequest("todos/".concat(expected.id), "GET");
    final String json = res.getEntity(String.class);
    Todo responsed = null;
    try {
      responsed = mapper.readValue(json, Todo.class);
    } catch (IOException e) {
      e.printStackTrace();
      fail();
    }
    assertEquals(200, res.getStatus());
    assertEquals(expected.id, responsed.id);
    assertEquals(expected.name, responsed.name);
    assertEquals(expected.created, responsed.created);
  }

  @Test
  public void testPost() throws JsonParseException, JsonMappingException, IOException {
    final String mockName = "test";
    final ObjectNode mockData = mapper.createObjectNode();
    mockData.put("name", mockName);

    // test response
    final ClientResponse res = sendRequest("todos", "POST", mockData.toString());
    final String json = res.getEntity(String.class);
    final Todo responsed = mapper.readValue(json, Todo.class);
    assertEquals(200, res.getStatus());
    assertEquals(mockName, responsed.name);

    // test database
    Todo actual = null;
    try {
      actual = DBManager.getInstance().get(responsed.id, Todo.class);
    } catch (NotExistException e) {
      e.printStackTrace();
      fail();
    }
    assertEquals(mockName, actual.name);
  }
}
