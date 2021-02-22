import server.Server;

import java.io.IOException;

/**
 * This class runs application's server.
 */
public class Main {
    public static void  main(String[] args) throws IOException {
        new Server().runserver();
    }
}
