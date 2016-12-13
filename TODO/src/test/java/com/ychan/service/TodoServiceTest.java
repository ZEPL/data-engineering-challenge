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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.ychan.App;
import com.ychan.DBManager;
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
    final Client client = Client.create(new DefaultClientConfig());
    final WebResource service = client.resource(getBaseURI());
    return service.path(path).accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).method(method,
        ClientResponse.class, body);
  }

  @Test
  public void testGet() {
    final ClientResponse res = sendRequest("todos", "GET");
    final String text = res.getEntity(String.class);
    assertEquals(200, res.getStatus());
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
    final Todo dataInDB = DBManager.getInstance().get(responsed.id, Todo.class);
    assertEquals(mockName, dataInDB.name);
  }
}
