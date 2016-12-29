package dhrim.zeplchallenge.todo;

/**
 * TODO : describe
 */
public class Main {

    public static final int DEFAULT_PORT = 2222;

    public static void main(String[] args) throws Exception {

        // TODO : read from args
        int port = DEFAULT_PORT;

        TodoServer todoServer = TodoServer.getInstance();

        try {
            todoServer.start(port);
            todoServer.join();
        } finally {
            todoServer.shutdown();
        }

    }

}
