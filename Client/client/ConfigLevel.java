package client;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
/**
 * This class contains parameters to levels.
 */
public class ConfigLevel  {
    FileReader reader;
    String name_level;
    Properties p;
    private int number_of_boxs_width ;
    private int number_of_boxs_height;

    /**
     * 2D table includes numbers which represents objects
     */
    int  [][] full_map_int;



    /**
     * This class loads and parses parameters from file .properties.
     * @param path Path to file .properties.
     * @throws IOException
     */
    public ConfigLevel(String path) throws IOException {
        reader=new FileReader(path);
        p=new Properties();
        p.load(reader);
        String looks_of_map;
        number_of_boxs_width=Integer.parseInt(p.getProperty("number_of_boxs_width"));
        number_of_boxs_height=Integer.parseInt(p.getProperty("number_of_boxs_height"));
        int  [][] full_map_int_temp= new int[number_of_boxs_width][number_of_boxs_height];
        name_level=p.getProperty("name_level");
        looks_of_map=p.getProperty("looks_of_map");
        String  [][] full_map_string = new String[number_of_boxs_width][number_of_boxs_height];
        String[] prop = looks_of_map.split(";");
        for(int i=0;i<prop.length;i++) {

            full_map_string[i]=prop[i].split(",");
            for(int j=0;j<full_map_string[i].length;j++) {
                full_map_int_temp[i][j]=Integer.parseInt(full_map_string[i][j]);

            }

        }
        full_map_int=full_map_int_temp;
    }

    public String getName_level() {
        return name_level;
    }
    public void setName_level(String name_level) {
        this.name_level = name_level;
    }

    public int[][] getFull_map_int() {
        return full_map_int;
    }
    public void setFull_map_int(int[][] full_map_int) {
        this.full_map_int = full_map_int;
    }

    public int getNumber_of_boxs_width() {
        return number_of_boxs_width;
    }
    public void setNumber_of_boxs_width(int number_of_box_width) {
        this.number_of_boxs_width = number_of_box_width;
    }

    public int getNumber_of_boxs_height() {
        return number_of_boxs_height;
    }
    public void setNumber_of_boxs_height(int number_of_box_height) {
        this.number_of_boxs_height = number_of_box_height;
    }
}
