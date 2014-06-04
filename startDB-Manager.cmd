@echo off
call "%~dp0\renewEnvironment.cmd"
start "hsqlDB-Manager" /d "%~dp0\tl_db\" /min %comspec% /c mvn exec:java -P hsqldb-manager

