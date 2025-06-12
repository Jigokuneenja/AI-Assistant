@echo off
echo Stopping backend and frontend servers...

REM Kill Java backend process (gradlew run)
taskkill /F /IM java.exe

REM Kill Node frontend (npm run dev)
taskkill /F /IM node.exe

echo Servers stopped.
pause