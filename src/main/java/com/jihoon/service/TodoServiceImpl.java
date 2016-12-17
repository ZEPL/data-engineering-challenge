package com.jihoon.service;

import com.google.inject.Inject;
import com.jihoon.app;
import com.jihoon.dao.*;
import com.jihoon.model.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * TodoServiceImpl implements TodoService
 */
public class TodoServiceImpl implements TodoService {

    private static final Logger logger = LoggerFactory.getLogger(app.class);

    private ObjectMapper objectMapper;
    private TodoDao todoDao;
    private TaskDao taskDao;

    @Inject
    public TodoServiceImpl(ObjectMapper objectMapper, TodoDao todoDao, TaskDao taskDao){
        this.objectMapper = objectMapper;
        this.todoDao = todoDao;
        this.taskDao = taskDao;
    }

    public Response getTodos() {

        logger.debug("getTodos");
        try {
            List<Todo> todoList = todoDao.getTodos();
            String jsonInString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(todoList);

            logger.debug("getTodos result : " + jsonInString);
            return Response.status(Response.Status.OK).entity(jsonInString).build();
        } catch (Exception e) {
            logger.error("Exception : "+ e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    public Response createTodo(String name) {

        logger.debug("createTodo");
        try {
            if(name == null){
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            Todo newTodo = todoDao.createTodo(name);

            logger.debug("createTodo result : " + newTodo.toString());
            return Response.status(Response.Status.CREATED).entity(newTodo).build();

        } catch (Exception e) {
            logger.error("Exception : "+ e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    public Response deleteTodo(String todoId) {

        logger.debug("deleteTodo");
        try {
            Boolean result = todoDao.deleteTodo(todoId);

            if(result){
                logger.debug("deleteTodo result : Success");
                return Response.status(Response.Status.OK).entity("{}").build();
            }else{
                logger.info("deleteTodo result : Failed");
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

        } catch (Exception e) {
            logger.error("Exception : "+ e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    public Response getTasks(String todoId){

        logger.debug("getTasks");
        try {
            List<Task> taskList = taskDao.getTasks(todoId);
            String jsonInString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(taskList);

            if(taskList.size() == 0){
                logger.info("getTasks result : Failes");
                return Response.status(Response.Status.BAD_REQUEST).build();
            }else{
                logger.debug("getTasks result : " + jsonInString);
                return Response.status(Response.Status.OK).entity(jsonInString).build();
            }
        } catch (Exception e) {
            logger.error("Exception : "+ e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    public Response createTask(String todoId, String name, String description){

        logger.debug("createTask");
        try{
            if(name == null || description == null){
                logger.error("createTask result : INVALID PARAMETERS " + "name : "+name + ",description : "+description);
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            Task newTask = taskDao.createTask(todoId, name, description);
            logger.debug("createTask result : " + newTask.toString());
            return Response.status(Response.Status.CREATED).entity(newTask).build();
        } catch (Exception e) {
            logger.error("Exception : " + e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    public Response deleteTask(String taskId) {

        logger.debug("deleteTask");
        try {
            Boolean result = taskDao.deleteTask(taskId);

            if(result){
                logger.debug("deleteTask result : Success");
                return Response.status(Response.Status.OK).entity("{}").build();
            }else{
                logger.info("deleteTask result : FAILED");
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            logger.error("deleteTask Exception : "+ e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    public Response getTask(String taskId){

        logger.debug("getTask");
        try {
            Task task = taskDao.getTask(taskId);
            String jsonInString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(task);

            logger.debug("getTodos result : " + jsonInString);
            return Response.status(Response.Status.OK).entity(jsonInString).build();
        } catch (Exception e) {
            logger.error("Exception : "+ e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    public Response getTasksDone(String todoId){

        logger.debug("getTasksDone");
        try {
            List<Task> taskList = taskDao.getTasksDone(todoId);
            String jsonInString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(taskList);

            logger.debug("getTodos result : " + jsonInString);
            return Response.status(Response.Status.OK).entity(jsonInString).build();
        } catch (Exception e) {
            logger.error("Exception : "+ e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    public Response getTasksNotDone(String todoId){

        logger.debug("getTasksNotDone");
        try {
            List<Task> taskList = taskDao.getTasksNotDone(todoId);
            String jsonInString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(taskList);

            logger.debug("getTodos result : " + jsonInString);
            return Response.status(Response.Status.OK).entity(jsonInString).build();
        } catch (Exception e) {
            logger.error("Exception : "+ e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    public Response updateTask(String taskId, String name, String description, String status){

        logger.debug("updateTask");
        try {
            if(name == null || description == null || status == null){
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            Task task = taskDao.updateTask(taskId, name , description, status);
            String jsonInString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(task);

            logger.debug("getTodos result : " + jsonInString);
            return Response.status(Response.Status.OK).entity(jsonInString).build();
        } catch (Exception e) {
            logger.error("Exception : "+ e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
