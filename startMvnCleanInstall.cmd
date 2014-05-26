@echo off
call %~dp0\renewEnvironment.cmd
start "hsqlDB" /d "%~dp0\" /min %comspec% /k mvn clean && mvn install
pause
