package com.ychan.service;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.junit.After;
import org.junit.Before;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.ychan.App;

public class BaseServiceTest {
  protected static final URI BASE_URI = getBaseURI();
  protected static final int PORT_NUM = 5001;
  protected App app;
  protected ObjectMapper mapper;

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

  public static ClientResponse sendRequest(final String path, final String method) {
    return sendRequest(path, method, null);
  }

  public static ClientResponse sendRequest(final String path, final String method, final String body) {
    final Client client = Client.create(new DefaultClientConfig());
    final WebResource service = client.resource(getBaseURI());
    return service.path(path)
        .accept(MediaType.APPLICATION_JSON)
        .type(MediaType.APPLICATION_JSON)
        .method(method, ClientResponse.class, body);
  }
}
