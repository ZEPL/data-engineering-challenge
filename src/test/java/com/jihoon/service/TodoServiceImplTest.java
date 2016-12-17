package com.jihoon.service;

import com.jihoon.dao.TaskDao;
import com.jihoon.dao.TodoDao;
import com.jihoon.testBase;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.ws.rs.core.Response;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TodoServiceImplTest extends testBase {

    @Mock
    public ObjectMapper objectMapper;
    @Mock
    public TodoDao todoDao;
    @Mock
    public TaskDao taskDao;

    private TodoServiceImpl todoServiceImpl;

    /**
     * The setup block creates a mock instance of ApiClient
     * that we subsequently inject into DataSaver
     */
    @Before
    public void setup() {
        // Mock the ApiClient so that we're
        // not actually querying the API
        objectMapper = new org.codehaus.jackson.map.ObjectMapper();
        todoDao = mock(TodoDao.class);
        taskDao = mock(TaskDao.class);

        // Make up some fake data : testBase Class defined

        // Use Mockito to stub out method responses
        when(todoDao.getTodos()).thenReturn(mockTodoList);
        when(todoDao.createTodo(todo1.getName())).thenReturn(todo1);
        when(todoDao.deleteTodo(todo1.getId())).thenReturn(true);

        when(taskDao.getTasks(todo1.getId())).thenReturn(mockTaskList);
        when(taskDao.createTask(todo1.getId(), task1.getName(), task1.getDescription())).thenReturn(task1);
        when(taskDao.deleteTask(task1.getId())).thenReturn(true);
        when(taskDao.getTask(task1.getId())).thenReturn(task1);
        when(taskDao.getTasksDone(todo1.getId())).thenReturn(mockTaskDoneList);
        when(taskDao.getTasksNotDone(todo1.getId())).thenReturn(mockTaskNotDoneList);
        when(taskDao.updateTask(updateTaskBefore.getId(), updateTaskAfter.getName(), updateTaskAfter.getDescription(), updateTaskAfter.getStatus())).thenReturn(updateTaskAfter);

        // Set up test module that injects mocks
        // instead of actual classes
        todoServiceImpl = new TodoServiceImpl(objectMapper, todoDao, taskDao);
    }

    @Test
    public void getTodosTest() throws Exception {

        int expectedResultStatus = 200;
        String expectedResultEntityJsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mockTodoList);

        //Run todoServiceImpl.getTodos()
        Response actualResult = todoServiceImpl.getTodos();

        assertEquals(actualResult.getStatus(), expectedResultStatus);
        assertEquals(actualResult.getEntity(), expectedResultEntityJsonString);
    }

    @Test
    public void createTodoTest() throws Exception {

        int expectedResultStatus = 201;
        String expectedResultEntityJsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(todo1);

        Response actualResult = todoServiceImpl.createTodo(todo1.getName());

        assertEquals(actualResult.getStatus(), expectedResultStatus);
        assertEquals(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(actualResult.getEntity()), expectedResultEntityJsonString);
    }

    @Test
    public void deleteTodoTest() throws Exception {

        int expectedResultStatus = 200;
        String expectedResultEntityJsonString = "{}";

        Response actualResult = todoServiceImpl.deleteTodo(todo1.getId());

        assertEquals(actualResult.getStatus(), expectedResultStatus);
        assertEquals(actualResult.getEntity(), expectedResultEntityJsonString);
    }

    @Test
    public void getTasksTest() throws Exception {

        int expectedResultStatus = 200;
        String expectedResultEntityJsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mockTaskList);

        //Run todoServiceImpl.getTasks()
        Response actualResult = todoServiceImpl.getTasks(todo1.getId());

        assertEquals(actualResult.getStatus(), expectedResultStatus);
        assertEquals(actualResult.getEntity(), expectedResultEntityJsonString);
    }

    @Test
    public void createTaskTest() throws Exception {
        int expectedResultStatus = 201;
        String expectedResultEntityJsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(task1);

        Response actualResult = todoServiceImpl.createTask(todo1.getId(), task1.getName(), task1.getDescription());

        assertEquals(actualResult.getStatus(), expectedResultStatus);
        assertEquals(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(actualResult.getEntity()), expectedResultEntityJsonString);

    }

    @Test
    public void deleteTaskTest() throws Exception {
        int expectedResultStatus = 200;
        String expectedResultEntityJsonString = "{}";

        Response actualResult = todoServiceImpl.deleteTask(task1.getId());

        assertEquals(actualResult.getStatus(), expectedResultStatus);
        assertEquals(actualResult.getEntity(), expectedResultEntityJsonString);
    }

    @Test
    public void getTaskTest() throws Exception {
        int expectedResultStatus = 200;
        String expectedResultEntityJsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(task1);

        //Run todoServiceImpl.getTasks()
        Response actualResult = todoServiceImpl.getTask(task1.getId());

        assertEquals(actualResult.getStatus(), expectedResultStatus);
        assertEquals(actualResult.getEntity(), expectedResultEntityJsonString);
    }

    @Test
    public void getTasksDoneTest() throws Exception {
        int expectedResultStatus = 200;
        String expectedResultEntityJsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mockTaskDoneList);

        //Run todoServiceImpl.getTasks()
        Response actualResult = todoServiceImpl.getTasksDone(todo1.getId());

        assertEquals(actualResult.getStatus(), expectedResultStatus);
        assertEquals(actualResult.getEntity(), expectedResultEntityJsonString);
    }

    @Test
    public void getTasksNotDoneTest() throws Exception {
        int expectedResultStatus = 200;
        String expectedResultEntityJsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mockTaskNotDoneList);

        //Run todoServiceImpl.getTasks()
        Response actualResult = todoServiceImpl.getTasksNotDone(todo1.getId());

        assertEquals(actualResult.getStatus(), expectedResultStatus);
        assertEquals(actualResult.getEntity(), expectedResultEntityJsonString);
    }

    @Test
    public void updateTaskTest() throws Exception {
        int expectedResultStatus = 200;
        String expectedResultEntityJsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(updateTaskAfter);

        //Run todoServiceImpl.getTasks()
        Response actualResult = todoServiceImpl.updateTask(updateTaskBefore.getId(), updateTaskAfter.getName(), updateTaskAfter.getDescription(), updateTaskAfter.getStatus());

        assertEquals(actualResult.getStatus(), expectedResultStatus);
        assertEquals(actualResult.getEntity(), expectedResultEntityJsonString);
    }
}