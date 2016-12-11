package com.ychan;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import com.google.inject.servlet.GuiceFilter;
import com.ychan.config.AppConfig;

public class App {
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
    // server configuration
    Server server = new Server(port);

    ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
    contextHandler.setContextPath("/");
    contextHandler.addEventListener(new AppConfig());
    contextHandler.addFilter(GuiceFilter.class, "/*", null);
    contextHandler.addServlet(DefaultServlet.class, "/"); // for 404
    server.setHandler(contextHandler);

    server.start();
    server.join();
  }
}