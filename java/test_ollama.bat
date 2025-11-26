@echo off
echo ========================================
echo Test de Conectividad Ollama (DeepSeek)
echo ========================================
echo.

cd /d "%~dp0"

echo Compilando TestPort.java...
call gradlew.bat compileJava -q

if %ERRORLEVEL% NEQ 0 (
    echo ERROR: No se pudo compilar el proyecto
    pause
    exit /b 1
)

echo.
echo Ejecutando test de conectividad...
echo.

call gradlew.bat -q --console=plain execute -PmainClass=com.solrac.computers.util.TestPort

echo.
pause
