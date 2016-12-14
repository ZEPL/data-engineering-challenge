package com.jepl.configs;

import com.google.inject.*;
import com.google.inject.servlet.*;

import java.util.*;

public class GuiceServletConfig extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        Iterable configs = Arrays.asList(new MyGuiceConfig(), new EventLoggerModule());
        return Guice.createInjector(configs);
    }
}
