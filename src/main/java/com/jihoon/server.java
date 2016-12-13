package com.jihoon;

import com.google.inject.Guice;
import com.google.inject.Stage;
import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.EnumSet;
import java.util.Properties;

public class server {


    public static void main(String[] args) throws Exception {

        Properties prop = new Properties();
        InputStream config = null;

        try {

            String filename = "configuration.conf";
            config = server.class.getClassLoader().getResourceAsStream(filename);
            if(config == null){
                System.out.println("Sorry, unable to find " + filename);
                return;
            }
            // load a properties file
            prop.load(config);

            // get the property value and print it out
            String address = prop.getProperty("address");
            String port = prop.getProperty("port");

            String database = prop.getProperty("database");
            String dbuser = prop.getProperty("dbuser");
            String dbpassword = prop.getProperty("dbpassword");

            System.out.println(address);
            System.out.println(port);
            System.out.println(database);
            System.out.println(dbuser);
            System.out.println(dbpassword);

            Guice.createInjector( Stage.PRODUCTION, new configModule());

            Server server = new Server(Integer.parseInt(port));

            ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
            context.addFilter(GuiceFilter.class, "/*", EnumSet.<javax.servlet.DispatcherType>of(javax.servlet.DispatcherType.REQUEST, javax.servlet.DispatcherType.ASYNC));
            context.addServlet(DefaultServlet.class, "/*");

            server.start();
            server.join();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
