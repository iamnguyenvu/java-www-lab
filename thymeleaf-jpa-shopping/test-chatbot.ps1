# Test Chatbot API
Write-Host "=== Testing Chatbot API ===" -ForegroundColor Cyan

# Test 1: Simple greeting
Write-Host "`n[Test 1] Simple greeting..." -ForegroundColor Yellow
$body1 = @{
    message = "Xin chào"
    sessionId = "test-001"
} | ConvertTo-Json

try {
    $response1 = Invoke-RestMethod -Uri "http://localhost:8088/api/chat" -Method Post -ContentType "application/json" -Body $body1
    Write-Host "✓ Success:" -ForegroundColor Green
    Write-Host "  Message: $($response1.message)" -ForegroundColor White
    Write-Host "  Session: $($response1.sessionId)" -ForegroundColor Gray
} catch {
    Write-Host "✗ Failed: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 2: Product inquiry
Write-Host "`n[Test 2] Product inquiry..." -ForegroundColor Yellow
$body2 = @{
    message = "Có sản phẩm nào giá dưới 500k không?"
    sessionId = "test-001"
} | ConvertTo-Json

try {
    $response2 = Invoke-RestMethod -Uri "http://localhost:8088/api/chat" -Method Post -ContentType "application/json" -Body $body2
    Write-Host "✓ Success:" -ForegroundColor Green
    Write-Host "  Message: $($response2.message)" -ForegroundColor White
} catch {
    Write-Host "✗ Failed: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 3: Stock check
Write-Host "`n[Test 3] Stock check..." -ForegroundColor Yellow
$body3 = @{
    message = "iPad Pro 12.9 còn hàng không?"
    sessionId = "test-002"
} | ConvertTo-Json

try {
    $response3 = Invoke-RestMethod -Uri "http://localhost:8088/api/chat" -Method Post -ContentType "application/json" -Body $body3
    Write-Host "✓ Success:" -ForegroundColor Green
    Write-Host "  Message: $($response3.message)" -ForegroundColor White
} catch {
    Write-Host "✗ Failed: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 4: Empty message (should fail)
Write-Host "`n[Test 4] Empty message (should return error)..." -ForegroundColor Yellow
$body4 = @{
    message = ""
    sessionId = "test-003"
} | ConvertTo-Json

try {
    $response4 = Invoke-RestMethod -Uri "http://localhost:8088/api/chat" -Method Post -ContentType "application/json" -Body $body4
    Write-Host "✗ Unexpected success - should have failed" -ForegroundColor Red
} catch {
    Write-Host "✓ Expected error: $($_.Exception.Message)" -ForegroundColor Green
}

Write-Host "`n=== Tests Completed ===" -ForegroundColor Cyan
