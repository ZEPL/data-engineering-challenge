package com.ychan.config;

import org.eclipse.jetty.servlet.DefaultServlet;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
//import com.google.inject.servlet.GuiceServletContextListener;
//import com.sun.jersey.guice.JerseyServletModule;
//import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.ychan.service.TodoService;

public class TodoServiceConfig extends GuiceServletContextListener {

  @Override
  protected Injector getInjector() {
    return Guice.createInjector(new JerseyServletModule() {

      @Override
      protected void configureServlets() {
        super.configureServlets();
        
        bind(TodoService.class);
        serve("/*").with(GuiceContainer.class);
      }
      
    });
  }  
}
