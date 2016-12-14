package com.ychan;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import com.google.inject.servlet.GuiceFilter;
import com.ychan.config.AppConfig;

public class App {
  private static Server server;
  public static void main(String[] args) throws Exception {
    if (args.length < 1) {
      // TODO: logger
      System.out.println("Please provide system argument for port number");
      return;
    }

    int port = -1;
    try {
      port = Integer.parseInt(args[0]);
    } catch (NumberFormatException e) {
      System.out.println("Error : Argument format should be number");
      return;
    }

    init(port);
  }

  public static void init(final int port) throws Exception { init(port, true); }
  public static void init(final int port, boolean sync) throws Exception {
    server = new Server(port);

    ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
    contextHandler.setContextPath("/");
    contextHandler.addEventListener(new AppConfig());
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
}