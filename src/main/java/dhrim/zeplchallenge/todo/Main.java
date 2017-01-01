package dhrim.zeplchallenge.todo;

/**
 * Main start class.
 *
 * Port could be configured with option '-p' or default port 8080 is used.
 *
 */
public class Main {

    public static final int DEFAULT_PORT = 8080;
    private static final String OPTIONS_PORT = "-p";

    public static void main(String[] args) throws Exception {

        int port = parsePort(args);

        TodoServer todoServer = TodoServer.getInstance();

        try {
            todoServer.start(port);
            todoServer.join();
        } finally {
            todoServer.shutdown();
        }

    }

    private static int parsePort(String[] args) {
        if(args.length==0) { return DEFAULT_PORT; }
        if(args.length==2 && OPTIONS_PORT.equals(args[0])) {
            try {
                return Integer.parseInt(args[1]);
            } catch(NumberFormatException e) {
                // ignore
            }
        }
        showUsage();
        System.exit(1);
        return 0;
    }

    private static void showUsage() {
        System.out.println("USAGE");
        System.out.println("    java "+Main.class.getName()+" : with default port "+DEFAULT_PORT);
        System.out.println("    java "+Main.class.getName()+" "+OPTIONS_PORT+" port");
    }

}
