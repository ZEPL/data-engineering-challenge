package com.ychan.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.ychan.controller.TaskController;
import com.ychan.controller.TodoController;
import com.ychan.dao.*;
import com.ychan.service.TaskService;
import com.ychan.service.TodoService;

public class AppConfig extends GuiceServletContextListener {

  @Override
  protected Injector getInjector() {
    return Guice.createInjector(new JerseyServletModule() {

      @Override
      protected void configureServlets() {
        super.configureServlets();

        bind(TodoController.class);
        bind(TodoService.class);
        bind(TodoDao.class);

        bind(TaskController.class);
        bind(TaskService.class);
        bind(TaskDao.class);

        bind(ObjectMapper.class);

        serve("/*").with(GuiceContainer.class);
      }

    });
  }
}
