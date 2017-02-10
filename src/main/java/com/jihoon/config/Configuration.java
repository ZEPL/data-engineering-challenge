package com.jihoon.config;

import com.google.inject.Singleton;
import com.jihoon.app;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Singleton
public class Configuration {

    private static final Logger logger = LoggerFactory.getLogger(app.class);

    private String serverAddress;
    private String serverPort;
    private String dbServerUrl;
    private String dbServerPort;
    private String databaseName;
    private String databaseUser;
    private String databasePassword;


    public Configuration(){
        Properties prop = new Properties();
        InputStream config = null;

        try {

            String filename = "application.conf";
            config = app.class.getClassLoader().getResourceAsStream(filename);
            if(config == null){
                logger.error("Sorry, unable to find " + filename);
                return;
            }
            // load a properties file
            prop.load(config);

            // get the property value and print it out
            String address = prop.getProperty("address");
            String port = prop.getProperty("port");

            String dbServerUrl = prop.getProperty("dbserverurl");
            String dbServerPort = prop.getProperty("dbserverport");
            String databaseName = prop.getProperty("databaseName");
            String dbuser = prop.getProperty("dbuser");
            String dbpassword = prop.getProperty("dbpassword");

            logger.info("address : "+address);
            logger.info("port : "+port);
            logger.info("dbServerUrl : "+dbServerUrl);
            logger.info("dbServerPort : "+dbServerPort);
            logger.info("databaseName : "+databaseName);
            logger.info("dbuser : "+dbuser);
            logger.info("dbpassword : "+dbpassword);

            this.setServerAddress(address);
            this.setServerPort(port);
            this.setDbServerUrl(dbServerUrl);
            this.setDbServerPort(dbServerPort);
            this.setDatabaseName(databaseName);
            this.setDatabaseUser(dbuser);
            this.setDatabasePassword(dbpassword);

        } catch (IOException ex) {
            logger.error("IOException : "+ ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex) {
            logger.error("Exception : " + ex.getMessage());
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

    public String getDatabaseName() {
        return databaseName;
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

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
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
