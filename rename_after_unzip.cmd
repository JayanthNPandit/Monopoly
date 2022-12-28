@echo off
REM cls
REM This batch command will rename all the *.txt files and *.ja files back to *.cmd and *.jar files

IF NOT EXIST compile.txt GOTO ERROR

REN compile.txt compile.cmd
REN run.txt run.cmd
REN compile_and_run.txt compile_and_run.cmd
REN rename_before_zip.txt rename_before_zip.cmd

REN lib\junit-platform-console-standalone-1.6.2.ja junit-platform-console-standalone-1.6.2.jar
REN lib\junit-platform-runner-1.6.2.ja junit-platform-runner-1.6.2.jar
REN lib\junit-platform-suite-api-1.6.2.ja junit-platform-suite-api-1.6.2.jar

GOTO END
:ERROR
ECHO.
ECHO You must run this "%0%" program from the same "root directory" where *.txt files and lib folder are located
ECHO.
:END
