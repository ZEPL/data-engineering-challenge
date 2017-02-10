package com.jihoon.controller;

import com.jihoon.service.TodoService;
import com.jihoon.testBase;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TodoControllerTest extends testBase {

    @Mock
    public TodoService mockTodoService;
    @Mock
    public ObjectMapper objectMapper;

    private TodoController todoController;

    /**
     * The setup block creates a mock instance of ApiClient
     * that we subsequently inject into DataSaver
     */
    @Before
    public void setup() throws Exception{
        // Mock the ApiClient so that we're
        // not actually querying the API
        mockTodoService = mock(TodoService.class);
        objectMapper = new org.codehaus.jackson.map.ObjectMapper();

        // Make up some Mock data

        // Use Mockito to stub out method responses
        when(mockTodoService.getTodos()).thenReturn(Response.status(Response.Status.OK).entity(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mockTodoList)).build());

        // Set up test module that injects mocks
        // instead of actual classes
        todoController = new TodoController(mockTodoService);
    }

    @Test
    public void getTodos() throws Exception {

        String jsonInString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mockTodoList);

        Response expectedResult = Response.status(Response.Status.OK).entity(jsonInString).build();
        Response actualResult = todoController.getTodos();

        System.out.println(actualResult.toString());
        assertEquals(actualResult.getStatus(), expectedResult.getStatus());
        assertEquals(actualResult.getEntity(), expectedResult.getEntity());
    }

    // All Controller APIs call the service layer methods, so service layer (TodoServiceImplTest class) could cover the unit test range
}


