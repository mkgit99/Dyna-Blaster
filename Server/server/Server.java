package server;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Main server's class.
 */
public class Server {

    /**
     * Server's port.
     */
    private int serverPort;

    /**
     * The method of setting the port and starting the server.
     * @throws IOException
     */
    public void runserver() throws IOException {
        try {
			FileReader reader=new FileReader("Properties/ConfigPort.properties");
            Properties p=new Properties();
            p.load(reader);
        serverPort=Integer.parseInt(p.getProperty("serverport"));
            ServerSocket serverSocket = new ServerSocket(serverPort);
            System.out.println("Server is working.Ready to command execution");
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ServerThread(socket)).start();
            }
        }
        catch (Exception e){
            System.out.println("Unfortunately, it was not possible to connect to server");
            System.err.println(e);
        }
    }
}
