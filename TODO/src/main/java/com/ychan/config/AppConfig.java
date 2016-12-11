package com.ychan.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.ychan.dao.*;
import com.ychan.dto.Todo;
import com.ychan.service.TaskService;
import com.ychan.service.TodoService;

public class AppConfig extends GuiceServletContextListener {

  @Override
  protected Injector getInjector() {
    return Guice.createInjector(new JerseyServletModule() {

      @Override
      protected void configureServlets() {
        super.configureServlets();

        bind(TodoService.class);
        bind(TaskService.class);
        bind(new TypeLiteral<Dao<Todo>>() {}).to(TodoDao.class);

        serve("/*").with(GuiceContainer.class);
      }

    });
  }
}
