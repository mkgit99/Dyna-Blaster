package client;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * This class contain attributes of game.
 */
public class GameAttributes {
    /**
     * Player's score
     */
    private int score;
    /**
     * Player's health
     */
    private int health;
    /**
     * Variable which contains amount of bombs
     */
    private int max_amount_of_bomb;
    /**
     * Variable which represents max time in game.
     */
    private int max_game_time;
    /**
     * Player's nick.
     */
    private String playerNick;
    /**
     * Variable which contains multiplier depending on game difficulty.
     */
    private double multiplier_difficulty;
    /**
     * Path
     */
    private String path;

    FileReader reader;
    Properties properties;

    /**
     * Class constructor.
     * @throws IOException
     */
    public GameAttributes() throws IOException {


    }

    /**
     * This method sets attributes of game as specific variables.
     * @throws IOException
     */
    public void setgameattributes(String path) throws IOException {
        reader = new FileReader(path);
        properties = new Properties();
        properties.load(reader);
        score = Integer.parseInt(properties.getProperty("score"));
        health = Integer.parseInt(properties.getProperty("health"));
        max_amount_of_bomb = (int)(Integer.parseInt(properties.getProperty("max_amount_of_bomb"))/multiplier_difficulty);
        max_game_time = Integer.parseInt(properties.getProperty("max_game_time"));
    }

    /**
     * This method updates game attributes per unit of time.
     */
    public void tick() {
        max_game_time=max_game_time-1;
    }

    /**
     * This method draws information about health, time, score and how many bombs we have.
     * @param g2 Variable of type Graphics2D.
     */
    public void render(Graphics2D g2) {
        Font fnt0 = new Font("Serif", Font.BOLD,300);
        g2.setFont(fnt0);
        g2.setColor(Color.cyan);
        g2.drawString("Health: "+ (int)(health),200,300);
        g2.drawString("Bomb: "+ max_amount_of_bomb,1700,300);
        g2.drawString("Time: "+ (int)max_game_time/60/60+":"+(int)(max_game_time/60-60*(int)(max_game_time/60/60)),3200,300);
        g2.drawString("Score: "+ score,5000,300);
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }

    public int getMax_amount_of_bomb() {
        return max_amount_of_bomb;
    }
    public void setMax_amount_of_bomb(int max_amount_of_bomb) {
        this.max_amount_of_bomb = max_amount_of_bomb;
    }

    public int getMax_game_time() {
        return max_game_time;
    }
    public void setMax_game_time(int max_game_time) {
        this.max_game_time = max_game_time;
    }

    public String getPlayerNick() { return playerNick; }
    public void setPlayerNick(String playerNick) { this.playerNick = playerNick; }

    /**
     * This method gets multiplier, depending on the difficulty of the level.
     * @return Multiplier as double.
     */
    public double getMultiplier_difficulty() { return multiplier_difficulty; }

    /**
     * This method sets multiplier, depending on the difficulty of the level.
     * @param difficulty String which represents name of level difficulty.
     */
    public void setMultiplier_difficulty(String difficulty) {
        switch (difficulty){
            case "Easy":
                multiplier_difficulty=1;
                break;
            case "Medium":
                multiplier_difficulty=1.25;
                break;
            case "Hard":
                multiplier_difficulty=1.5;
                break;
            default:
                multiplier_difficulty=0;
        }
    }
}
