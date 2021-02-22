package client;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class describes enemy which player have to destroy during a game.
 * Class is added to Handler.
 */
public class Enemy extends GameObject {
    private  Handler handler;
    private Game game;
	private Boolean change_of_direction_X=false;
	private	Boolean change_of_direction_Y=false;


    /**
     * This constructor initiates enemy's movement - in this case enemy starts going up on board.
     * @param x number of pixels along the horizontal axis
     * @param y number of pixels along the vertical axis
     * @param id object id
     * @param image enemy image which is loaded to the object
     * @param handler contener to storage this object
     */
    public Enemy(int x, int y, Id_object id, BufferedImage image,Handler handler, Game game) {
        super(x, y, id, image);
        this.handler=handler;
        this.game=game;
        speedY=(int)(20*handler.gameAttributes.getMultiplier_difficulty());
        speedX=(int)(11*handler.gameAttributes.getMultiplier_difficulty());
    }

    /**
     * This method updates enemies's position per unit of time. Also runs collision method.
     */
    public void tick() {
        if(game.gameState == Game.AppState.GAME) {
          
			x += speedX;
            y += speedY;
	
            colision();
			  
        }
    }

    /**
     * This method draws enemy on game board.
     * @param g2
     */
    public void render(Graphics2D g2) {
        g2.drawImage(getImage(),getX(),getY(),null);
        //g2.drawRect(x+150,y+130,180,220);
       //g2.drawRect(x+130,y+150,220,180);
    }

    /**
     * This method returns an enemy as a rectangle with position bounds (hitbox).
     * @return
     */
    public Rectangle getBounds() {
        return new Rectangle(x,y,480,480);
    }
    /**
     * These methods return rectangles with position bounds (hitbox).
     * Enemy range is like a small cross, so there have to be two methods with vertical and horizontal direction.
     * @return
     */
    public Rectangle getBounds_vertical() {
        return new Rectangle(x+150,y+130,180,220);
    }
    /**
     * These methods return rectangles with position bounds (hitbox).
     * Enemy range is like a  small cross, so there have to be two methods with vertical and horizontal direction.
     * @return
     */
    public Rectangle getBounds_horizontal(){
        return new Rectangle(x+130,y+150,220,180);
    }
    /**
     * Method checks if enemy is near stone or box plate, then makes it move in the opposite direction.
     */
    private void colision() {
		change_of_direction_X=false;
		change_of_direction_Y=false;
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == Id_object.Stone || tempObject.getId() == Id_object.Box) {
				
                if (getBounds_vertical().intersects(tempObject.getBounds())&&change_of_direction_Y==false) {

                  speedY*=-1;
				  change_of_direction_Y=true;
                    }
                if (getBounds_horizontal().intersects(tempObject.getBounds())&&change_of_direction_X==false) {

                    speedX*=-1;
					change_of_direction_X=true;
                }
				

                }
            }
        }

    }



