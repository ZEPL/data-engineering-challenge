# Back-end engineer challenge
This challenge is designed to assess the ability of a back-end candidate to write maintainable and well structured (reusable code, good coding practice) backend service.

## Submission instructions
 1. Fork this repository on github.
 2. Complete the project as described below within your fork.
 3. Keep the commit history - don't squash.
 4. Push all of your changes to your fork on github and submit a pull request to this repository.

## Project description
Write a HTTP restful **TODO** service. This service will have an open api to let user manage his TODOs.

**Topology**

A Todo is a container of a list of Tasks.
You can imagine a structure like this:

 ```
  - TODO
   - Task1
   - Task2
   - task3
   - etcetc 
 ```

**Tasks**

- You will have to find a nice way to organize and persist each todos and tasks. (If we restart the service we should be able to recover the list of todos and tasks)
- You need to handle eventual error with a proper HTTP status + error message.
- You need to log events
- We need to be able to start the service with different port. (provide a way to configure it)
- You will provide the following endpoint

 * `GET /todos` Get a list of Todos
     
     Payload will look like this
     
     ```
      [
         {
            "id": "84569ae0-bc41-11e6-a4a6-cec0c932ce01",
            "name": "Todo name",
            "created": "2016-12-07 05:42:29.809"
         }
      ]
     ```
     
 * `GET /todos/:todo_id` Get todo tasks
     
     Payload will look like this
     
     ```
      [
         {
            "id": "84569ae0-bc41-11e6-a4a6-cec0c932ce03",
            "name": "task name",
            "description": "Description of the task",
            "status": "NOT_DONE",
            "created": "2016-12-07 05:42:29.809"
         }
      ]
     ```
     
 * `GET /todos/:todo_id/tasks/:task_id` Get a todo task
 
     Payload will look like this
     
     ```
      {
          "id": "84569ae0-bc41-11e6-a4a6-cec0c932ce03",
          "name": "task name",
          "description": "Description of the task",
          "status": "NOT_DONE",
          "created": "2016-12-07 05:42:29.809"
      }
     ```
     
  * `GET /todos/:todo_id/tasks/done` Get a list of task that are done  
     
     Payload will look like this
     
     ```
      [
         {
            "id": "84569ae0-bc41-11e6-a4a6-cec0c932ce03",
            "name": "task name",
            "description": "Description of the task",
            "status": "DONE",
            "created": "2016-12-07 05:42:29.809"
         }
      ]
     ```
     
  * `GET /todos/:todo_id/tasks/not-done` Get a list of task that are not done  
     
     Payload will look like this
     
     ```
      [
         {
            "id": "84569ae0-bc41-11e6-a4a6-cec0c932ce03",
            "name": "task name",
            "description": "Description of the task",
            "status": "NOT_DONE",
            "created": "2016-12-07 05:42:29.809"
          }
      ]

     ```
 
 * `POST /todos` Create a new todo
     * data
     ```
     {
       "name": "Name of the todo"
     }
     ```
     
     Payload will look like this
     
     ```
      {
          "id": "84569ae0-bc41-11e6-a4a6-cec0c932ce03",
          "name": "Todo name",
          "created": "2016-12-07 05:42:29.809"
      }
     ```
     
 * `POST /todos/:todo_id/tasks` Create a new task for the given todo
     * data
     ```
     {
       "name": "Name of the task",
       "description": "description of the task"
     }
     ```
     
     Payload will look like this
     
     ```
      {
          "id": "84569ae0-bc41-11e6-a4a6-cec0c932ce03",
          "name": "Task name",
          "description": "Description of the task",
          "status": "NOT_DONE",
          "created": "2016-12-07 05:42:29.809"
      }
     ```
   
  * `PUT /todos/:todo_id/tasks/:task_id` Update task
     * data
     ```
     {
         "name": "Name of the task",
         "description": "description of the task",
         "status": "DONE"
     }
     ```
     
     Payload will look like this
     
     ```
      {
          "id": "84569ae0-bc41-11e6-a4a6-cec0c932ce03",
          "name": "Task name",
          "description": "Description of the task",
          "status": "DONE",
          "created": "2016-12-07 05:42:29.809"
      }
     ```
     
 * `DELETE /todos/:todo_id` Remove a toto and releated task
     
     Payload will look like this
     
     ```
      {
      }
     ```
     
 * `DELETE /todos/:todo_id/tasks/:task_id` Remove task
     
     Payload will look like this
     
     ```
      {
      }
     ```

### Implementation instructions
For the fun, we want you to implement this challenge by using the following instructions. 

 * Java 8 (lambda, steam api etcetc).
 * [Guice](https://github.com/google/guice) for Dependency Injection.
 * Jetty & JAX-RS for web framework.
 * Jackson for JSON serialization/deserialization.

## Evaluation (in order of importance)

 1. Code organization (reusable code, good coding practice, project architecture)
 2. Completeness of the project
 3. Code readability
 4. Test coverage
