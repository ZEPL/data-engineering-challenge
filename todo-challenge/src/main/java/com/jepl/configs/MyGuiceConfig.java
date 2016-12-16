package com.jepl.configs;

import com.google.inject.*;

import com.jepl.daos.*;
import com.jepl.resources.*;
import com.jepl.utils.*;
import com.sun.jersey.guice.*;
import com.sun.jersey.guice.spi.container.servlet.*;

public class MyGuiceConfig extends JerseyServletModule {

    @Override
    protected void configureServlets() {
        bind(TodoResource.class);
        bind(Dao.class).to(SimpleDao.class).asEagerSingleton();
        bind(ResponseUtil.class).asEagerSingleton();

        serve("/*").with(GuiceContainer.class);
    }
}


