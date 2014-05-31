@echo off
setlocal
call %~dp0\renewEnvironment.cmd
pushd %~dp0
call mvn clean 
call mvn install
pause

