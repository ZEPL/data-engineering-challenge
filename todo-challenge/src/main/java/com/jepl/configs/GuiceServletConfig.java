package com.jepl.configs;

import com.google.inject.*;
import com.google.inject.servlet.*;

import com.fasterxml.jackson.core.type.*;
import com.jepl.daos.*;
import com.jepl.models.*;
import com.jepl.utils.*;

import org.slf4j.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class GuiceServletConfig extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        Iterable configs = Arrays.asList(new MyGuiceConfig(), new EventLoggerModule());
        return Guice.createInjector(configs);
    }
}
