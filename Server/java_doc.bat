ECHO OFF
set path="C:\Program Files\Java\jdk-11.0.3\bin";
SET mypath=%~dp0
javadoc -private -d  javaDoc server Main.java
ECHO Dokumentacja w katalogu javaDoc
pause