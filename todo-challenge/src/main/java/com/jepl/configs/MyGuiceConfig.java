package com.jepl.configs;

import com.google.inject.*;
import com.google.inject.name.*;

import com.fasterxml.jackson.core.type.*;
import com.jepl.daos.*;
import com.jepl.models.*;
import com.jepl.resources.*;
import com.jepl.utils.*;
import com.sun.jersey.guice.*;
import com.sun.jersey.guice.spi.container.servlet.*;

import org.slf4j.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class MyGuiceConfig extends JerseyServletModule {
    static final Logger logger = LoggerFactory.getLogger(MyGuiceConfig.class);

    @Override
    protected void configureServlets() {
        bind(TodoResource.class);
        bind(new TypeLiteral<List<Todo>>(){}).annotatedWith(Names.named("backupTodos")).toInstance(getBackupData());
        bind(Dao.class).to(HazelcastDao.class).asEagerSingleton();
//        bind(Dao.class).to(SimpleDao.class).asEagerSingleton();
        bind(ResponseUtil.class).asEagerSingleton();

        serve("/*").with(GuiceContainer.class);
    }

    private static List<Todo> getBackupData() {
        try {
            String jsonString = FileUtil.readString("/tmp/todos.json");
            return JsonUtil.readValue(jsonString, new TypeReference<List<Todo>>() { });
        } catch (IOException e) {
            logger.error("getBackupData method is failed. {}", e);
        }
        return new ArrayList<>();
    }
}


