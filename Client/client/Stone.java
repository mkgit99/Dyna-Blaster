package client;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class describes indestructible board walls, through which player can't walk.
 */
public class Stone extends GameObject {

    /**
     * Stone constructor
     * @param x number of pixels along the horizontal axis
     * @param y number of pixels along the vertical axis
     * @param id object id
     * @param image stone image which is loaded to the object
     */
    public Stone(int x, int y, Id_object id, BufferedImage image) {
        super(x, y, id, image);
    }

    public void tick() {
    }

    /**
     * This method draws stone on game board.
     * @param g2
     */
    public void render(Graphics2D g2) {

        g2.drawImage(image,x,y,null);

    }

    /**
     * This method returns a rectangle with position bounds (hitbox).
     * @return
     */
    public Rectangle getBounds() {
            return new Rectangle(x,y,480,480);
    }
}
