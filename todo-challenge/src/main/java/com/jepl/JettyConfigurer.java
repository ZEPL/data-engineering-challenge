package com.jepl;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.xml.sax.SAXException;

import java.io.IOException;

public class JettyConfigurer implements JettyServerCustomizer {

    @Override
    public void customize(Server server) {
        WebAppContext webAppContext = (WebAppContext) server.getHandler();
    }

    private XmlConfiguration createConfiguration(String xml) throws IOException, SAXException {
        return new XmlConfiguration(Launcher.class.getResourceAsStream(xml));
    }
}
