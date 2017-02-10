package com.jihoon.dao;

import com.jihoon.config.Database;
import com.jihoon.model.Todo;
import com.jihoon.testBase;
import org.bson.Document;
import org.bson.conversions.Bson;
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

public class TodoDaoImplTest extends testBase {

    private final static Logger logger = LoggerFactory.getLogger(TodoDaoImplTest.class);

    @Mock
    public Database database;

    private TodoDaoImpl todoDaoImpl;

    @Before
    public void setup() throws Exception {

        //TEST mongoDB Server Init
        testMongoDBserverInit();

        //mock database class
        database = mock(Database.class);

        //when database getTodoCollection() is called, test server's database collection returns.
        when(database.getTodoCollection()).thenReturn(this.getTodoCollection());

        //Create todoDaoImpl instance for tests
        todoDaoImpl = new TodoDaoImpl(database);

        //clean mongoDB collection : delete all documents
        this.getTodoCollection().deleteMany(new Document());
    }

    @Test
    public void getTodosTest() {

        //create test data-set on TEST mongoDB collection
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String createdDate = dateTimeFormat.format(now);

        //data-set 1
        Document newDocument1 = new Document();
        newDocument1.put("name", todo1.getName());
        newDocument1.put("created", createdDate);

        this.getTodoCollection().insertOne(newDocument1);

        //data-set 2
        Document newDocument2 = new Document();
        newDocument2.put("name", todo2.getName());
        newDocument2.put("created", createdDate);

        this.getTodoCollection().insertOne(newDocument2);

        List<Todo> todoListResult = todoDaoImpl.getTodos();
        logger.info(todoListResult.toString());

        assertEquals(todoListResult.size() , 2);

        assertEquals(todoListResult.get(0).getName(), todo1.getName());
        assertEquals(todoListResult.get(0).getCreated(), createdDate);

        assertEquals(todoListResult.get(1).getName(), todo2.getName());
        assertEquals(todoListResult.get(1).getCreated(), createdDate);
    }

    @Test
    public void createTodoTest() {

        //data-set 1
        String testDataName = "June1234";
        Todo todoCreateResult = todoDaoImpl.createTodo(testDataName);
        assertEquals(todoCreateResult.getName() , testDataName);

        List<Todo> todoListResult = todoDaoImpl.getTodos();
        logger.info(todoListResult.toString());

        assertEquals(todoListResult.size() , 1);
        assertEquals(todoListResult.get(0).getName(), testDataName);
    }

    @Test
    public void deleteTodoTest() {

        //create test data-set on TEST mongoDB collection
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String createdDate = dateTimeFormat.format(now);

        //Insert data-set 1
        Document newDocument1 = new Document();
        String testDataName = "June1234";
        newDocument1.put("name", testDataName);
        newDocument1.put("created", createdDate);

        this.getTodoCollection().insertOne(newDocument1);

        List<Todo> todoListResult = todoDaoImpl.getTodos();

        assertEquals(todoListResult.size() , 1);
        assertEquals(todoListResult.get(0).getName(), testDataName);
        assertEquals(todoListResult.get(0).getCreated(), createdDate);

        String dataId = todoListResult.get(0).getId();

        //Delete data-set 1
        Boolean todoDeleteResult = todoDaoImpl.deleteTodo(dataId);
        assertEquals(todoDeleteResult, true);

        List<Todo> todoGetListResult = todoDaoImpl.getTodos();
        assertEquals(todoGetListResult.size() , 0);
    }
}
