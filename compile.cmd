@echo off
REM cls
REM ... Check to see if the current location is the right "root folder"
IF NOT EXIST src GOTO ERROR
IF NOT EXIST bin GOTO ERROR
IF NOT EXIST bin GOTO ERROR
ECHO.
ECHO Compiling Java source code...
ECHO.
REM
REM ... Compile the main Classes
REM
del bin\main-classes\*.class /s 1> nul 2> nul
dir /s /b src\main\*.java > sources.txt
javac -cp lib @sources.txt -d bin\main-classes
del sources.txt
REM
REM ... Compile the JUnit Test Classes
REM
del bin\test-classes\*.class /s 1> nul 2> nul
dir /s /b src\test\*.java > sources.txt
SET MONOPOLY_CLASSPATH=lib\junit-platform-console-standalone-1.6.2.jar;lib\junit-platform-runner-1.6.2.jar;lib\junit-platform-suite-api-1.6.2.jar;bin\main-classes;bin\test-classes
javac -classpath %MONOPOLY_CLASSPATH%  @sources.txt -d bin\test-classes
del sources.txt
REM
REM ... Run the JUnit Tests
REM
java -classpath %MONOPOLY_CLASSPATH% -jar lib\junit-platform-console-standalone-1.6.2.jar -classpath %MONOPOLY_CLASSPATH% --disable-ansi-colors --scan-classpath
GOTO END
:ERROR
ECHO.
ECHO Please run this "%0%" program from the parent-folder of "src" folder.
ECHO.
:END
