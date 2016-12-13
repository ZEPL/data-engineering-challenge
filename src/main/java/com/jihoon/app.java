package com.jihoon;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.google.inject.servlet.GuiceFilter;
import com.jihoon.config.configModule;
import com.jihoon.config.configuration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.util.EnumSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class app {

    final static Logger logger = LoggerFactory.getLogger(app.class);

    public static void main(String[] args) throws Exception {

        logger.info("Msg #1");
        logger.warn("Msg #2");
        logger.error("Msg #3");
        logger.debug("Msg #4");

        Injector injector = Guice.createInjector( Stage.PRODUCTION, new configModule());

        configuration config = injector.getInstance(configuration.class);
        Server server = new Server(Integer.parseInt(config.getServerPort()));

        ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
        context.addFilter(GuiceFilter.class, "/*", EnumSet.<javax.servlet.DispatcherType>of(javax.servlet.DispatcherType.REQUEST, javax.servlet.DispatcherType.ASYNC));
        context.addServlet(DefaultServlet.class, "/*");

        server.start();
        server.join();
    }
}
