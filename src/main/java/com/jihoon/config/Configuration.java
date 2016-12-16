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
    private String dbServerUrl;
    private String dbServerPort;
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

            String dbServerUrl = prop.getProperty("dbserverurl");
            String dbServerPort = prop.getProperty("dbserverport");
            String databaseName = prop.getProperty("database");
            String dbuser = prop.getProperty("dbuser");
            String dbpassword = prop.getProperty("dbpassword");

//            System.out.println(address);
//            System.out.println(port);
//            System.out.println(dbServerUrl);
//            System.out.println(dbServerPort);
//            System.out.println(databaseName);
//            System.out.println(dbuser);
//            System.out.println(dbpassword);

            this.setServerAddress(address);
            this.setServerPort(port);
            this.setDbServerUrl(dbServerUrl);
            this.setDbServerPort(dbServerPort);
            this.setDatabase(databaseName);
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

    public String getDbServerUrl() {
        return dbServerUrl;
    }

    public void setDbServerUrl(String dbServerUrl) {
        this.dbServerUrl = dbServerUrl;
    }

    public String getDbServerPort() {
        return dbServerPort;
    }

    public void setDbServerPort(String dbServerPort) {
        this.dbServerPort = dbServerPort;
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
