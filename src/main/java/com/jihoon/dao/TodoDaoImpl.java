package com.jihoon.dao;

import com.google.inject.Inject;
import com.jihoon.config.Database;
import com.jihoon.model.Todo;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TodoDao class : implements TodoDao
 *
 * This Dao handle mongoDB database and Todos Collection data
 */
public class TodoDaoImpl implements TodoDao {

    private static final Logger logger = LoggerFactory.getLogger(TodoDaoImpl.class);

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

    public Boolean deleteTodo(String todoId){

        MongoCollection<Document> collection = database.getTodoCollection();

        Bson filter = new Document("_id", new ObjectId(todoId));
        DeleteResult deleteResult = collection.deleteOne(filter);

        if(deleteResult.getDeletedCount() == 1){
            logger.debug("deleteTodo : "+ todoId + " deleted");
            return true;
        }else{
            logger.error("deleteTodo "+ todoId + " ERROR deleted item: "+ deleteResult.getDeletedCount());
            return false;
        }
    }
}
