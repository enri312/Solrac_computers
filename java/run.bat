@echo off
echo ========================================
echo Iniciando Solrac Computers Application
echo ========================================
echo.
cd /d "%~dp0"
echo Directorio actual: %CD%
echo.
echo Ejecutando gradlew run...
echo.
call gradlew.bat run
echo.
echo ========================================
echo Aplicacion finalizada
echo ========================================
pause
