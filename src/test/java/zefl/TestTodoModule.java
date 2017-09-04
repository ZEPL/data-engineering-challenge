package zefl;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;

import zefl.dao.TodoDAO;
import zefl.dao.TodoMemDAOImpl;
import zefl.service.TodoService;
import zefl.service.TodoServiceImpl;

public class TestTodoModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(TodoResource.class);
        bind(TodoService.class).to(TodoServiceImpl.class);
        bind(TodoDAO.class).to(TodoMemDAOImpl.class);
    }

    @Provides
    @Named("content")
    public String provideContent() {
        return "OK";
    }
}