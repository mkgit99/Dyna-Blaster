package client;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * This class describes fire which destroys boxes and enemies.
 * Class is added to Handler.
 */
public class Fire extends GameObject {
    /**
     * Represents time after which the Fire give damage.
     */
    private ConfigFire configFire;
    private Handler handler;

    /**
     * Fire constructor
     * @param x number of pixels along the horizontal axis
     * @param y number of pixels along the vertical axis
     * @param id object id
     * @param image fire image which is loaded to the object
     * @param handler container to storage this object
     */
    public Fire(int x, int y, Id_object id, BufferedImage image,Handler handler) throws IOException {
        super(x, y, id, image);
        this.handler=handler;
        configFire= new ConfigFire("Properties/ConfigFire.properties");
    }

    /**
     * Method checks if fire range includes box or enemy. Then removes those objects and itself.
     */
    private void colision(){

        for (int i = handler.object.size()-1; i >=0 ; i--) {
            GameObject tempObject=handler.object.get(i);

            if( tempObject.getId()==Id_object.Box ){

                if(getBounds_horizontal().intersects(tempObject.getBounds()) || getBounds_vertical().intersects(tempObject.getBounds())){
                    handler.gameAttributes.setScore(handler.gameAttributes.getScore() + (int)(10*handler.gameAttributes.getMultiplier_difficulty()));
                    handler.removeObject(tempObject);
                }

            }
            if(  tempObject.getId()==Id_object.Enemy){

                if(getBounds_horizontal().intersects(tempObject.getBounds()) || getBounds_vertical().intersects(tempObject.getBounds())){
                    handler.gameAttributes.setScore(handler.gameAttributes.getScore() + (int)(15*handler.gameAttributes.getMultiplier_difficulty()));
                    handler.removeObject(tempObject);
                }

            }
            if( tempObject.getId()==Id_object.Player){

                if(getBounds_horizontal().intersects(tempObject.getBounds()) || getBounds_vertical().intersects(tempObject.getBounds())){
                    handler.gameAttributes.setHealth(handler.gameAttributes.getHealth()-(int)(10*handler.gameAttributes.getMultiplier_difficulty()));
                }

            }

        }
        handler.removeObject(this);
    }

    /**
     * This method updates fire's state per unit of time.
     * After static value of time, fire and enemies/boxes disappear.
     */
    public void tick() {
        if(configFire.getTime_to_damage() ==0){
            colision();
        }
        configFire.setTime_to_damage(configFire.getTime_to_damage()-1);
    }

    /**
     * This method draws fire on game board.
     * @param g2
     */
    public void render(Graphics2D g2) {
        g2.drawImage(getImage(),getX()-480,getY()-480,null);
        //g2.drawRect(x+190,y-360,100,1200);
        //g2.drawRect(x-360,y+190,1200,100);
    }

    /**
     * This method returns a rectangle with position bounds (hitbox).
     * @return
     */
    public Rectangle getBounds() {
        return null;
    }




    /**
     * These methods return rectangles with position bounds (hitbox).
     * Fire range is like a cross, so there have to be two methods with vertical and horizontal direction.
     * @return
     */
    public Rectangle getBounds_vertical() {
        return new Rectangle(x+190,y-360,100,1200);
    }
    /**
     * These methods return rectangles with position bounds (hitbox).
     * Fire range is like a cross, so there have to be two methods with vertical and horizontal direction.
     * @return
     */
    public Rectangle getBounds_horizontal(){
        return new Rectangle(x-360,y+190,1200,100);
    }

}
