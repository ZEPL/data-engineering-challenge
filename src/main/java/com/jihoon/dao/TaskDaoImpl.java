package com.jihoon.dao;

import com.google.inject.Inject;
import com.jihoon.config.Database;
import com.jihoon.model.Task;
import com.jihoon.model.Todo;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskDaoImpl implements TaskDao {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private Database database;


    @Inject
    public void TodoDao(Database database){
        this.database = database;
    }

    public List<Task> getTasks(String todoId){
        List<Task> taskList = new ArrayList();

        MongoCollection<Document> collection = database.getTaskCollection();

        collection.find(Filters.eq("todoId",todoId)).forEach((Block<Document>) document -> {
            logger.debug(document.toString());
            String id = document.get("_id").toString();
            String name = document.get("name").toString();
            String description = document.get("description").toString();
            String status = document.get("status").toString();
            String created = document.get("created").toString();

            logger.debug("id : "+ id);
            logger.debug("name : "+ name);
            logger.debug("description : "+ description);
            logger.debug("status : "+ status);
            logger.debug("created : "+ created);

            taskList.add(new Task(id,name,description,status,created));
        });
        return taskList;
    }

    public Task createTask(String newTodoId, String newName, String newDescription){

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String createdDate = dateTimeFormat.format(now);

        MongoCollection<Document> collection = database.getTaskCollection();

        Document newDocument = new Document();
        newDocument.put("name", newName);
        newDocument.put("todoId", newTodoId);
        newDocument.put("description", newDescription);
        newDocument.put("status", "NOT_DONE");
        newDocument.put("created", createdDate);

        collection.insertOne(newDocument);

        Task task = new Task();

        collection.find(Filters.eq("name",newName)).limit(1).forEach((Block<Document>) document -> {
            logger.debug(document.toString());
            String id = document.get("_id").toString();
            String todoId = document.get("todoId").toString();
            String name = document.get("name").toString();
            String description = document.get("description").toString();
            String status = document.get("status").toString();
            String created = document.get("created").toString();

            logger.debug("new id : "+ id);
            logger.debug("new todoId : "+ todoId);
            logger.debug("new name : "+ name);
            logger.debug("new description : "+ description);
            logger.debug("new status : "+ status);
            logger.debug("new created : "+ created);

            task.setId(id);
            task.setName(name);
            task.setDescription(description);
            task.setStatus(status);
            task.setCreated(created);
        });

        return task;
    }

    public Task getTask(String taskId){
        Task task = new Task();

        MongoCollection<Document> collection = database.getTaskCollection();

        collection.find(Filters.eq("_id",new ObjectId(taskId))).limit(1).forEach((Block<Document>) document -> {
            logger.debug(document.toString());
            String id = document.get("_id").toString();
            String name = document.get("name").toString();
            String description = document.get("description").toString();
            String status = document.get("status").toString();
            String created = document.get("created").toString();

            logger.debug("id : "+ id);
            logger.debug("name : "+ name);
            logger.debug("description : "+ description);
            logger.debug("status : "+ status);
            logger.debug("created : "+ created);

            task.setId(id);
            task.setName(name);
            task.setDescription(description);
            task.setStatus(status);
            task.setCreated(created);
        });
        return task;
    }

    public List<Task> getTasksDone(String todoId){
        List<Task> taskList = new ArrayList();

        MongoCollection<Document> collection = database.getTaskCollection();

        collection.find(Filters.and(Filters.eq("todoId",todoId),Filters.eq("status","DONE"))).forEach((Block<Document>) document -> {
            logger.debug(document.toString());
            String id = document.get("_id").toString();
            String name = document.get("name").toString();
            String description = document.get("description").toString();
            String status = document.get("status").toString();
            String created = document.get("created").toString();

            logger.debug("id : "+ id);
            logger.debug("name : "+ name);
            logger.debug("description : "+ description);
            logger.debug("status : "+ status);
            logger.debug("created : "+ created);

            taskList.add(new Task(id,name,description,status,created));
        });
        return taskList;
    }

    public List<Task> getTasksNotDone(String todoId){
        List<Task> taskList = new ArrayList();

        MongoCollection<Document> collection = database.getTaskCollection();

        collection.find(Filters.and(Filters.eq("todoId",todoId),Filters.eq("status","NOT_DONE"))).forEach((Block<Document>) document -> {
            logger.debug(document.toString());
            String id = document.get("_id").toString();
            String name = document.get("name").toString();
            String description = document.get("description").toString();
            String status = document.get("status").toString();
            String created = document.get("created").toString();

            logger.debug("id : "+ id);
            logger.debug("name : "+ name);
            logger.debug("description : "+ description);
            logger.debug("status : "+ status);
            logger.debug("created : "+ created);

            taskList.add(new Task(id,name,description,status,created));
        });
        return taskList;
    }
}
