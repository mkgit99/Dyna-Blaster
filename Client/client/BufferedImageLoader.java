package client;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * This class only uses method described below.
 */
public class BufferedImageLoader {
    private BufferedImage image;

    /**
     * Method returns image from the given path and throws exception if path is incorrent.
     * @param path Given path
     * @return Image
     * @throws IOException
     */
    public BufferedImage loadImage(String path) throws IOException {
    try {
        image =ImageIO.read(getClass().getResource(path));
    }catch (Exception e){
        e.printStackTrace();
    }

    return image;
    }
}
