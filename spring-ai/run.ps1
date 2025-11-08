# AI Flight Assistant - PowerShell Startup Script
Write-Host "===================================" -ForegroundColor Cyan
Write-Host "AI Flight Assistant - Startup" -ForegroundColor Cyan
Write-Host "===================================" -ForegroundColor Cyan
Write-Host ""

# Check Java version
Write-Host "[1/4] Checking Java version..." -ForegroundColor Yellow
try {
    $javaVersion = java -version 2>&1 | Select-String "version"
    Write-Host "OK: $javaVersion" -ForegroundColor Green
} catch {
    Write-Host "ERROR: Java not found! Please install Java 21+" -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}
Write-Host ""

# Check Maven
Write-Host "[2/4] Checking Maven..." -ForegroundColor Yellow
try {
    $mavenVersion = mvn -version 2>&1 | Select-String "Apache Maven"
    Write-Host "OK: $mavenVersion" -ForegroundColor Green
} catch {
    Write-Host "ERROR: Maven not found! Please install Maven 3.8+" -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}
Write-Host ""

# Check API Key
Write-Host "[3/4] Checking OpenAI API Key..." -ForegroundColor Yellow
if (-not $env:OPENAI_API_KEY) {
    Write-Host "WARNING: OPENAI_API_KEY not set!" -ForegroundColor Red
    Write-Host ""
    $apiKey = Read-Host "Please enter your OpenAI API Key"
    $env:OPENAI_API_KEY = $apiKey
    Write-Host "API Key set for this session" -ForegroundColor Green
} else {
    $maskedKey = $env:OPENAI_API_KEY.Substring(0, [Math]::Min(10, $env:OPENAI_API_KEY.Length)) + "..."
    Write-Host "OK: API Key configured ($maskedKey)" -ForegroundColor Green
}
Write-Host ""

# Run application
Write-Host "[4/4] Starting application..." -ForegroundColor Yellow
Write-Host ""
Write-Host "Application will start at: " -NoNewline
Write-Host "http://localhost:8088" -ForegroundColor Cyan
Write-Host "Press Ctrl+C to stop" -ForegroundColor Yellow
Write-Host ""
Write-Host "===================================" -ForegroundColor Cyan
Write-Host ""

mvn spring-boot:run
