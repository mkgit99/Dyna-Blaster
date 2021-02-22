package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Socket;

/**
 * This class represents main menu in application.
 * It contains menu's appearance, buttons and methods using ActionListener and MouseAdapter.
 */
public class Menu extends MouseAdapter implements ActionListener {

    private Game game;
    private Handler handler;
    private BufferedImage player;
    private static Socket socket;
    private HelpConfig helpconfig;

    /**
     * Closed is logic value which allows user to open only one window with game settings.
     */
    Boolean closed=true;
    JFrame frame1;
    JTextField tf1;
    JComboBox difBox;
    JButton bStart, bBack;
    JLabel l1, l2;

    /**
     * Constructor passes given classes values to local variables.
     * @param game
     * @param handler
     * @param player
     */
    public Menu(Game game, Handler handler, BufferedImage player, Socket socket){
        this.game = game;
        this.handler = handler;
        this.player = player;
        this.socket=socket;
        helpconfig=new HelpConfig();
    }

    /**
     * Method which changes game state depending on the actual mouse position.
     * When button "play" is pressed, new window with game settings is opened.
     * @param e mouse coordinates
     */
    public void mousePressed(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();

        if(game.gameState == Game.AppState.MENU){
            //PLAY BUTTON
            if(mouseOver(mx, my, (int)(190*game.getGame_scale_x()),(int)(200*game.getGame_scale_y()),(int)(220*game.getGame_scale_x()),(int)(54*game.getGame_scale_y()))){
                if(closed) {
                    frame1 = new JFrame("settings");
					//frame1.setUndecorated(true);
                    frame1.setSize(400, 550);
                    frame1.setTitle("Game Settings");
                    frame1.setVisible(true);
                    frame1.setLocationRelativeTo(null);
                    frame1.setResizable(false);
                    frame1.setLayout(null);
                    frame1.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    frame1.getContentPane().setBackground(Color.cyan);

                    l1 = new JLabel("Write your Nick:");
                    l1.setBounds(50,40,100,30);
                    tf1 = new JTextField("");
                    tf1.setBounds(50,70,300,30);

                    l2 = new JLabel("Choose dificulty level:");
                    l2.setBounds(50,110,200,30);
                    String difficulty[] = {"Easy","Medium","Hard"};
                    difBox=new JComboBox(difficulty);
                    difBox.setBounds(50,140,300,30);

                    bStart=new JButton("Start Game!");
                    bStart.setBounds(170,240,120,30);
                    bStart.addActionListener(this);

					bBack = new JButton("Back to Menu!");
                    bBack.setBounds(50, 240, 120, 30);
                    bBack.addActionListener(this);

                    frame1.add(l1);
                    frame1.add(l2);
                    frame1.add(tf1);
                    frame1.add(difBox);
                    frame1.add(bStart);
					frame1.add(bBack);
                    closed=false;
                }
            }

            //BEST RESULTS BUTTON
            if(mouseOver(mx, my, (int)(190*game.getGame_scale_x()),(int)(300*game.getGame_scale_y()),(int)(220*game.getGame_scale_x()),(int)(54*game.getGame_scale_y())))
            {
                if(socket!=null)
                game.getBestScores();
                try {
                    handler.scores=new Scores("Properties/ConfigBestScores.properties");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                game.gameState = Game.AppState.RESULTS;
            }

            //HELP BUTTON
            if(mouseOver(mx, my, (int)(190*game.getGame_scale_x()),(int)(400*game.getGame_scale_y()),(int)(220*game.getGame_scale_x()),(int)(54*game.getGame_scale_y()))){
               if(socket!=null)
                game.getHelpConfig();
                game.gameState = Game.AppState.HELP;
            }

            //EXIT BUTTON
            if(mouseOver(mx, my, (int)(190*game.getGame_scale_x()),(int)(500*game.getGame_scale_y()),(int)(220*game.getGame_scale_x()),(int)(54*game.getGame_scale_y()))){
                System.exit(1);
            }
        }
        //BACK BUTTON FOR HELP
        if(game.gameState == Game.AppState.HELP || game.gameState == Game.AppState.RESULTS){
            if(mouseOver(mx, my, (int)(190*game.getGame_scale_x()),(int)(500*game.getGame_scale_y()),(int)(220*game.getGame_scale_x()),(int)(54*game.getGame_scale_y()))){
                game.gameState = Game.AppState.MENU;
            }
        }
    }

    public void mouseReleased(MouseEvent e){
    }

    /**
     * This method returns logic value as true if mouse is pressed in rectangle (button).
     * @param mx actual mause horizontal position
     * @param my actual mause vertical position
     * @param x rectangle horizontal position
     * @param y rectangle vertical position
     * @param width rectangle width
     * @param height rectangle height
     * @return logic value
     */
    private boolean mouseOver(int mx, int my, int x, int y, int width, int height){
        if(mx > x && mx < x + width){
            if(my > y && my < y + height){
                return true;
            }else return false;
        }else return false;
    }

    /**
     * This method draws menu on window.
     * @param g Variable of type Graphics2D.
     */
    public void render(Graphics2D g){
        if(game.gameState == Game.AppState.MENU){
            Font fnt0 = new Font("Serif", Font.BOLD,45);
            Font fnt1 = new Font("Serif", Font.BOLD,25);

            g.setFont(fnt0);
            g.setColor(Color.BLACK);
            g.drawString("DYNA BLASTER", 122, 100);

            g.setFont(fnt1);
            g.fillRect(190,200,220,54);
            g.setColor(Color.cyan);
            g.drawString("PLAY", 268, 235);

            g.setColor(Color.BLACK);
            g.fillRect(190,300,220,54);
            g.setColor(Color.cyan);
            g.drawString("BEST RESULTS", 205, 335);

            g.setColor(Color.BLACK);
            g.fillRect(190,400,220,54);
            g.setColor(Color.cyan);
            g.drawString("HELP", 268, 435);

            g.setColor(Color.BLACK);
            g.fillRect(190,500,220,54);
            g.setColor(Color.cyan);
            g.drawString("EXIT", 272, 535);
        }
        else if(game.gameState == Game.AppState.RESULTS){
            Font fnt0 = new Font("Serif", Font.BOLD, 35);
            Font fnt1 = new Font("Serif", Font.BOLD, 25);
            Font fnt2 = new Font("Serif", Font.BOLD, 20);

            g.setFont(fnt0);
            g.setColor(Color.BLACK);
            g.drawString("LIST", 255, 80);
            g.setFont(fnt2);
            g.drawString("Place     Nick    Score", 70, 120);
            for(int i=0;i<10;i++) {
                g.drawString(i+1+"        "+handler.scores.getScores(i), 90, 150+30*i);
            }
            g.setFont(fnt1);
            g.setColor(Color.BLACK);
            g.fillRect(190,500,220,54);
            g.setColor(Color.cyan);
            g.drawString("BACK", 265, 535);
        }
        else if(game.gameState == Game.AppState.HELP){
            Font fnt0 = new Font("Serif", Font.BOLD, 35);
            Font fnt1 = new Font("Serif", Font.BOLD, 25);
            Font fnt2 = new Font("Serif", Font.BOLD, 20);
            Font fnt3 = new Font("Serif", Font.PLAIN, 16);

            g.setFont(fnt0);
            g.setColor(Color.BLACK);
            g.drawString("HELP", 255, 100);
            String []command=helpconfig.gethelpstring();
            g.setFont(fnt2);
            g.drawString(command[0], 70, 150);
            g.setFont(fnt3);
            g.drawString(command[1], 70, 170);
            g.drawString(command[2], 70, 190);
            g.setFont(fnt2);
            g.drawString(command[3], 70, 230);
            g.setFont(fnt3);
            for(int i=4;i<helpconfig.get_length_of_commands();i++) {
                g.drawString(command[i], 70, 250+(i-4)*20);

            }
            g.setFont(fnt1);
            g.setColor(Color.BLACK);
            g.fillRect(190,500,220,54);
            g.setColor(Color.cyan);
            g.drawString("BACK", 265, 535);
        }
    }

    /**
     * This method starts new game with proper multiplier or makes user back to main menu, depending on what button user clicked.
     * {@inheritDoc}
     */
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source==bStart) {
            game.gameState = Game.AppState.NEWGAME;
            handler.gameAttributes.setPlayerNick(tf1.getText());
            handler.gameAttributes.setMultiplier_difficulty(String.valueOf(difBox.getSelectedItem()));
            frame1.dispose();
        }
		if(source==bBack){
            game.gameState= Game.AppState.MENU;
            frame1.dispose();

            closed=true;
        }
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }
}
