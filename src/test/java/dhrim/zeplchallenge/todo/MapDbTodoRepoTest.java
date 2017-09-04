package dhrim.zeplchallenge.todo;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MapDbTodoRepoTest {

    private MapDbTodoRepo repo = new MapDbTodoRepo();

    @Before
    public void before() {
        repo.clear();
    }


    @Test
    public void test_saveOrUpdate_and_getTodo () {

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
