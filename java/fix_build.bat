@echo off
echo ========================================
echo Limpiando y reconstruyendo el proyecto
echo ========================================
echo.

echo Paso 1: Limpiando build anterior...
call gradlew.bat clean

echo.
echo Paso 2: Descargando dependencias actualizadas...
call gradlew.bat build --refresh-dependencies

echo.
echo ========================================
echo Build completado!
echo ========================================
echo.
echo Ahora recarga VS Code con: Ctrl+Shift+P > "Developer: Reload Window"
echo.
pause
