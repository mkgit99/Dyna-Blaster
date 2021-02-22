package client;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * This class describes bomb which player is planting during a game.
 * Handler after using space key on keyboard.
 */
public class Bomb extends GameObject {
    /**
     * Represents time after which the bomb explodes.
     */
    private ConfigBomb configBomb;
    private Handler handler;
    private BufferedImage fire;


    /**
     * Bomb constructor
     * @param x number of pixels along the horizontal axis
     * @param y number of pixels along the vertical axis
     * @param id object id
     * @param image bomb image which is loaded to the object
     * @param handler container to storage this object
     * @param fire fire image which is loaded after bomb explosion
     */
    public Bomb(int x, int y, Id_object id, BufferedImage image,Handler handler,BufferedImage fire) throws IOException {

        super(x, y, id, image);
        this.handler=handler;
        this.fire=fire;
        configBomb= new ConfigBomb("Properties/ConfigBomb.properties");
    }

    /**
     * This method updates bomb's state per unit of time.
     */
    public void tick() throws IOException {
        if(configBomb.getTime_to_explode() ==0)
        {
            handler.addObject(new Fire(x,y,Id_object.Fire,fire,handler));
            handler.removeObject(this);

        }
        configBomb.setTime_to_explode(configBomb.getTime_to_explode()-1) ;

    }

    /**
     * This method draws bomb on game board.
     * @param g2
     */
    public void render(Graphics2D g2) {
        g2.drawImage(getImage(),getX(),getY(),null);
    }

    /**
     * This method returns a rectangle with position bounds (hitbox).
     * @return
     */
    public Rectangle getBounds() {
        return null;
    }
}
