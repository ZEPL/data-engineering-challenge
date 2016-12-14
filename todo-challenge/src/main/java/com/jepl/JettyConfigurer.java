package com.jepl;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;

public class JettyConfigurer implements JettyServerCustomizer {

    @Override
    public void customize(Server server) {
        WebAppContext webAppContext = (WebAppContext) server.getHandler();
    }
}
