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

    static final Logger logger = LoggerFactory.getLogger(GuiceServletConfig.class);

    @Override
    protected Injector getInjector() {
        Iterable configs = Arrays.asList(new MyGuiceConfig(), new EventLoggerModule());
        Injector injector = Guice.createInjector(configs);
        Dao dao = injector.getInstance(Dao.class);
        dao.init(getBackupData());
        return injector;
    }

    private Map<String, Todo> getBackupData() {
        try {
            String jsonString = FileUtil.readString("/tmp/todos.json");
            List<Todo> backupedTodos = JsonUtil.readValue(jsonString, new TypeReference<List<Todo>>() { });
            return backupedTodos.stream().collect(Collectors.toMap(Todo::getId, x -> x));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }
}
