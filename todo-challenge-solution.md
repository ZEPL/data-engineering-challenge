Solution for Software Engineer Challenge
===============================

## build & test
    mvn package

## run
    mvn exec:java -Dexec.mainClass="com.jihoon.app"

## configuration
###server port configuration
Default server port is 4000

    port=4000 ( /src/main/resources/application.conf )
    

###mongoDB configuration for application service
This is temporary web MongoDB hosting server config ( **Do not change** )

    dbserverurl=ds133358.mlab.com ( /src/main/resources/application.conf )
    dbserverport=33358
    databaseName=zepl
    dbuser=zepl
    dbpassword=zepl1234
    
    
###mongoDB configuration for testing
This is temporary web MongoDB hosting server config ( **Do not change** ) which tests Dao modules on Real MongoDB environment.

    dbserverurl=ds139278.mlab.com ( /src/test/resources/application.conf )
    dbserverport=39278
    databaseName=test_zepl
    dbuser=zepl
    dbpassword=zepl1234    
    
###logging config
This application uses logback library for logging.
Users can set the level of the this Restful application.
There are 4 levels like TRACE, DEBUG, INFO, WARN and ERROR.
Current Level is DEBUG.
    
    <root level="DEBUG"> ( /src/main/resources/logback.xml )

## Architecture overview
There are 5 modules and 1 main class.
 1. config module
 server's config ( port ), Database ( mongoDB ) and DI(Dependancy Injection) configuration.
 2. controller module
 TodoController define routes(path and http options) for the TodoServices, which calls the todo service module.
 3. dao module
 Dao classes implement database control and management of data.
 There are 2 Dao classes (Todo and Task)
 4. model module
 Model classes(Todo and Task) define the schema of data-set to be used in services and dao.
 5. service module
 Service classes define the logic of Todo Restful service.
 6. app class
 Main entry class which runs the Restful application server.
 
Feel free to email us at hsboykjh@gmail.com if you have any questions about the application.
