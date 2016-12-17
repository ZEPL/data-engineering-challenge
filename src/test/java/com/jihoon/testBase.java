package com.jihoon;

import com.jihoon.model.Task;
import com.jihoon.model.Todo;

import java.util.ArrayList;
import java.util.List;

public class testBase {

    public Todo todo1;
    public Todo todo2;
    public List<Todo> mockTodoList;

    public Task task1;
    public Task task2;
    public Task task3;
    public Task task4;

    public Task updateTaskBefore;
    public Task updateTaskAfter;

    public List<Task> mockTaskList;
    public List<Task> mockTaskDoneList;
    public List<Task> mockTaskNotDoneList;

    public testBase() {

        todo1 = new Todo("584fccea734d1d378598735f" , "Todo name", "2016-12-07 05:42:29.809");
        todo2 = new Todo("5850b32fff6f9f871c749c77" , "todo2", "2016-12-14 13:49:19.121");

        mockTodoList = new ArrayList();
        mockTodoList.add(todo1);
        mockTodoList.add(todo2);

        // todo1's tasks ( todo ID : 584fccea734d1d378598735f )
        task1 = new Task("5850d4c3ff6f9f0410a856c2", "task12", "description of the task111", "DONE", "2016-12-14 16:12:35.976");
        task2 = new Task("5850d756ff6f9f73c8603b49", "task2", "description of the task", "NOT_DONE", "2016-12-14 16:23:34.831");
        task3 = new Task("5850dcbaff6f9f44c030a791", "task3", "description of the task", "DONE", "2016-12-14 16:46:34.429");
        task4 = new Task("58536e72ff6f9f7b105d5c6d", "task14", "description of the task", "NOT_DONE", "2016-12-16 15:32:50.918");

        updateTaskBefore = new Task("12345678", "task10", "description of the task", "NOT_DONE", "2016-12-16 15:32:50.918");
        updateTaskAfter = new Task("12345678", "task10", "description of the task", "DONE", "2016-12-16 15:32:50.918");

        mockTaskList = new ArrayList();
        mockTaskList.add(task1);
        mockTaskList.add(task2);
        mockTaskList.add(task3);
        mockTaskList.add(task4);

        mockTaskDoneList = new ArrayList();
        mockTaskList.add(task1);
        mockTaskList.add(task3);

        mockTaskNotDoneList = new ArrayList();
        mockTaskList.add(task2);
        mockTaskList.add(task4);
    }
}