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

        assertTrue(dao.insertTodo(todo));

        List<Todo> expected = new ArrayList<>();
        expected.add(new Todo("1", "A", "bb"));
        checkResultByFindAll(expected);
    }

    @Test
    public void testInsert2Items() throws Exception {
        assertTrue(dao.insertTodo(new Todo("1","A","bb")));
        assertTrue(dao.insertTodo(new Todo("2","B","cc")));

        List<Todo> expected = new ArrayList<>();
        expected.add(new Todo("1", "A", "bb"));
        expected.add(new Todo("2", "B", "cc"));
        checkResultByFindAll(expected);
    }

    @Test
    public void testInsert2ItemsWithSameId() throws Exception {
        assertTrue(dao.insertTodo(new Todo("1","A","bb")));
        assertTrue(dao.insertTodo(new Todo("1","B","cc")));

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
}