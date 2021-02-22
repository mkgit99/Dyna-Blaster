package client;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * This class is abstract class of objects which  will be included to container in Handler.
 */
public abstract class GameObject {
    /**
     * Number of pixels along the horizontal axis.
     */
    protected int x;
    /**
     * Number of pixels along the vertical axis.
     */
    protected int y;
    /**
     * Object id
     */
    protected Id_object id;
    /**
     * Bomb image which is loaded to the object.
     */
    protected BufferedImage image;
    /**
     * Velocity of moving objects vertical and horizontal.
     */
    protected int speedX,speedY;

    /**
     * GameObject constructor.
     * @param x Number of pixels along the horizontal axis.
     * @param y Number of pixels along the vertical axis.
     * @param id Object id.
     * @param image Bomb image which is loaded to the object.
     */
    public GameObject(int x, int y, Id_object id, BufferedImage image)
    {
        this.x=x;
        this.y=y;
        this.id=id;
        this.image=image;
    }

    /**
     * This abstract method updates state per unit of time.
     */
    public abstract void tick() throws IOException;

    /**
     * This abstract method draws image of object.
     * @param g2 Variable of type Graphics2D.
     */
    public abstract void render(Graphics2D g2);

    /**
     * This method returns a rectangle with position bounds (hitbox).
     * @return
     */
    public abstract Rectangle getBounds();

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public Id_object getId() {
        return id;
    }
    public void setId(Id_object id) {
        this.id = id;
    }

    public int getSpeedX() {
        return speedX;
    }
    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }
    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public BufferedImage getImage() {
        return image;
    }
    public void setImage(BufferedImage image) {
        this.image = image;
    }
}