package com.ychan;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ychan.config.AppConfig;

public class Index {
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

    Injector injector = Guice.createInjector(new AppConfig(port));
    App app = injector.getInstance(App.class);
    app.init();
    app.stopServer();
  }
}
