package client;

import client.*;


import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;


/**
 * This class contains methods which destribes how program will load, work and reflesh itself.
 */
public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;

    private static Socket socket;
    ConfigGame configgame;
    ConfigLevel level;

    private boolean runnning = false;
    public boolean level_exist = false;
    private int number_of_level=0;
    private Handler handler;
    private String path_level;
    private BufferedImage stone = null;
    private BufferedImage grass = null;
    private BufferedImage box = null;
    private BufferedImage player = null;
    private BufferedImage bomb = null;
    private BufferedImage fire = null;
    private BufferedImage enemy = null;
    private BufferedImage portal = null;

    private double scale=0.125;
    private double game_scale_x=0;
    private double game_scale_y=0;

    private Menu menu;

    /**
     * Enum States of program.
     */
    public enum AppState {
        MENU,
        RESULTS,
        HELP,
        GAME,
        PAUSE,
        WIN,
        LOSE,
        GAMEEND,
        NEXTLEVEL,
        NEWGAME
    };

    /**
     * This enum makes user see main menu after run the application.
     */
    public AppState gameState = AppState.MENU;

    // 1 is box
    // 2 is stone
    // 0 is grass , grass is also in place where stone isn't
    // 3 enemy
    // 4 portal
    private int[][] look_of_map;

    /**
     * Class constructor
     * Adds configs level and game.
     * Adds listeners Mouse and keybord.
     * Adds players, walls, box, grass.
     */
    public Game(Socket socket) throws IOException {
        this.socket=socket;
        configgame = new ConfigGame("Properties/ConfigGame.properties");
        path_level  = "Properties/ConfigLevel1.properties";

        handler = new Handler(this);
        menu = new Menu(this, handler, player,socket);
        new Window(configgame.getWidth(), configgame.getHeight(), "DynaBlaster", this);

        BufferedImageLoader loader = new BufferedImageLoader();
        stone = loader.loadImage("Pictures/wall.png");
        grass = loader.loadImage("Pictures/grass.png");
        box = loader.loadImage("Pictures/box.png");
        player = loader.loadImage("Pictures/player.png");
        bomb = loader.loadImage("Pictures/bomb.png");
        fire = loader.loadImage("Pictures/fire.png");
        enemy = loader.loadImage("Pictures/enemy.png");
        portal = loader.loadImage("Pictures/portal.png");

        this.addKeyListener(new KeyInput(this, handler, bomb, fire));
        this.addMouseListener(menu);

    }

    /**
     * This method updates scale.
     */
    public void get_New_Scale(){
        setGame_scale_x((double)getWidth()/600);
        setGame_scale_y((double)getHeight()/600);
    }

    /**
     * This method is to start running program.
     */
    public  synchronized void start(){
        runnning=true;
        new Thread(this).start();

    }

    /**
     * This method is to stop running program.
     */
    public  synchronized void stop(){
        try{

            runnning=false;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method is main engine this game.
     */
    public void run(){
        long lastTime=System.nanoTime();
        double nsPerTick=1000000000D/60D;

        int frames=0;
        int ticks=0;
        long lastTimer=System.currentTimeMillis();
        double delta=0;
        boolean shouldRender=true;
        while (runnning){
            long now =System.nanoTime();
            delta+=(now-lastTime)/nsPerTick;
            lastTime=now;

            while (delta>=1)
            {
                ticks++;
                try {
                    tick();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                delta-=1;
                shouldRender=true;
            }
            try {
                Thread.sleep(2);
            }   catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(shouldRender) {
                frames++;
                render();
            }
            if(System.currentTimeMillis()-lastTimer>=1000){
                lastTimer +=1000;

                frames=0;
                ticks=0;
            }
        }
    }

    /**
     * This method updates menu and objects from handler.
     */
    public void tick() throws IOException {

        get_New_Scale();
        if(gameState==AppState.GAMEEND){
            number_of_level=0;
            deletelevel();
            gameState=AppState.MENU;
            menu.setClosed(true);
        }

        if(gameState==AppState.NEWGAME){

            gameState=AppState.GAME;
            number_of_level++;
            if(socket!=null) {
                getGameAttributes();
                getBombConfig();
                getFireConfig();
                get_Level("Properties/ConfigLevel" + number_of_level + ".properties");
            }
            handler.gameAttributes.setgameattributes("Properties/ConfigGameAttributes.properties");
            newlevel();
        }
        if(gameState==AppState.NEXTLEVEL){
            number_of_level++;

            if(number_of_level>configgame.getNumber_of_levels())
            {
				handler.gameAttributes.setScore(handler.gameAttributes.getScore()+handler.gameAttributes.getMax_game_time()/60);
				handler.gameAttributes.setMax_game_time(10);
                if(socket!=null)
					
                Send_scores();
                gameState=AppState.WIN;
            }
            else {

                if(socket!=null)
                    get_Level("Properties/ConfigLevel"+number_of_level+".properties");
                deletelevel();
                newlevel();
                gameState = AppState.GAME;
            }
        }
        if(gameState == AppState.GAME){
            handler.tick();

        }else if(gameState == AppState.MENU){
            if(number_of_level!=0) {
                deletelevel();
                number_of_level = 0;
            }
        }
    }

    /**
     * This method draws objects from handler and draw menu.
     */
    public void render(){
        BufferStrategy bufferStrategy=this.getBufferStrategy();
        if(bufferStrategy==null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g=bufferStrategy.getDrawGraphics();
        Graphics2D g2=(Graphics2D) g;

        g2.setColor(Color.cyan);
        g2.fillRect(0,0,getWidth(),getHeight());

        if(gameState == AppState.GAME || gameState == AppState.PAUSE||gameState==AppState.WIN||gameState==AppState.LOSE){

            g2.scale(getGame_scale_x()*scale*2/3,getGame_scale_y()*scale*2/3);

            handler.render(g2);
            Font fnt0 = new Font("Serif", Font.BOLD,400);
            g2.setFont(fnt0);
            g2.setColor(Color.cyan);
            g2.drawString(level.getName_level(),3000,7080);
        }else if(gameState == AppState.MENU || gameState == AppState.HELP || gameState == AppState.RESULTS){
            g2.scale(getGame_scale_x(),getGame_scale_y());
            menu.render(g2);
        }

        g.dispose();
        bufferStrategy.show();
    }

    /**
     * Method to remove objects from previous level.
     */
    public void deletelevel() {

        for (int i = handler.object.size()-1; i >=0 ; i--) {
            GameObject tempObject = handler.object.get(i);
           handler.removeObject(tempObject);

        }
    }

    /**
     * Method to add new objects to game in new level.
     */
    public void newlevel() throws IOException {

        path_level  = "Properties/ConfigLevel"+number_of_level+".properties";

        level = new ConfigLevel(path_level);

        look_of_map = level.getFull_map_int();
        for (int i = 0; i < level.getNumber_of_boxs_width(); i++) {
            for (int j = 0; j < level.getNumber_of_boxs_height(); j++) {
                if (look_of_map[i][j] == 2) {
                    handler.addObject(new Stone(i * 480, j * 480, Id_object.Stone, stone));
                } else {
                    handler.addObject(new Grass(i * 480, j * 480, Id_object.Grass, grass));
                }
            }}
        for (int i = 0; i < level.getNumber_of_boxs_width(); i++) {
            for (int j = 0; j < level.getNumber_of_boxs_height(); j++) {

                if (look_of_map[i][j] == 1) {
                    handler.addObject(new Box(i * 480, j * 480, Id_object.Box, box));

                }
                if (look_of_map[i][j] == 3) {
                    handler.addObject(new Enemy(i * 480, j * 480, Id_object.Enemy, enemy, handler, this));
                }
                if (look_of_map[i][j] == 4) {
                    handler.addObject(new Portal(i * 480, j * 480, Id_object.Portal, portal));
                    handler.addObject(new Box(i * 480, j * 480, Id_object.Box, box));
                }
            }
        }
        handler.addObject(new Player(480, 480, Id_object.Player, player, handler,this));
        level_exist=true;
    }

    /**
     * This method send request list of best scores to server and save receive string to properties file.
     */
    public static void getBestScores(){
        try{
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            pw.println("GET_BEST_SCORES");
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String settings = br.readLine();

            String[] setting = settings.split(" ");
            PrintWriter out = new PrintWriter("Properties/ConfigBestScores.properties");

            for (int i = 0; i < setting.length; i++) {
                out.println(setting[i]);
            }
            out.close();

        }catch (IOException e){
            System.out.println("Access denied");
            System.out.println(e);
        }
    }
    /**
     * This method send request level config to server and save receive string to given path.
     * @param path
     */
    private void get_Level(String path){
        try{
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            pw.println("GET_LEVEL-"+number_of_level);
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String settings = br.readLine();
            String []setting =settings.split(" ");

            PrintWriter out = new PrintWriter(path);
            for(int i=0;i<setting.length;i++) {
                out.println(setting[i]);
            }
            out.close();
        }catch (IOException e){
            System.out.println("Access denied");
            System.out.println(e);
        }
    }

    /**
     * This method sends new score from client to server.
     */
    private void Send_scores(){
        try{
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            pw.println("SEND_SCORE-"+handler.gameAttributes.getPlayerNick()+"-"+handler.gameAttributes.getScore());
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String settings = br.readLine();

        }catch (IOException e){
            System.out.println("Access denied");
            System.out.println(e);
        }
    }

    /**
     * This method send request game attributes to server and save receive string to .properties file.
     */
    private static void getGameAttributes(){
        try{
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            pw.println("GET_GAME_ATTRIBUTES");
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String settings = br.readLine();
            String []setting =settings.split(" ");
            PrintWriter out = new PrintWriter("Properties/ConfigGameAttributes.properties");
            for(int i=0;i<setting.length;i++) {
                out.println(setting[i]);
            }
            out.close();
        }catch (IOException e){
            System.out.println("Access denied");
            System.out.println(e);
        }
    }
    /**
     * This method send request bomb attributes to server and save receive string to .properties file.
     */
    private static void getBombConfig(){
        try{
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            pw.println("GET_BOMB_CONFIG");
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String settings = br.readLine();
            String []setting =settings.split(" ");
            PrintWriter out = new PrintWriter("Properties/ConfigBomb.properties");
            for(int i=0;i<setting.length;i++) {
                out.println(setting[i]);
            }
            out.close();
        }catch (IOException e){
            System.out.println("Access denied");
            System.out.println(e);
        }
    }

    /**
     * This method send request fire attributes to server and save receive string to .properties file.
     */
    private static void getFireConfig(){
        try{
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            pw.println("GET_FIRE_CONFIG");
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String settings = br.readLine();
            String []setting =settings.split(" ");
            PrintWriter out = new PrintWriter("Properties/ConfigFire.properties");
            for(int i=0;i<setting.length;i++) {
                out.println(setting[i]);
            }
            out.close();
        }catch (IOException e){
            System.out.println("Access denied");
            System.out.println(e);
        }
    }
    /**
     * This method send request help text to server and save receive string to .txt file.
     */
    public static void getHelpConfig(){
        try{
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            pw.println("GET_HELP");
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String settings = br.readLine();
            String []setting =settings.split("@");
            PrintWriter out = new PrintWriter("Properties/HelpConfig.txt");
            for(int i=0;i<setting.length;i++) {
                out.println(setting[i]);
            }
            out.close();
        }catch (IOException e){
            System.out.println("Access denied");
            System.out.println(e);
        }
    }


    public double getGame_scale_x() {
        return game_scale_x;
    }
    public void setGame_scale_x(double game_scale_x) {
        this.game_scale_x = game_scale_x;
    }

    public double getGame_scale_y() {
        return game_scale_y;
    }
    public void setGame_scale_y(double game_scale_y) {
        this.game_scale_y = game_scale_y;
    }
}
