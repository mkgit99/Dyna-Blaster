package client;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * This class contains parameters to class Fire.
 */
public class ConfigFire {
    private int time_to_damage;

    FileReader reader;
    Properties properties;

    /**
     * This class load and parse parameters from file .properties.
     * @param path Path to file .properties.
     * @throws IOException
     */
    public ConfigFire(String path) throws IOException {

        reader = new FileReader(path);
        properties = new Properties();
        properties.load(reader);
        time_to_damage = Integer.parseInt(properties.getProperty("time_to_damage"));

    }

    public int getTime_to_damage() {
        return time_to_damage;
    }

    public void setTime_to_damage(int time_to_damage) {
        this.time_to_damage = time_to_damage;
    }
}

