package com.jepl.dao;

import com.jepl.daos.*;
import com.jepl.enums.*;
import com.jepl.models.*;

import org.junit.*;

import java.util.*;

import javax.ws.rs.*;

import static org.junit.Assert.*;

public class HazelcastDaoTest {
    private static Dao dao;

    @BeforeClass
    public static void initTest(){
        dao = new HazelcastDao(new ArrayList<>());
    }

    @AfterClass
    public static void afterTest() {
        ((HazelcastDao)dao).destory();
    }

    @Before
    public void initBefore() {
        ((HazelcastDao)dao).deleteAllTask();
    }

    @Test
    public void testGetAllTodo() {
        List<Todo> allTodo = dao.getAllTodo();
        assertEquals(0, allTodo.size());
    }

    @Test(expected = NotFoundException.class)
    public void testGetTodoById() {
        Todo todo = dao.getTodoById("");
    }

    @Test(expected = NotFoundException.class)
    public void testGetAllTaskByTodoId() {
        dao.getAllTaskByTodoId("");
    }

    @Test(expected = NotFoundException.class)
    public void testGetTaskByTodoIdAndTaskId() {
        dao.getTaskByTodoIdAndTaskId("", "");
    }

    @Test
    public void testCreateTodo() {
        Todo todo = dao.createTodo("todo name");
        Todo resultTodo = dao.getTodoById(todo.getId());
        assertTrue(resultTodo.equals(todo));
    }

    @Test
    public void testCreateTask() {
        Todo todo = dao.createTodo("todo name");
        Task task = dao.createTask(todo.getId(), "name", "description");
        Task resultTask = dao.getTaskByTodoIdAndTaskId(todo.getId(), task.getId());
        assertTrue(resultTask.equals(task));
    }

    @Test
    public void testUpdatedStatusToAllTaskInTodo() {
        Todo todo = dao.createTodo("todo name");
        dao.createTask(todo.getId(), "name", "description");
        dao.createTask(todo.getId(), "name", "description");
        dao.updateStatusToAllTaskInTodo(todo.getId(), TaskStatus.DONE);
        Todo resultTodo = dao.getTodoById(todo.getId());
        for (Task task : resultTodo.getTasks().values()) {
            assertEquals(TaskStatus.DONE, task.getStatus());
        }
    }

    @Test
    public void testuUpdateTask() {
        Todo todo = dao.createTodo("todo name");
        Task task = dao.createTask(todo.getId(), "name", "description");
        task.setName("update name");
        task.setDescription("update description");

        dao.updateTask(todo.getId(), task);
        Task resultTask = dao.getTaskByTodoIdAndTaskId(todo.getId(), task.getId());
        assertEquals("update name", resultTask.getName());
        assertEquals("update description", resultTask.getDescription());
    }

    @Test
    public void testDeleteTodo() {
        Todo todo = dao.createTodo("todo name");
        dao.deleteTodo(todo.getId());
        List<Todo> todos = dao.getAllTodo();
        assertEquals(0, todos.size());
    }

    @Test
    public void testDeleteTask() {
        Todo todo = dao.createTodo("todo name");
        Task task = dao.createTask(todo.getId(), "name", "description");
        Todo resultTodo = dao.getTodoById(todo.getId());

        assertEquals(1, resultTodo.getTasks().size());
        dao.deleteTask(todo.getId(), task.getId());
        assertEquals(0, todo.getTasks().size());

    }
}
