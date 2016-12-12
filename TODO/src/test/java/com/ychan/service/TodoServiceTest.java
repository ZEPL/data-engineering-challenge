package com.ychan.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URI;

import javax.swing.text.html.parser.Entity;
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
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.RequestBuilder;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.ychan.App;
import com.ychan.dto.Todo;

import junit.framework.TestCase;

public class TodoServiceTest{
  static final URI BASE_URI = getBaseURI();
  static final int PORT_NUM = 5000;
  private App app;
  
  private static URI getBaseURI() {
      return UriBuilder.fromUri("http://localhost/").port(PORT_NUM).build();
  }

  @Before
  public void startServer() throws Exception {
      app = new App();
      app.init(PORT_NUM, false);
  }

  @After
  public void stopServer() throws Exception {
      app.stopServer();
  }

  @Test
  public void testGet() {
      Client client = Client.create(new DefaultClientConfig());
      WebResource service = client.resource(getBaseURI());
      ClientResponse res = service.path("todos")
          .accept(MediaType.APPLICATION_JSON)
          .get(ClientResponse.class);
      String text = res.getEntity(String.class);

      assertEquals(200, res.getStatus());
  }
}
