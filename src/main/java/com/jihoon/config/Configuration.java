package com.jihoon.config;

import com.google.inject.Singleton;
import com.jihoon.app;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Singleton
public class Configuration {

    private String serverAddress;
    private String serverPort;
    private String database;
    private String databaseUser;
    private String databasePassword;


    public Configuration(){
        Properties prop = new Properties();
        InputStream config = null;

        try {

            String filename = "application.conf";
            config = app.class.getClassLoader().getResourceAsStream(filename);
            if(config == null){
                System.out.println("Sorry, unable to find " + filename);
                return;
            }
            // load a properties file
            prop.load(config);

            // get the property value and print it out
            String address = prop.getProperty("address");
            String port = prop.getProperty("port");

            String database = prop.getProperty("Database");
            String dbuser = prop.getProperty("dbuser");
            String dbpassword = prop.getProperty("dbpassword");

            System.out.println(address);
            System.out.println(port);
            System.out.println(database);
            System.out.println(dbuser);
            System.out.println(dbpassword);

            this.setServerAddress(address);
            this.setServerPort(port);
            this.setDatabase(database);
            this.setDatabaseUser(dbuser);
            this.setDatabasePassword(dbpassword);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public void setDatabaseUser(String databaseUser) {
        this.databaseUser = databaseUser;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }
}
