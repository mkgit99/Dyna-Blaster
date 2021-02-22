package client;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * This class is responsible for players' scores.
 */
public class Scores {

    FileReader reader;
    String [] nick_names=new String[10];
    Properties p;
    int [] scores=new int[10];

    /**
     * Class constructor read scores and nicks from file
     * @param path Given path
     * @throws IOException
     */
    public Scores(String path) throws IOException {
        reader = new FileReader(path);
        p = new Properties();
        p.load(reader);
        String nick_and_score = null;
        for (int j = 1; j <= 10; j++) {
            nick_and_score = p.getProperty("place" + j);
            String[] pair = nick_and_score.split(",");
            for (int i = 0; i < pair.length; i++) {
                nick_names[j - 1] = pair[0];
                scores[j - 1] = Integer.parseInt(pair[1]);
            }
        }
    }

    /**
     * Method returned nick and score from requested place
     * @param i
     * @return
     */
    public String getScores(int i) {
        return nick_names[i]+"   "+scores[i];
    }
}
