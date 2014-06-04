@echo off
call "%~dp0\renewEnvironment.cmd"
start "tomcat 7" /d "%~dp0\tl_server\" /min %comspec% /c mvn tomcat7:run
