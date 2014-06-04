@echo off
rem set | find "%%" | sort /R
rem echo.----------------------------------------
for /F "tokens=1* delims==" %%a in ('set ^| find "%%"') do (
    REM %%a ... Umgebungsvariable (zB PATH)
    REM %%b ... aktueller Variablen-Wert lt. Umgebung (zB "...;%HOME_DIR%;...")
    rem echo --- CURRENT LOCAL:
    rem echo %%~a=%%~b
    rem echo.
    REM lokale Umgebungsvariable setzen
    for /F "tokens=*" %%c in ('echo "%%~b"') do (
        REM %%c ..... interpretierter aktueller Wert
        set %%~a=%%~c
    )
    rem echo --- NEW LOCAL:
    rem set "%%~a" | find "%%~a="
    rem echo.----------------------------------------
)
rem set | find "%%" | sort /R
rem pause 
