@echo off
call %~dp0\renewEnvironment.cmd
start "Maven clean" /d "%~dp0\" /wait /min %comspec% /c mvn clean 
start "Maven install" /d "%~dp0\" /min %comspec% /k mvn install

