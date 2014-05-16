@echo off
start "tomcat 7" /d "%~dp0\tl_server\" /min %comspec% /c mvn tomcat7:run
