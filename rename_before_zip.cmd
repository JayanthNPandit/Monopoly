@echo off
REM cls
REM This batch command will rename all the *.cmd files and *.jar files to get them ready for creating a ZIP file

IF NOT EXIST compile.cmd GOTO ERROR

REN compile.cmd compile.txt
REN run.cmd run.txt
REN compile_and_run.cmd compile_and_run.txt
REN rename_after_unzip.cmd rename_after_unzip.txt

REN lib\junit-platform-console-standalone-1.6.2.jar junit-platform-console-standalone-1.6.2.ja
REN lib\junit-platform-runner-1.6.2.jar junit-platform-runner-1.6.2.ja
REN lib\junit-platform-suite-api-1.6.2.jar junit-platform-suite-api-1.6.2.ja

REN rename_before_zip.cmd rename_before_zip.txt

GOTO END
:ERROR
ECHO.
ECHO You must run this "%0%" program from the same "root directory" where *.cmd files and lib folder are located
ECHO.
:END
