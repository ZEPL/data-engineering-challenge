package zefl;

public class TestTodoResource extends TodoResource {
    //static TodoDAO dao = new TodoMemDAOImpl();
    public TestTodoResource() {
        setTodoService(new TodoServiceImpl());
        todoService.setTodoDao(new TodoMemDAOImpl());
    }
    public static void resetData() {
        TodoMemDAOImpl.resetData();
    }
}
