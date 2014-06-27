@echo off
setlocal
call "%~dp0\renewEnvironment.cmd"
pushd "%~dp0"
call mvn clean install %*
pause

