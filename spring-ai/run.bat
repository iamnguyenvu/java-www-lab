@echo off
echo ===================================
echo AI Flight Assistant - Startup
echo ===================================
echo.

REM Check Java version
echo [1/4] Checking Java version...
java -version 2>&1 | findstr /C:"version" >nul
if errorlevel 1 (
    echo ERROR: Java not found! Please install Java 21+
    pause
    exit /b 1
)
echo OK: Java found
echo.

REM Check Maven
echo [2/4] Checking Maven...
mvn -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Maven not found! Please install Maven 3.8+
    pause
    exit /b 1
)
echo OK: Maven found
echo.

REM Check API Key
echo [3/4] Checking OpenAI API Key...
if "%OPENAI_API_KEY%"=="" (
    echo WARNING: OPENAI_API_KEY not set!
    echo.
    set /p apikey="Please enter your OpenAI API Key: "
    set OPENAI_API_KEY=%apikey%
)
echo OK: API Key configured
echo.

REM Run application
echo [4/4] Starting application...
echo.
echo Application will start at: http://localhost:8088
echo Press Ctrl+C to stop
echo.
echo ===================================
echo.

mvn spring-boot:run

pause
