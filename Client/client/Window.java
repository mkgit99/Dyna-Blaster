package client;

import javax.swing.*;
import java.awt.*;

/**
 * This class adds window and sets parameters of look.
 */
public class Window extends Canvas {
    /**
     * Window constructor
     * @param width
     * @param height
     * @param name
     * @param game
     */
    public Window(int width, int height, String name, Game game){
        // Initialize frame
        JFrame frame= new JFrame(name);
        // Stop program when closing
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set size the frame
        frame.setSize(width, height);
        frame.setTitle(name);
        // Show the frame
        frame.setVisible(true);
        // Centering the frame
        frame.setLocationRelativeTo(null);
        // Resizing
        frame.setResizable(true);
        // Add game to frame
        frame.add(game);
        game.start();
    }
}



