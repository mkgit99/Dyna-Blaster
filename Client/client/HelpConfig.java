package client;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * This class is responsible for Help text in application
 */
public class HelpConfig {
    private String[] command;

    /**
     * This method gets help text from .txt file.
     * @return Command in string.
     */
    public String []gethelpstring() {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader("Properties/HelpConfig.txt"));
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                sb.append(currentLine);
                sb.append("@");
            }
        } catch (Exception e) {
            System.out.println("I can't open this file");
        }

        String commands = sb.toString();

        command = commands.split("@");

        return command;

    }

    /**
     * this method return length of commands
     * @return length of commands
     */
    public int get_length_of_commands(){
        return command.length;
    }
}
