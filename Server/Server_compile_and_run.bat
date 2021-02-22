@ECHO OFF
set path="C:\Program Files\Java\jdk-11.0.3\bin";
TITLE SERVER BOOMBER MAN
javac "server\\Server.java"
javac "server\\ServerCommands.java"
javac "server\\ServerThread.java"
javac "server\\User_and_Score.java"
javac "Main.java"

PAUSE 
ECHO. 
jar cvfm Server.jar META-INF\\MANIFEST.MF Main.class server\\Server.class server\\ServerCommands.class server\\ServerThread.class server\\User_and_Score.class 
java -jar Server.jar
PAUSE 