package server;

/**
 * This class represents player's score like nick and number.
 */
public class User_and_Score{

    /**
     * Player's nick
     */
    private String nick;
    /**
     * Player's score
     */
    private int score;

    /**
     * Class constructor
     * @param nick Player's nick
     * @param score Player's score
     */
    public User_and_Score(String nick,int score){
        this.nick=nick;
        this.score=score;
    }

    /**
     * Nick's getter
     * @return Player's nick
     */
    public String getNick() {
        return nick;
    }

    /**
     * Score's getter
     * @return Player's score
     */
    public int getScore() {
        return score;
    }

}