# Запуск всех микросервисов

# Запуск инфраструктуры
docker-compose up -d

# Ожидание запуска
Start-Sleep -Seconds 30

# Запуск Eureka Server
Start-Process -FilePath "powershell.exe" -ArgumentList "-Command cd services/eureka-server; .\gradlew bootRun" -NoNewWindow

# Ожидание
Start-Sleep -Seconds 10

# Запуск Config Server
Start-Process -FilePath "powershell.exe" -ArgumentList "-Command cd services/config-server; .\gradlew bootRun" -NoNewWindow

# Ожидание
Start-Sleep -Seconds 10

# Запуск API Gateway
Start-Process -FilePath "powershell.exe" -ArgumentList "-Command cd services/api-gateway; .\gradlew bootRun" -NoNewWindow

# Ожидание
Start-Sleep -Seconds 10

# Запуск бизнес-сервисов
Start-Process -FilePath "powershell.exe" -ArgumentList "-Command cd services/auth-service; .\gradlew bootRun" -NoNewWindow
Start-Process -FilePath "powershell.exe" -ArgumentList "-Command cd services/user-service; .\gradlew bootRun" -NoNewWindow
Start-Process -FilePath "powershell.exe" -ArgumentList "-Command cd services/employee-service; .\gradlew bootRun" -NoNewWindow
Start-Process -FilePath "powershell.exe" -ArgumentList "-Command cd services/dataTable-service; .\gradlew bootRun" -NoNewWindow

Write-Host "Все сервисы запущены"