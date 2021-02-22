package client;

import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;

/**
 * This class is container to objects type GameObjects.
 */
public class Handler {
    /**
     * Container to objects type GameObjects.
     */
    public LinkedList<GameObject> object =new LinkedList<GameObject>();
    public GameAttributes gameAttributes;
    public Scores scores;
    private Game game;

    /**
     * Handler constructor
     * @param game Object of class Game.
     * @throws IOException
     */
    public Handler(Game game) throws IOException {
      gameAttributes=new GameAttributes();
        this.game=game;
    }

    /**
     *  This method updates state all objects from container per unit of time.
    */
    public void tick() throws IOException {
        for (int i=0;i<object.size();i++){
            GameObject tempObject=object.get(i);
            tempObject.tick();
        }
        gameAttributes.tick();
        if(gameAttributes.getHealth()<=0 ||gameAttributes.getMax_game_time()<=0){

            gameAttributes.setHealth(0);
            game.gameState= Game.AppState.LOSE;
        }
    }

    /**
     *  This method draws all objects from container on screen.
     */
    public void render(Graphics2D g2){
        for (int i=0;i<object.size();i++){
            GameObject tempObject=object.get(i);
            tempObject.render(g2);
        }

        gameAttributes.render(g2);

        if(game.gameState == Game.AppState.PAUSE){
            Font fnt0 = new Font("Serif", Font.BOLD, 1000);
            g2.setFont(fnt0);
            g2.setColor(Color.cyan);
            g2.drawString("PAUSE", 2000, 3900);
        }
        if(game.gameState == Game.AppState.WIN){
            Font fnt0 = new Font("Serif", Font.BOLD, 1000);
            g2.setFont(fnt0);
            g2.setColor(Color.cyan);
            g2.drawString("YOU WIN", 1000, 3900);
            Font fnt1 = new Font("Serif", Font.BOLD, 400);
            g2.setFont(fnt1);
            g2.drawString(gameAttributes.getPlayerNick()+" your score is: "+gameAttributes.getScore(),1000,5000);
        }
        if(game.gameState == Game.AppState.LOSE){
            Font fnt0 = new Font("Serif", Font.BOLD, 1000);
            g2.setFont(fnt0);
            g2.setColor(Color.cyan);
            g2.drawString("YOU LOSE", 1000, 3900);
        }
        if(game.gameState == Game.AppState.WIN ||game.gameState == Game.AppState.LOSE){
            Font fnt0 = new Font("Serif", Font.BOLD, 400);
            g2.setFont(fnt0);
            g2.setColor(Color.cyan);
            g2.drawString("Press ENTER to back to menu", 1000, 5900);
        }
    }

    /**
     * This method allows to add objects.
     * @param object
     */
    public void addObject(GameObject object){
        this.object.add(object);
    }

    /**
     * This method allows to remove objects.
     * @param object
     */
    public void removeObject(GameObject object){
        this.object.remove(object);
    }
}
