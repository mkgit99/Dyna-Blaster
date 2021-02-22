package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;

/**
 * This class is responsible for executing commands sent to the server
 */
public final class ServerCommands {

    /**
     * This method handles server requests.
     * @param command Command
     * @return Server's message.
     * @throws IOException
     */
    public static String serveranswer(String command) throws IOException {
        String serverms;
		String command2=command;
		String []commands=command.split("-");
		command=commands[0];
        switch (command){
            case "GET_SETTINGS":
                serverms=getString("Properties/ConfigGame.properties");
                break;
            case "GET_BEST_SCORES":
                serverms=getBestSores();
                break;
			case "GET_LEVEL":
                serverms=getLevel(command2.replace("GET_LEVEL-",""));
                break;
            case "SEND_SCORE":
                serverms=Send_Score(command2.replace("SEND_SCORE-",""));
                break;
            case "GET_GAME_ATTRIBUTES":
                serverms=getString("Properties/ConfigGameAttributes.properties");
                break;
            case "GET_BOMB_CONFIG":
                serverms=getString("Properties/ConfigBomb.properties");
                break;
            case "GET_FIRE_CONFIG":
                serverms=getString("Properties/ConfigFire.properties");
                break;
            case "GET_HELP":
                serverms=getHelpConfig();
                break;
            default:
                serverms="INVALID_COMMAND";
        }
        return serverms;
    }

    /**
     * This method gets HelpConfig text by server to client.
     * @return Server's message.
     */
    private static String getHelpConfig() {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader("Properties/HelpConfig.txt"));
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                sb.append(currentLine);
                sb.append("@");
            }
        }
        catch (Exception e){
            System.out.println("I can't open this file");

        }

        return sb.toString();
    }

    /**
     * This method gets string for choosen path.
     * @param path Choosen path.
     * @return Server's message.
     */
    private static String getString(String path){
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                sb.append(currentLine);
                sb.append(" ");
            }
        }
        catch (Exception e){
            System.out.println("I can't open this file");

        }

        return sb.toString();
    }

    /**
     * This method supports retrieving the list of best scores.
     * @return Server's message.
     */
    private static String getBestSores(){
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader("Properties/ConfigBestScores.properties"));
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                sb.append(currentLine);
                sb.append(" ");
            }
        }
        catch (Exception e){
            System.out.println("I can't open this file");

        }

        return sb.toString();
    }

    /**
     * This method gets given levels.
     * @param level Number of level.
     * @return Server's message.
     */
	private static String getLevel(String level){
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader("Properties/ConfigLevel"+level+".properties"));
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                sb.append(currentLine);
                sb.append(" ");
            }
        }
        catch (Exception e){
            System.out.println("I can't open this file");

        }

        return sb.toString();
    }

    /**
     * This method sends score from server to client.
     * @param path Given path.
     * @return Server's message.
     * @throws IOException
     */
	private static String Send_Score(String path) throws IOException {

        ArrayList<User_and_Score> scorelist;
        scorelist = new ArrayList<>();
        scorelist=loadBestScores(scorelist);
        String []fromclient=path.split("-");
        scorelist.add(new User_and_Score(fromclient[0],Integer.parseInt(fromclient[1])));

        sortScores(scorelist);
        saveScores(scorelist);


        return "Your score is save";
    }

    /**
     * This method loads best scores from properties file.
     * @param scorelist List of scores.
     * @return Server's message.
     * @throws IOException
     */
    private static ArrayList<User_and_Score> loadBestScores(ArrayList<User_and_Score> scorelist) throws IOException {
        FileReader reader = new FileReader("Properties/ConfigBestScores.properties");
        Properties p = new Properties();
        p.load(reader);
        String nick_and_score = null;
        for (int j = 1; j <= 10; j++) {
            nick_and_score = p.getProperty("place" + j);
            String[] pair = nick_and_score.split(",");
                scorelist.add(new User_and_Score(pair[0],Integer.parseInt(pair[1])));
        }
        return scorelist;
    }

    /**
     * This method sorts list from best to worst scores.
     * @param scorelist List of scores.
     */
    private static void sortScores(ArrayList<User_and_Score> scorelist){
        Collections.sort(scorelist, (user1, user2) -> {
            if (user1.getScore() < user2.getScore()) {
                return 1;
            }
            if (user1.getScore() > user2.getScore()) {
                return -1;
            } else
                return 0;
        });

    }

    /**
     * This method saves scores to properties file.
     * @param scorelist List of scores.
     */
    private static void saveScores(ArrayList<User_and_Score> scorelist) {
        try{
        PrintWriter out = new PrintWriter("Properties/ConfigBestScores.properties");
        for(int i=0;i<10;i++) {
            int j=i+1;
            out.println("place"+j+"="+scorelist.get(i).getNick()+","+scorelist.get(i).getScore());
        }
        out.close();
    }catch (IOException e){
        System.out.println("Access denied");
        System.out.println(e);
    }
    }
}
