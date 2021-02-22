@ECHO OFF
set path="C:\Program Files\Java\jdk-11.0.3\bin";
TITLE BOOMBER MAN

javac "Main.java"
javac "client\\Bomb.java"
javac "client\\Box.java"
javac "client\\BufferedImageLoader.java"
javac "client\\ConfigBomb.java"
javac "client\\ConfigFire.java"
javac "client\\ConfigGame.java"
javac "client\\ConfigLevel.java"
javac "client\\Enemy.java"
javac "client\\Fire.java"
javac "client\\Game.java"
javac "client\\GameAttributes.java"
javac "client\\GameObject.java"
javac "client\\Grass.java"
javac "client\\Handler.java"
javac "client\\HelpConfig.java"
javac "client\\Id_object.java"
javac "client\\KeyInput.java"
javac "client\\Menu.java" 
javac "client\\Player.java"
javac "client\\Portal.java"
javac "client\\Scores.java"
javac "client\\Stone.java"
javac "client\\Window.java"

PAUSE 
ECHO. 

jar cvfm BOOMBER_MAN.jar META-INF\\MANIFEST.MF Main.class client\\Pictures\\*.png client\\Game$AppState.class client\\Bomb.class client\\Box.class client\\BufferedImageLoader.class client\\ConfigBomb.class client\\ConfigFire.class client\\ConfigGame.class client\\ConfigLevel.class client\\Enemy.class client\\Fire.class client\\Game.class client\\GameAttributes.class client\\GameObject.class client\\Grass.class client\\Handler.class client\\HelpConfig.class client\\Id_object.class client\\KeyInput.class client\\Menu.class client\\Player.class client\\Portal.class client\\Scores.class client\\Stone.class client\\Window.class
PAUSE 
ECHO. 
java -jar BOOMBER_MAN.jar 
PAUSE 