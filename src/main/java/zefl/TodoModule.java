package zefl;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;

public class TodoModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(TodoResource.class);
        bind(TodoService.class).to(TodoServiceImpl.class);
    }

    @Provides
    @Named("content")
    public String provideContent() {
        return "Got it!";
    }
}