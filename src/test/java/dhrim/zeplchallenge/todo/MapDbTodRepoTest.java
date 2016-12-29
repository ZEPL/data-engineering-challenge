package dhrim.zeplchallenge.todo;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MapDbTodRepoTest {

    private MapDbTodoRepo repo = new MapDbTodoRepo();

    @Before
    public void before() {
        repo.clear();
    }


    @Test
    public void test_createTodo_success () {

        // GIVEN
        Todo orgTodo = new TodoBuilder().id("id1").name("name1").build();

        {
            // WHEN
            Todo actualTodo = repo.saveOrUpdate(orgTodo);

            // THEN
            assertEquals(orgTodo, actualTodo);
        }


        {
            // WHEN
            Todo actualTodo = repo.getTodo(orgTodo.getId());

            // THEN
            assertEquals(orgTodo, actualTodo);
        }

    }


}
