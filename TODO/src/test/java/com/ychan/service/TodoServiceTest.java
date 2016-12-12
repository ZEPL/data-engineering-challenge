package com.ychan.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.ychan.App;
import com.ychan.dto.Todo;

public class TodoServiceTest {
  static final URI BASE_URI = getBaseURI();
  static final int PORT_NUM = 5000;
  private App app;
  private ObjectMapper mapper;

  private static URI getBaseURI() {
    return UriBuilder.fromUri("http://localhost/").port(PORT_NUM).build();
  }

  @Before
  public void startServer() throws Exception {
    app = new App();
    app.init(PORT_NUM, false);

    mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
  }

  @After
  public void stopServer() throws Exception {
    app.stopServer();
  }

  public ClientResponse sendRequest(final String path, final String method) {
    return sendRequest(path, method, null);
  }

  public ClientResponse sendRequest(final String path, final String method, final String body) {
    Client client = Client.create(new DefaultClientConfig());
    WebResource service = client.resource(getBaseURI());
    return service.path(path).accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).method(method,
        ClientResponse.class, body);
  }

  <T> T fromJson(String json, Class<T> targetClass) {
    Object value = null;
    try {
      value = mapper.readValue(json, targetClass);
    } catch (JsonParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (JsonMappingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return targetClass.cast(value);
  }

  <T> String toJson(Class<T> targetClass, Object contents) throws JsonProcessingException {
    String json = null;
    json = mapper.writeValueAsString(targetClass.cast(contents));
    return json;
  }

  @Test
  public void testGet() {
    ClientResponse res = sendRequest("todos", "GET");
    String text = res.getEntity(String.class);
    assertEquals(200, res.getStatus());
  }

  @Test
  public void testPost() {
    final String mockName = "test";
    ObjectNode mockData = mapper.createObjectNode();
    mockData.put("name", mockName);

    ClientResponse res = sendRequest("todos", "POST", mockData.toString());
    String json = res.getEntity(String.class);
    Todo result = fromJson(json, Todo.class);
    assertEquals(200, res.getStatus());
    assertEquals(mockName, result.name);
  }
}
