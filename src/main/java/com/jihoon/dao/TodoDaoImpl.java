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

public class TodoDaoImpl implements TodoDao {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private Database database;


    @Inject
    public void TodoDao(Database database){
        this.database = database;
    }

    public List<Todo> getTodos(){
        List<Todo> todoList = new ArrayList();

        MongoCollection<Document> collection = database.getTodoCollection();

        collection.find().forEach((Block<Document>) document -> {
            logger.debug(document.toString());
            String id = document.get("_id").toString();
            String name = document.get("name").toString();
            String created = document.get("created").toString();

            logger.debug("id : "+ id);
            logger.debug("name : "+ name);
            logger.debug("created : "+ created);

            todoList.add(new Todo(id,name,created));
        });
        return todoList;
    }

    public Todo createTodo(String newName){

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String createdDate = dateTimeFormat.format(now);

        MongoCollection<Document> collection = database.getTodoCollection();

        Document newDocument = new Document();
        newDocument.put("name", newName);
        newDocument.put("created", createdDate);

        collection.insertOne(newDocument);

        Todo todo = new Todo();

        collection.find(Filters.eq("name",newName)).limit(1).forEach((Block<Document>) document -> {
            logger.debug(document.toString());
            String id = document.get("_id").toString();
            String name = document.get("name").toString();
            String created = document.get("created").toString();

            logger.debug("new id : "+ id);
            logger.debug("new name : "+ name);
            logger.debug("new created : "+ created);

            todo.setId(id);
            todo.setName(name);
            todo.setCreated(created);
        });

        return todo;
    }
}
