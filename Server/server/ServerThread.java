package server;

import java.io.*;
import java.net.Socket;

/**
 * This class represents server's thread.
 */
public class ServerThread  implements Runnable{

    /**
     * Socket
     */
    private Socket socket;

    /**
     * Class constructor.
     * @param socket
     */
    public ServerThread(Socket socket){
this.socket=socket;
    }

    /**
     * This method handles all events between the client and server.
     */
    public void run() {
        try{
            while (true) {
                InputStream is = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                OutputStream os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os, true);
             String serverMessage;
             String clientMessage;
                clientMessage = br.readLine();
				if(clientMessage!=null) {
                System.out.println("FROM CLIENT: " + clientMessage);
                serverMessage = ServerCommands.serveranswer(clientMessage);
              pw.println(serverMessage);
              pw.flush();
              System.out.println("TO CLIENT: " + serverMessage);
            }
			}
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }

    }
}
