package com.jihoon.dao;

import com.jihoon.config.Database;
import com.jihoon.model.Task;
import com.jihoon.testBase;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaskDaoImplTest extends testBase {

    private final static Logger logger = LoggerFactory.getLogger(TodoDaoImplTest.class);

    @Mock
    public Database database;

    private TaskDaoImpl taskDaoImpl;

    @Before
    public void setup() throws Exception {

        //TEST mongoDB Server Init
        testMongoDBserverInit();

        //mock database class
        database = mock(Database.class);

        //when database getTaskCollection() is called, test server's database collection returns.
        when(database.getTaskCollection()).thenReturn(this.getTaskCollection());

        //Create taskDaoImpl instance for tests
        taskDaoImpl = new TaskDaoImpl(database);

        //clean mongoDB collection : delete all documents
        this.getTaskCollection().deleteMany(new Document());
    }

    @Test
    public void getTasksTest() {

        //create test data-set on TEST mongoDB collection
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String createdDate = dateTimeFormat.format(now);

        //data-set 1
        String mockTestTodoId = "1234567890";
        Document newDocument1 = new Document();
        newDocument1.put("todoId", mockTestTodoId);
        newDocument1.put("name", task1.getName());
        newDocument1.put("description", task1.getDescription());
        newDocument1.put("status", task1.getStatus());
        newDocument1.put("created", createdDate);
        
        this.getTaskCollection().insertOne(newDocument1);

        //data-set 2
        Document newDocument2 = new Document();
        newDocument2.put("todoId", mockTestTodoId);
        newDocument2.put("name", task2.getName());
        newDocument2.put("description", task2.getDescription());
        newDocument2.put("status", task2.getStatus());
        newDocument2.put("created", createdDate);

        this.getTaskCollection().insertOne(newDocument2);

        List<Task> taskListResult = taskDaoImpl.getTasks(mockTestTodoId);
        logger.info(taskListResult.toString());

        assertEquals(taskListResult.size() , 2);

        assertEquals(taskListResult.get(0).getName(), task1.getName());
        assertEquals(taskListResult.get(0).getDescription(), task1.getDescription());
        assertEquals(taskListResult.get(0).getStatus(), task1.getStatus());
        assertEquals(taskListResult.get(0).getCreated(), createdDate);

        assertEquals(taskListResult.get(1).getName(), task2.getName());
        assertEquals(taskListResult.get(1).getDescription(), task2.getDescription());
        assertEquals(taskListResult.get(1).getStatus(), task2.getStatus());
        assertEquals(taskListResult.get(1).getCreated(), createdDate);
    }

    @Test
    public void createTaskTest() {

        String mockTestTodoId = "1234567890";
        Task taskCreateResult = taskDaoImpl.createTask(mockTestTodoId , task1.getName(), task1.getDescription());

        assertEquals(taskCreateResult.getName(), task1.getName());
        assertEquals(taskCreateResult.getDescription(), task1.getDescription());
        assertEquals(taskCreateResult.getStatus(), "NOT_DONE");

        List<Task> taskListResult = taskDaoImpl.getTasks(mockTestTodoId);
        logger.info(taskListResult.toString());

        assertEquals(taskListResult.size() , 1);

        assertEquals(taskListResult.get(0).getName(), task1.getName());
        assertEquals(taskListResult.get(0).getDescription(), task1.getDescription());
        assertEquals(taskListResult.get(0).getStatus(), "NOT_DONE");
    }

    @Test
    public void getTaskTest() {

        //create test data-set on TEST mongoDB collection
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String createdDate = dateTimeFormat.format(now);

        //data-set 1
        String mockTestTodoId = "1234567890";
        Document newDocument1 = new Document();
        newDocument1.put("todoId", mockTestTodoId);
        newDocument1.put("name", task1.getName());
        newDocument1.put("description", task1.getDescription());
        newDocument1.put("status", task1.getStatus());
        newDocument1.put("created", createdDate);

        this.getTaskCollection().insertOne(newDocument1);

        //data-set 2
        Document newDocument2 = new Document();
        newDocument2.put("todoId", mockTestTodoId);
        newDocument2.put("name", task2.getName());
        newDocument2.put("description", task2.getDescription());
        newDocument2.put("status", task2.getStatus());
        newDocument2.put("created", createdDate);

        this.getTaskCollection().insertOne(newDocument2);

        List<Task> taskListResult = taskDaoImpl.getTasks(mockTestTodoId);
        logger.info(taskListResult.toString());

        assertEquals(taskListResult.size() , 2);

        assertEquals(taskListResult.get(0).getName(), task1.getName());
        assertEquals(taskListResult.get(0).getDescription(), task1.getDescription());
        assertEquals(taskListResult.get(0).getStatus(), task1.getStatus());
        assertEquals(taskListResult.get(0).getCreated(), createdDate);

        String taskId1 = taskListResult.get(0).getId();

        assertEquals(taskListResult.get(1).getName(), task2.getName());
        assertEquals(taskListResult.get(1).getDescription(), task2.getDescription());
        assertEquals(taskListResult.get(1).getStatus(), task2.getStatus());
        assertEquals(taskListResult.get(1).getCreated(), createdDate);

        String taskId2 = taskListResult.get(1).getId();

        Task taskResult1 = taskDaoImpl.getTask(taskId1);
        Task taskResult2 = taskDaoImpl.getTask(taskId2);

        assertEquals(taskResult1.getName(), task1.getName());
        assertEquals(taskResult1.getDescription(), task1.getDescription());
        assertEquals(taskResult1.getStatus(), task1.getStatus());
        assertEquals(taskResult1.getCreated(), createdDate);

        assertEquals(taskResult2.getName(), task2.getName());
        assertEquals(taskResult2.getDescription(), task2.getDescription());
        assertEquals(taskResult2.getStatus(), task2.getStatus());
        assertEquals(taskResult2.getCreated(), createdDate);
    }

    @Test
    public void getTasksDoneTest() {

        //create test data-set on TEST mongoDB collection
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String createdDate = dateTimeFormat.format(now);

        //data-set 1
        String mockTestTodoId = "1234567890";
        Document newDocument1 = new Document();
        newDocument1.put("todoId", mockTestTodoId);
        newDocument1.put("name", task1.getName());
        newDocument1.put("description", task1.getDescription());
        newDocument1.put("status", "DONE");
        newDocument1.put("created", createdDate);

        this.getTaskCollection().insertOne(newDocument1);

        //data-set 2
        Document newDocument2 = new Document();
        newDocument2.put("todoId", mockTestTodoId);
        newDocument2.put("name", task2.getName());
        newDocument2.put("description", task2.getDescription());
        newDocument2.put("status", "NOT_DONE");
        newDocument2.put("created", createdDate);

        this.getTaskCollection().insertOne(newDocument2);

        //data-set 3
        Document newDocument3 = new Document();
        newDocument3.put("todoId", mockTestTodoId);
        newDocument3.put("name", task3.getName());
        newDocument3.put("description", task3.getDescription());
        newDocument3.put("status", "DONE");
        newDocument3.put("created", createdDate);

        this.getTaskCollection().insertOne(newDocument3);

        //data-set 4
        Document newDocument4 = new Document();
        newDocument4.put("todoId", mockTestTodoId);
        newDocument4.put("name", task4.getName());
        newDocument4.put("description", task4.getDescription());
        newDocument4.put("status", "NOT_DONE");
        newDocument4.put("created", createdDate);

        this.getTaskCollection().insertOne(newDocument4);

        List<Task> taskListResult = taskDaoImpl.getTasksDone(mockTestTodoId);
        logger.info(taskListResult.toString());

        assertEquals(taskListResult.size() , 2);

        assertEquals(taskListResult.get(0).getName(), task1.getName());
        assertEquals(taskListResult.get(0).getDescription(), task1.getDescription());
        assertEquals(taskListResult.get(0).getStatus(), task1.getStatus());
        assertEquals(taskListResult.get(0).getCreated(), createdDate);

        assertEquals(taskListResult.get(1).getName(), task3.getName());
        assertEquals(taskListResult.get(1).getDescription(), task3.getDescription());
        assertEquals(taskListResult.get(1).getStatus(), task3.getStatus());
        assertEquals(taskListResult.get(1).getCreated(), createdDate);
    }

    @Test
    public void getTasksNotDoneTest() {

        //create test data-set on TEST mongoDB collection
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String createdDate = dateTimeFormat.format(now);

        //data-set 1
        String mockTestTodoId = "1234567890";
        Document newDocument1 = new Document();
        newDocument1.put("todoId", mockTestTodoId);
        newDocument1.put("name", task1.getName());
        newDocument1.put("description", task1.getDescription());
        newDocument1.put("status", "DONE");
        newDocument1.put("created", createdDate);

        this.getTaskCollection().insertOne(newDocument1);

        //data-set 2
        Document newDocument2 = new Document();
        newDocument2.put("todoId", mockTestTodoId);
        newDocument2.put("name", task2.getName());
        newDocument2.put("description", task2.getDescription());
        newDocument2.put("status", "NOT_DONE");
        newDocument2.put("created", createdDate);

        this.getTaskCollection().insertOne(newDocument2);

        //data-set 3
        Document newDocument3 = new Document();
        newDocument3.put("todoId", mockTestTodoId);
        newDocument3.put("name", task3.getName());
        newDocument3.put("description", task3.getDescription());
        newDocument3.put("status", "DONE");
        newDocument3.put("created", createdDate);

        this.getTaskCollection().insertOne(newDocument3);

        //data-set 4
        Document newDocument4 = new Document();
        newDocument4.put("todoId", mockTestTodoId);
        newDocument4.put("name", task4.getName());
        newDocument4.put("description", task4.getDescription());
        newDocument4.put("status", "NOT_DONE");
        newDocument4.put("created", createdDate);

        this.getTaskCollection().insertOne(newDocument4);

        List<Task> taskListResult = taskDaoImpl.getTasksNotDone(mockTestTodoId);
        logger.info(taskListResult.toString());

        assertEquals(taskListResult.size() , 2);

        assertEquals(taskListResult.get(0).getName(), task2.getName());
        assertEquals(taskListResult.get(0).getDescription(), task2.getDescription());
        assertEquals(taskListResult.get(0).getStatus(), task2.getStatus());
        assertEquals(taskListResult.get(0).getCreated(), createdDate);

        assertEquals(taskListResult.get(1).getName(), task4.getName());
        assertEquals(taskListResult.get(1).getDescription(), task4.getDescription());
        assertEquals(taskListResult.get(1).getStatus(), task4.getStatus());
        assertEquals(taskListResult.get(1).getCreated(), createdDate);
    }

    @Test
    public void updateTaskTest() {

        String mockTestTodoId = "1234567890";
        Task taskCreateResult = taskDaoImpl.createTask(mockTestTodoId , task1.getName(), task1.getDescription());

        assertEquals(taskCreateResult.getName(), task1.getName());
        assertEquals(taskCreateResult.getDescription(), task1.getDescription());
        assertEquals(taskCreateResult.getStatus(), "NOT_DONE");

        String taskId = taskCreateResult.getId();

        Task taskUpdateResult = taskDaoImpl.updateTask(taskId, task1.getName(), task1.getDescription(), "DONE");
        logger.info(taskUpdateResult.toString());

        assertEquals(taskUpdateResult.getName(), task1.getName());
        assertEquals(taskUpdateResult.getDescription(), task1.getDescription());
        assertEquals(taskUpdateResult.getStatus(), "DONE");
    }

    @Test
    public void deleteTaskTest() {

        String mockTestTodoId = "1234567890";
        Task taskCreateResult = taskDaoImpl.createTask(mockTestTodoId , task1.getName(), task1.getDescription());

        assertEquals(taskCreateResult.getName(), task1.getName());
        assertEquals(taskCreateResult.getDescription(), task1.getDescription());
        assertEquals(taskCreateResult.getStatus(), "NOT_DONE");

        String taskId = taskCreateResult.getId();

        Boolean taskUpdateResult = taskDaoImpl.deleteTask(taskId);

        assertEquals(taskUpdateResult, true);

        Task taskGetResult = taskDaoImpl.getTask(taskId);
        assertEquals(taskGetResult.getName() , null);
        assertEquals(taskGetResult.getDescription() , null);
        assertEquals(taskGetResult.getStatus() , null);
    }
}
