package com.jihoon.service;

import com.jihoon.model.Todo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 2016-12-13.
 */
public class TodoServiceImp implements TodoService {

    public List<Todo> getTodos(){

        //test for json mapper ( Dummy data )
        List<Todo> todoList = new ArrayList();
        todoList.add(new Todo("1","name1","todo1"));
        todoList.add(new Todo("2","name2","todo2"));

        return todoList;
    }
}
