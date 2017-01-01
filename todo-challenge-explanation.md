Todo Challenge Explanation
=========================

### Build and Run
```
$ mvn compile
$ mvn exec:java -Dexec.mainClass=dhrim.zeplchallenge.todo.Main -Dexec.args="-p 8080"
```

### Architecture
Resource -->  Service --> Repo
  - TodoRestApi : Jersey resource file. endpoint is defined. call TodoService
  - TodoService : treat business logic. use TodoRepo for storing
  - TodoRepo : repository interface
  -- AbstractMapBaseTodoRepo : abstract repo class which use map
  -- MapDbTodoRepo : concret repo class which extends AbstractMapBaseTodoRepo.
  - TodoServer : Server Main. provide getInstance() which create instance by Guice

### Repository
Use MapDb(http://www.mapdb.org/) for storing which use local file for persistence.

### Limitation
- Didn't consider expandability. It means only valid for single server beacuse of repository
- Didn't consider performance
- Didn't treat detail exception case. Not defined in requirement as like duplicated name.

### TestCase
3 test case exist.
- TodoRestApiTest : call REST api by http request. It use mock Repo instead of real Repo.
- IntegrationTest : test with real Repo.
- MapDbTodoRepoTest : test working with MapDb.

