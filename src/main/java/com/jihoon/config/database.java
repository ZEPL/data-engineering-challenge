package com.jihoon.config;

import com.google.inject.Inject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class database {

    private String username;
    private String password;
    private String databaseName;

    @Inject
    public database(configuration config){
        this.username = config.getDatabaseUser();
        this.password = config.getDatabasePassword();
        this.databaseName = config.getDatabase();

        init();
    }

    public void init(){
        String mongoUrl = "mongodb://"+username+":"+password+"@ds133358.mlab.com:33358/zepl";
        MongoClientURI uri = new MongoClientURI(mongoUrl);
        MongoClient mongoClient = new MongoClient(uri);

        MongoDatabase database = mongoClient.getDatabase("zepl");

        MongoCollection<Document> collection = database.getCollection("todos");

//        collection.find().forEach(printBlock);
    }

}
