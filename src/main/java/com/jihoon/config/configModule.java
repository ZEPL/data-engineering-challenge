package com.jihoon.config;

import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import com.jihoon.controller.TodoController;
import com.jihoon.service.TodoService;
import com.jihoon.service.TodoServiceImp;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import java.util.HashMap;

public class ConfigModule extends ServletModule {

    @Override
    protected void configureServlets() {
        bind(DefaultServlet.class).in(Singleton.class);
        bind(TodoController.class).in(Singleton.class);
        bind(Configuration.class).in(Singleton.class);
        bind(ObjectMapper.class).in(Singleton.class);
        bind(Database.class).in(Singleton.class);


        bind(MessageBodyReader.class).to(JacksonJsonProvider.class);
        bind(MessageBodyWriter.class).to(JacksonJsonProvider.class);
        bind(TodoService.class).to(TodoServiceImp.class);

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("com.sun.jersey.api.json.POJOMappingFeature", "true");
        serve("/*").with(GuiceContainer.class, options);
    }
}
