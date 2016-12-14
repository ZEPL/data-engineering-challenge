package com.jepl.configs;

import com.jepl.resources.*;
import com.sun.jersey.guice.*;
import com.sun.jersey.guice.spi.container.servlet.*;

public class MyGuiceConfig extends JerseyServletModule {

    @Override
    protected void configureServlets() {
//        bind(TodoResource.class);
        bind(TodoResource.class);

        serve("/*").with(GuiceContainer.class);
    }
}


