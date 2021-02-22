package client;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

/**
 * This class describes  player .
 * Class is added to Handler.
 */
public class Player extends GameObject {

    private Handler handler;
    private Game game;
    private int time_of_get_hp=0;

    /**
     * Player Constructor
     * @param x number of pixels along the horizontal axis
     * @param y number of pixels along the vertical axis
     * @param id object id
     * @param image player image which is loaded to the object
     * @param handler container to storage this object
     */
    public Player(int x, int y, Id_object id, BufferedImage image, Handler handler, Game game) {
        super(x, y, id,image);
        this.handler=handler;
        this.game=game;
    }

    /**
     * This method updates player's position per unit of time. Also runs collision method.
     */
    public void tick()  {
        x+=speedX;
        y+=speedY;
        colision();
    }

    /**
     * Method checks if player is near stone or box plate, then makes it cant get through this.
     */
    private void colision() {
        for (int i=0;i<handler.object.size();i++){
            GameObject tempObject=handler.object.get(i);

            if(tempObject.getId()==Id_object.Stone || tempObject.getId()==Id_object.Box){
                if(getBounds_vertical().intersects(tempObject.getBounds())){
                    y+=speedY*-1;
                }
            }
            if(tempObject.getId()==Id_object.Stone || tempObject.getId()==Id_object.Box){
                if(getBounds_horizontal().intersects(tempObject.getBounds())){
                    x+=speedX*-1;
                }
            }
            if (tempObject.getId() == Id_object.Enemy) {
                if (getBoundsCircle().intersects(tempObject.getBounds())) {
                    if(time_of_get_hp==5) {
                        handler.gameAttributes.setHealth(handler.gameAttributes.getHealth() - (int) (1 * 4 * handler.gameAttributes.getMultiplier_difficulty()));
                        time_of_get_hp = 0;
                    }
                    else {
                        time_of_get_hp++;
                    }
                }
            }
            if(tempObject.getId()==Id_object.Portal) {
                if(getBoundsCircle().intersects(tempObject.getBounds())){
                    game.gameState= Game.AppState.NEXTLEVEL;
                }
            }
        }
    }

    /**
     * This method draws player on game board.
     * @param g2 Variable of type Graphics2D.
     */
    public void render(Graphics2D g2){
        g2.drawImage(image,x,y,null);
    }

    /**
     * This method returns an player as a rectangle with position bounds (hitbox).
     * @return
     */
    public Rectangle getBounds() {
        return new Rectangle(x+80,y+80,320,320);
    }

    /**
     * This method returns an player as a circle with position bounds (hitbox).
     * @return
     */
    public Ellipse2D.Double getBoundsCircle() {
        return new Ellipse2D.Double(x+130, y+130, 220, 220);
    }
    /**
     * These methods return rectangles with position bounds (hitbox).
     * Player range is like a small cross, so there have to be two methods with vertical and horizontal direction.
     * It is needed to move player smoothly
     * @return
     */
    public Rectangle getBounds_vertical() {
        return new Rectangle(x+150,y+130,180,220);
    }
    /**
     * These methods return rectangles with position bounds (hitbox).
     * Player range is like a small cross, so there have to be two methods with vertical and horizontal direction.
     * It is needed to move player smoothly
     * @return
     */
    public Rectangle getBounds_horizontal(){
        return new Rectangle(x+130,y+150,220,180);
    }
}
