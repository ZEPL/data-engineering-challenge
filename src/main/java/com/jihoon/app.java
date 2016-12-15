package com.jihoon;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.google.inject.servlet.GuiceFilter;
import com.jihoon.config.ConfigModule;
import com.jihoon.config.Configuration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.util.EnumSet;

public class app {

    public static void main(String[] args) throws Exception {

        Injector injector = Guice.createInjector( Stage.PRODUCTION, new ConfigModule());

        Configuration config = injector.getInstance(Configuration.class);
        Server server = new Server(Integer.parseInt(config.getServerPort()));

        ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
        context.addFilter(GuiceFilter.class, "/*", EnumSet.<javax.servlet.DispatcherType>of(javax.servlet.DispatcherType.REQUEST, javax.servlet.DispatcherType.ASYNC));
        context.addServlet(DefaultServlet.class, "/*");

        server.start();
        server.join();
    }
}
