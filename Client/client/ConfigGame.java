package client;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * This class contains parameters to main class Game.
 */
public class ConfigGame {
    private int number_of_levels;

    //width and height of frame game;
    private int width , height;
    FileReader reader;
    Properties properties;

    /**
     * This class load and parse parameters from file .properties.
     * @param path Path to file .properties.
     * @throws IOException
     */
    public ConfigGame(String path) throws IOException {

    reader=new FileReader(path);
        properties=new Properties();
        properties.load(reader);
        number_of_levels=Integer.parseInt(properties.getProperty("number_of_levels"));
        width=Integer.parseInt(properties.getProperty("with"));
        height=Integer.parseInt(properties.getProperty("height"));
    }

    public int getNumber_of_levels() {
        return number_of_levels;
    }

    public void setNumber_of_levels(int number_of_levels) {
        this.number_of_levels = number_of_levels;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }



}
