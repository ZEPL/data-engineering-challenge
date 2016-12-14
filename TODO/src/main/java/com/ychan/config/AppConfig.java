package com.ychan.config;

import org.eclipse.jetty.server.Server;

import com.google.inject.AbstractModule;

public class AppConfig extends AbstractModule {
  private int port;

  public AppConfig(final int port) {
    this.port = port;
  }

  @Override
  protected void configure() {
    bind(Server.class).toInstance(new Server(port));
  }

}
