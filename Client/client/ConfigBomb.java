package client;

import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * This class contains parameters to class Bomb.
 */
public class ConfigBomb {
    /**
     * This variable represents time after which bomb explodes.
     */
    private int time_to_explode;
    FileReader reader;
    Properties properties;

    /**
     * This class loads and parses parameters from properties file.
     * @param path Path to file .properties.
     * @throws IOException
     */
    public ConfigBomb(String path) throws IOException {

        reader = new FileReader(path);
        properties = new Properties();
        properties.load(reader);
        time_to_explode = Integer.parseInt(properties.getProperty("time_to_explode"));


    }

    public int getTime_to_explode() {
        return time_to_explode;
    }

    public void setTime_to_explode(int time_to_explode) {
        this.time_to_explode = time_to_explode;
    }

}




