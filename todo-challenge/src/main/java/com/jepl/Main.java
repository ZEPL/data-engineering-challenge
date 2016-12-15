package com.jepl;

import com.google.common.base.*;
import com.google.inject.*;
import com.google.inject.servlet.*;

import com.jepl.configs.*;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.*;
import org.eclipse.jetty.webapp.*;
import org.slf4j.*;

import java.util.*;

import javax.servlet.*;

public class Main {
    static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static Server server;
    private static WebAppContext context;

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length > 0 && !Strings.isNullOrEmpty(args[0])) {
            try {
                String portString = args[0].split("=")[1];
                port = Integer.parseInt(portString);
            } catch (Exception e) {
                logger.error("Can't parse String to Integer for port number. {}", e);
            }
        }

        server = new Server(port);
        context = new WebAppContext();
        context.setResourceBase("/");
        context.setContextPath("/");
        ServletHandler handler = new ServletHandler();
        FilterHolder filterHolder = handler.addFilterWithMapping(GuiceFilter.class, "/*", EnumSet.of(DispatcherType.INCLUDE, DispatcherType.REQUEST));
        filterHolder.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");
//        handler.addServletWithMapping(TestServlet.class, "/*");
        context.addEventListener(new GuiceServletConfig());
        context.setServletHandler(handler);
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

