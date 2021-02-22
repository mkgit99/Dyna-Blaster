package client;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class describes board ground, where player can walk during game.
 */
public class Grass extends GameObject {
    /**
     * Grass constructor
     * @param x number of pixels along the horizontal axis
     * @param y number of pixels along the vertical axis
     * @param id object id
     * @param image grass image which is loaded to the object
     */
    public Grass(int x, int y, Id_object id, BufferedImage image) {
        super(x, y, id, image);
    }

    public void tick() {
    }

    /**
     * This method draws grass on game board.
     * @param g2 Variable of type Graphics2D.
     */
    public void render(Graphics2D g2) {
        g2.drawImage(image,x,y,null);
    }

    /**
     * This method returns a rectangle with position bounds.
     * @return
     */
    public Rectangle getBounds() {
        return null;
    }
}
