package client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * This class takes from user, what he pressed in keyboard.
 */
public class KeyInput extends KeyAdapter {

    private Handler handler;
    private BufferedImage bomb;
    private BufferedImage fire;
    private Game game;

    /**
     * Constructor KeyInput
     * @param game
     * @param handler
     * @param bomb
     * @param fire
     */
    public KeyInput(Game game, Handler handler, BufferedImage bomb, BufferedImage fire) {
        this.game = game;
        this.handler = handler;
        this.bomb=bomb;
        this.fire=fire;
    }

    /**
     * This method check what pressed user it is used to move player(arrows on keyboard ) or add bomb (space on keyboard).
     * @param e Key pressed by the user.
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (game.gameState == Game.AppState.GAME) {
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempobject = handler.object.get(i);

                if (tempobject.getId() == Id_object.Player) {

                    if (key == KeyEvent.VK_RIGHT) tempobject.setSpeedX(20);
                    if (key == KeyEvent.VK_UP) tempobject.setSpeedY(-20);
                    if (key == KeyEvent.VK_LEFT) tempobject.setSpeedX(-20);
                    if (key == KeyEvent.VK_DOWN) tempobject.setSpeedY(20);
                    if (key == KeyEvent.VK_P) game.gameState = Game.AppState.PAUSE;

                    if( key == KeyEvent.VK_SPACE && handler.object.get(handler.object.size()-1).getId()!=Id_object.Bomb && handler.gameAttributes.getMax_amount_of_bomb()!=0) {

                        try {
                            handler.addObject(new Bomb(tempobject.getX(), tempobject.getY(), Id_object.Bomb, bomb,handler,fire));
                            handler.gameAttributes.setMax_amount_of_bomb(handler.gameAttributes.getMax_amount_of_bomb()-1);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
        else if (game.gameState == Game.AppState.PAUSE){
            if (key == KeyEvent.VK_P) game.gameState = Game.AppState.GAME;
        }
        else if (game.gameState== Game.AppState.LOSE||game.gameState== Game.AppState.WIN){
            if (key == KeyEvent.VK_ENTER) game.gameState = Game.AppState.GAMEEND;
        }
    }

    /**
     * This method check what pressed user it is used to stop move player(arrows on keyboard).
     * @param e Key released by the user.
     */
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (game.gameState == Game.AppState.GAME || game.gameState == Game.AppState.PAUSE) {
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempobject = handler.object.get(i);

                if (tempobject.getId() == Id_object.Player) {
                    if (key == KeyEvent.VK_RIGHT) tempobject.setSpeedX(0);
                    if (key == KeyEvent.VK_UP) tempobject.setSpeedY(0);
                    if (key == KeyEvent.VK_LEFT) tempobject.setSpeedX(0);
                    if (key == KeyEvent.VK_DOWN) tempobject.setSpeedY(0);
                }
            }
        }
    }
}
