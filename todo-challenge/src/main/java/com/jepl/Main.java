package com.jepl;

import com.google.common.base.*;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.webapp.*;
import org.slf4j.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class Main {
    static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static Server server;
    private static WebAppContext context;

    public static void main(String[] args) throws Exception {
        int port = 8080;
        String portString = System.getProperty("port");
        if (!Strings.isNullOrEmpty(portString)) {
            try {
                port = Integer.parseInt(portString);
            } catch (Exception e) {
                logger.error("Can't parse String to Integer for port number. {}", e);
            }
        }

        server = new Server(port);
        context = new WebAppContext();
        context.setResourceBase(Main.class.getClass().getResource("/").toString());
        context.setDescriptor(Main.class.getResource("/web.xml").toString());
        context.setContextPath("/");
        context.setParentLoaderPriority(true);

        server.setHandler(context);

        server.start();


    }

    public static void detroy() throws Exception {
        context.shutdown();
        server.stop();
        server.destroy();
    }
}

