package zefl;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TodoMemDAOImplTest {
    TodoDAO dao;

    @Before
    public void setUp() throws Exception {
        dao = new TodoMemDAOImpl();
    }

    @Test
    public void testInsert1Item() throws Exception {
        Todo todo = new Todo("1","A","bb");

        assertTrue(dao.upsertTodo(todo));

        List<Todo> expected = new ArrayList<>();
        expected.add(new Todo("1", "A", "bb"));
        checkResultByFindAll(expected);
    }

    @Test
    public void testInsert2Items() throws Exception {
        assertTrue(dao.upsertTodo(new Todo("1","A","bb")));
        assertTrue(dao.upsertTodo(new Todo("2","B","cc")));

        List<Todo> expected = new ArrayList<>();
        expected.add(new Todo("1", "A", "bb"));
        expected.add(new Todo("2", "B", "cc"));
        checkResultByFindAll(expected);
    }

    @Test
    public void testInsert2ItemsWithSameId() throws Exception {
        assertTrue(dao.upsertTodo(new Todo("1","A","bb")));
        assertTrue(dao.upsertTodo(new Todo("1","B","cc")));

        List<Todo> expected = new ArrayList<>();
        expected.add(new Todo("1", "B", "cc"));
        checkResultByFindAll(expected);
    }

    @Test
    public void testFindAllWithEmptyResult() throws Exception {
        checkResultByFindAll(new ArrayList<>());
    }

    private void checkResultByFindAll(List<Todo> expected) {
        List<Todo> result = dao.findAll();
        assertEquals(expected, result);
    }

    @Test
    public void testGetEmptyTask() throws Exception {
        List<Task> result = dao.findTasksByTodoId("1");
        assertEquals(null, result);
    }

    @Test
    public void testInsertAndGet1Task() throws Exception {
        assertTrue(dao.upsertTodo(new Todo("1","A","bb")));
        assertTrue(dao.upsertTask("1", new Task("T1","todo", "Implement Todo", "NOT_DONE", "10")));
        List<Task> result = dao.findTasksByTodoId("1");
        List<Task> expected = new ArrayList<>();
        expected.add(new Task("T1","todo", "Implement Todo", "NOT_DONE", "10"));
        assertEquals(expected, result);
    }

    @Test
    public void testInsert2TasksAndGet1Task() throws Exception {
        assertTrue(dao.upsertTodo(new Todo("1","A","bb")));
        assertTrue(dao.upsertTask("1", new Task("T1","todo", "Implement Todo", "NOT_DONE", "10")));
        assertTrue(dao.upsertTask("1", new Task("T2","todo", "Implement Todo2", "NOT_DONE", "10")));
        List<Task> result = dao.findTasksByTodoId("1");
        List<Task> expected = new ArrayList<>();
        expected.add(new Task("T1","todo", "Implement Todo", "NOT_DONE", "10"));
        expected.add(new Task("T2","todo", "Implement Todo2", "NOT_DONE", "10"));
        assertEquals(expected, result);
    }
}