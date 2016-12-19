package com.jihoon;

import com.jihoon.dao.TodoDaoImplTest;
import com.jihoon.model.Task;
import com.jihoon.model.Todo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class testBase {

    private final static Logger logger = LoggerFactory.getLogger(testBase.class);

    // Test Mongo DB server config
    private String dbServerUrl;
    private String dbServerPort;
    private String username;
    private String password;
    private String databaseName;

    private MongoDatabase database;
    private MongoCollection todoCollection;
    private MongoCollection taskCollection;

    // Mock test Data
    public Todo todo1;
    public Todo todo2;
    public List<Todo> mockTodoList;

    public Task task1;
    public Task task2;
    public Task task3;
    public Task task4;

    public Task updateTaskBefore;
    public Task updateTaskAfter;

    public List<Task> mockTaskList;
    public List<Task> mockTaskDoneList;
    public List<Task> mockTaskNotDoneList;

    public testBase() {

        todo1 = new Todo("584fccea734d1d378598735f" , "Todo name", "2016-12-07 05:42:29.809");
        todo2 = new Todo("5850b32fff6f9f871c749c77" , "todo2", "2016-12-14 13:49:19.121");

        mockTodoList = new ArrayList();
        mockTodoList.add(todo1);
        mockTodoList.add(todo2);

        // todo1's tasks ( todo ID : 584fccea734d1d378598735f )
        task1 = new Task("5850d4c3ff6f9f0410a856c2", "task12", "description of the task111", "DONE", "2016-12-14 16:12:35.976");
        task2 = new Task("5850d756ff6f9f73c8603b49", "task2", "description of the task", "NOT_DONE", "2016-12-14 16:23:34.831");
        task3 = new Task("5850dcbaff6f9f44c030a791", "task3", "description of the task", "DONE", "2016-12-14 16:46:34.429");
        task4 = new Task("58536e72ff6f9f7b105d5c6d", "task14", "description of the task", "NOT_DONE", "2016-12-16 15:32:50.918");

        updateTaskBefore = new Task("12345678", "task10", "description of the task", "NOT_DONE", "2016-12-16 15:32:50.918");
        updateTaskAfter = new Task("12345678", "task10", "description of the task", "DONE", "2016-12-16 15:32:50.918");

        mockTaskList = new ArrayList();
        mockTaskList.add(task1);
        mockTaskList.add(task2);
        mockTaskList.add(task3);
        mockTaskList.add(task4);

        mockTaskDoneList = new ArrayList();
        mockTaskList.add(task1);
        mockTaskList.add(task3);

        mockTaskNotDoneList = new ArrayList();
        mockTaskList.add(task2);
        mockTaskList.add(task4);
    }


    public void testMongoDBserverInit(){

        logger.info("TEST Database config");

        Properties prop = new Properties();
        InputStream config = null;

        try {

            String filename = "application.conf";
            config = testBase.class.getClassLoader().getResourceAsStream(filename);
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

            logger.info("dbServerUrl : "+dbServerUrl);
            logger.info("dbServerPort : "+dbServerPort);
            logger.info("databaseName : "+databaseName);
            logger.info("dbuser : "+dbuser);
            logger.info("dbpassword : "+dbpassword);

            this.dbServerUrl = dbServerUrl;
            this.dbServerPort = dbServerPort;
            this.databaseName = databaseName;
            this.username = dbuser;
            this.password = dbpassword;

        } catch (IOException ex) {
            logger.error("IOException : "+ ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex) {
            logger.error("Exception : " + ex.getMessage());
            ex.printStackTrace();
        }

        logger.info("Database init start");
        String mongoUrl = "mongodb://"+username+":"+password+"@"+dbServerUrl+":"+dbServerPort+"/"+databaseName;
        logger.info("mongoUrl : "+ mongoUrl);
        MongoClientURI uri = new MongoClientURI(mongoUrl);
        MongoClient mongoClient = new MongoClient(uri);

        this.database = mongoClient.getDatabase(databaseName);
        this.todoCollection = database.getCollection("todos");
        this.taskCollection = database.getCollection("tasks");

        logger.info("TEST mongoDB init completed");
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public MongoCollection getTodoCollection() {
        return todoCollection;
    }

    public MongoCollection getTaskCollection() {
        return taskCollection;
    }
}