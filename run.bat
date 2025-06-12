@echo off
setlocal

REM Prompt user to choose if they want console windows visible
choice /m "Show backend and frontend command windows? (Y=Yes, N=No)"

if errorlevel 2 (
    REM User chose No - run hidden
    call :startBackendHidden
    call :startFrontendHidden
) else (
    REM User chose Yes - run visible consoles
    call :startBackendVisible
    call :startFrontendVisible
)

echo.
echo Servers started.
echo To stop servers, run: stop_servers.bat
pause
exit /b


:startBackendVisible
echo Starting backend with console window...
start cmd /k "cd server && gradlew.bat run"
exit /b

:startFrontendVisible
echo Starting frontend with console window...
start cmd /k "cd client && npm run dev"
exit /b


:startBackendHidden
echo Starting backend hidden...
powershell -WindowStyle Hidden -Command "Start-Process -FilePath 'cmd.exe' -ArgumentList '/c gradlew.bat run' -WorkingDirectory '%CD%\server' -NoNewWindow"
exit /b

:startFrontendHidden
echo Starting frontend hidden...
powershell -WindowStyle Hidden -Command "Start-Process -FilePath 'cmd.exe' -ArgumentList '/c npm run dev' -WorkingDirectory '%CD%\client' -NoNewWindow"
exit /b