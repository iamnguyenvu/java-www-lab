# Build Production - PowerShell Script
Write-Host "===================================" -ForegroundColor Cyan
Write-Host "Building for Production" -ForegroundColor Cyan
Write-Host "===================================" -ForegroundColor Cyan
Write-Host ""

# Clean
Write-Host "[1/3] Cleaning old build..." -ForegroundColor Yellow
mvn clean
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Clean failed!" -ForegroundColor Red
    exit 1
}
Write-Host "OK: Clean completed" -ForegroundColor Green
Write-Host ""

# Build
Write-Host "[2/3] Building production package..." -ForegroundColor Yellow
Write-Host "This may take a few minutes..." -ForegroundColor Yellow
mvn package -Pproduction -DskipTests
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Build failed!" -ForegroundColor Red
    exit 1
}
Write-Host "OK: Build completed" -ForegroundColor Green
Write-Host ""

# Info
Write-Host "[3/3] Build information" -ForegroundColor Yellow
$jarFile = Get-ChildItem -Path "target" -Filter "*.jar" | Where-Object { $_.Name -notlike "*-sources.jar" -and $_.Name -notlike "*-javadoc.jar" } | Select-Object -First 1

if ($jarFile) {
    Write-Host "JAR file created: " -NoNewline
    Write-Host $jarFile.Name -ForegroundColor Cyan
    Write-Host "Size: " -NoNewline
    Write-Host ("{0:N2} MB" -f ($jarFile.Length / 1MB)) -ForegroundColor Cyan
    Write-Host "Location: " -NoNewline
    Write-Host $jarFile.FullName -ForegroundColor Cyan
    Write-Host ""
    Write-Host "To run:" -ForegroundColor Yellow
    Write-Host "  java -jar $($jarFile.FullName)" -ForegroundColor White
} else {
    Write-Host "WARNING: JAR file not found in target folder" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "===================================" -ForegroundColor Cyan
Write-Host "Build completed successfully!" -ForegroundColor Green
Write-Host "===================================" -ForegroundColor Cyan

Read-Host "Press Enter to exit"
