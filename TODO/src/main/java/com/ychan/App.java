 package com.ychan;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import com.google.inject.Inject;
import com.google.inject.servlet.GuiceFilter;
import com.ychan.config.ServerConfig;

public class App {
  private Server server;

  @Inject
  public App(final Server server) {
    this.server = server;
  }

  public void init() throws Exception { init(true); }
  public void init(boolean sync) throws Exception {
    ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
    contextHandler.setContextPath("/");
    contextHandler.addEventListener(new ServerConfig());
    contextHandler.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
    contextHandler.addServlet(DefaultServlet.class, "/"); // for 404
    server.setHandler(contextHandler);

    NCSARequestLog requestLog = new NCSARequestLog();
    server.setRequestLog(requestLog);

    server.start();
    if(sync) server.join();
  }

  public void stopServer() throws Exception {
    // TODO: Exception
    server.stop();
    server.destroy();
  }

  public void finalize () throws Exception {
    stopServer();
  }
}