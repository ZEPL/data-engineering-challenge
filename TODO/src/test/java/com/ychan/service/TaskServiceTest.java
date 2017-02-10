package com.ychan.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.jersey.api.client.ClientResponse;
import com.ychan.DBManager;
import com.ychan.DBManager.NotExistException;
import com.ychan.dto.Task;
import com.ychan.dto.Todo;

public class TaskServiceTest extends BaseServiceTest{
  //mock data
  final Todo mockTodo = new Todo("todo");
  final Task mockTaskWorking = 
      new Task("working", "work hard", Task.NOT_DONE, mockTodo.getId());
  final Task mockTaskMeeting =
      new Task("meeting", "at 12pm", Task.DONE, mockTodo.getId());

//  @Test
//  public void testGetAll() throws IOException {
//    final String addr = MessageFormat.format("{0}/{1}/{2}", "todos", mockTodo.getId(), "tasks");
//    Task[] expected = { mockTaskWorking, mockTodoMeeting };
//    Arrays.sort(expected, (Object a, Object b) -> {
//      return ((Task) a).name.compareTo(((Task) b).name);
//    });
//    
//    DBManager db = DBManager.getInstance();
//    db.flushAll();
//    db.put(mockTodo.getId(), mockTodo);
//    Arrays.stream(expected).forEach(task -> {
//      try {
//        db.put(task.id, task);
//      } catch (JsonProcessingException e) {
//        e.printStackTrace();
//        fail();
//      }
//    });
//    
//    final ClientResponse res = sendRequest(addr, "GET");
//    final String json = res.getEntity(String.class);
//    Task[] responsed = null;
//    responsed = mapper.readValue(json, Task[].class);
//    Arrays.sort(responsed, (Object a, Object b) -> {
//      return ((Task) a).name.compareTo(((Task) b).name);
//    });
//
//    assertEquals(200, res.getStatus());
//    for (int i = 0; i < responsed.length; i++) {
//      assertEquals(expected[i].name, responsed[i].name);
//    }
//  }

  @Test
  public void testGetById() throws IOException {
    Task expected = mockTaskWorking;
    DBManager.getInstance().put(mockTodo.id, mockTodo);
    DBManager.getInstance().put(expected.id, expected);
   
    final String addr = MessageFormat.format("{0}/{1}/{2}/{3}", 
        "todos", mockTodo.getId(), "tasks", expected.getId());
    final ClientResponse res = sendRequest(addr, "GET");
    final String json = res.getEntity(String.class);
    Task responsed = mapper.readValue(json, Task.class);
    
    assertEquals(200, res.getStatus());
    assertEquals(expected.id, responsed.id);
    assertEquals(expected.name, responsed.name);
    assertEquals(expected.description, responsed.description);
    assertEquals(expected.status, responsed.status);
    assertEquals(expected.created, responsed.created);
  }

  @Test
  public void testGetDone() throws IOException {
    testGetWithStatus("done", "not-done");
  }

  public void testGetWithStatus(final String ...endpoints) throws IOException {
    for (String ep : endpoints) {
      final String addr = MessageFormat.format("{0}/{1}/{2}/{3}", "todos", mockTodo.getId(), "tasks", ep);
      Task[] tasks = { mockTaskWorking, mockTaskMeeting };
      Task expected = ep.equals("done") ? mockTaskMeeting : mockTaskWorking;  // TODO: refactoring

      DBManager db = DBManager.getInstance();
      db.flushAll();
      db.put(mockTodo.getId(), mockTodo);
      Arrays.stream(tasks).forEach(task -> {
        try {
          db.put(task.id, task);
        } catch (JsonProcessingException e) {
          e.printStackTrace();
          fail();
        }
      });

      final ClientResponse res = sendRequest(addr, "GET");
      final String json = res.getEntity(String.class);
      Task[] responsed = mapper.readValue(json, Task[].class);
      assertEquals(200, res.getStatus());
      assertEquals(expected.name, responsed[0].name);
    }
  }

  @Test
  public void testPost() throws JsonParseException, JsonMappingException, IOException, NotExistException {
    final String mockName = "test";
    final String mockDesc = "test description";
    final ObjectNode mockData = mapper.createObjectNode();
    mockData.put("name", mockName);
    mockData.put("description", mockDesc);
    
    DBManager.getInstance().put(mockTodo.getId(), mockTodo);

    // test response
    final String addr = MessageFormat.format("{0}/{1}/{2}", "todos", mockTodo.getId(), "tasks");
    final ClientResponse res = sendRequest(addr, "POST", mockData.toString());
    final String json = res.getEntity(String.class);
    final Task responsed = mapper.readValue(json, Task.class);
    assertEquals(200, res.getStatus());
    assertEquals(mockName, responsed.name);

    // test database
    Task actual = DBManager.getInstance().get(responsed.id, Task.class);
    assertEquals(mockName, actual.name);
  }

  @Test
  public void testPut() throws IOException, NotExistException {
    final Task expected = mockTaskWorking;
    expected.setStatus(Task.DONE);
    final ObjectNode mockData = mapper.createObjectNode();
    mockData.put("name", mockTaskWorking.name);
    mockData.put("description", mockTaskWorking.description);
    mockData.put("status", Task.DONE);

    DBManager.getInstance().put(mockTaskWorking.getId(), mockTaskWorking);

    // test response
    final String addr = MessageFormat.format("{0}/{1}/{2}/{3}",
        "todos", mockTodo.getId(), "tasks", expected.id);
    final ClientResponse res = sendRequest(addr, "PUT", mockData.toString());
    final String json = res.getEntity(String.class);
    final Task responsed = mapper.readValue(json, Task.class);
    assertEquals(200, res.getStatus());
    assertEquals(expected.getName(), responsed.name);
    assertEquals(expected.getStatus(), responsed.status);

    // test database
    Task actual = DBManager.getInstance().get(responsed.id, Task.class);
    assertEquals(expected.getName(), actual.name);
    assertEquals(expected.getStatus(), actual.status);
  }

  @Test
  public void testDelete() throws JsonProcessingException {
    DBManager.getInstance().put(mockTodo.getId(), mockTodo);
    DBManager.getInstance().put(mockTaskWorking.getId(), mockTaskWorking);

    final String addr = MessageFormat.format("{0}/{1}/{2}/{3}",
        "todos", mockTodo.getId(), "tasks", mockTaskWorking.getId());
    final ClientResponse res = sendRequest(addr, "DELETE");
    assertEquals(200, res.getStatus());
    try {
      DBManager.getInstance().get(mockTaskWorking.id, Task.class);
    } catch (NotExistException e) {
      // PASS!
      return;
    } catch (Exception e) {
      fail();
    }
    fail();
  }
}
