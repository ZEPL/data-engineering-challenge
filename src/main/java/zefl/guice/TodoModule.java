package zefl.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

import zefl.TodoResource;
import zefl.dao.TodoDAO;
import zefl.dao.TodoSimplePersistentDaoImpl;
import zefl.service.TodoService;
import zefl.service.TodoServiceImpl;

public class TodoModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(TodoResource.class);
        bind(TodoService.class).to(TodoServiceImpl.class);
        bind(TodoDAO.class).to(TodoSimplePersistentDaoImpl.class);
        bindConstant().annotatedWith(Names.named("dbFilename")).to("todo.db");
    }

    @Provides
    @Named("content")
    public String provideContent() {
        return "OK";
    }
}