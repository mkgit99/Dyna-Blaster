import client.Game;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Properties;

/**
 * This class runs socket server.
 */
public class Main {
    private static String IPAddress;
    private static int Port;
    private static Socket socket;

    /**
     * This is main method
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        connectToServer();
       run();

    }

    /**
     * This method starts socket server
     */
    private static void connectToServer() {
        readport();
        try {
            socket = new Socket(IPAddress, Port);
        }
        catch (Exception e) {
            System.out.println("Connection could not be opened..");
            System.out.println("error: "+e);
        }

}

    /**
     * This method read properties file and set port and ip address
     */
    private static void readport() {
    try {
        FileReader reader = new FileReader("Properties/ConfigPort.properties");
        Properties p = new Properties();
        p.load(reader);
        Port = Integer.parseInt(p.getProperty("Port"));
        IPAddress = p.getProperty("IPAddress");

    } catch (Exception e) {
        System.out.println("Unfortunately, it was not possible load this file");
        System.err.println(e);
    }
}

    /**
     * this methos runs game online or offline
     * @throws IOException
     */
    public static void run()  throws IOException {

        if(socket!=null) {
            getsettings();
            Game game=new Game(socket);
        }
        else {
            Object[] options={
                    "PLAY","EXIT"
            };
            switch(JOptionPane.showOptionDialog(null, "Not connected to server. Do you want to play offline?", "Access denied", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0])){
                case JOptionPane.YES_OPTION:
                    Game game=new Game(socket);
                    break;

                case JOptionPane.WARNING_MESSAGE:
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }

    }

    /**
     * This method send request get setings to server and save receive string to .properties file.
     */
    public static void getsettings(){
        try{

            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            pw.println("GET_SETTINGS");
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String settings = br.readLine();
            String []setting =settings.split(" ");
            PrintWriter out = new PrintWriter("Properties/ConfigGame.properties");
            for(int i=0;i<setting.length;i++) {
                out.println(setting[i]);
            }
            out.close();
        }catch (IOException e){
            System.out.println("Access denied");
            System.out.println(e);
        }
    }
}

