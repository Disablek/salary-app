# Salary App - Microservices Architecture

## Описание

WEB-приложение формирования списков педагогических работников лицея БНТУ с автоматизированным расчётом выплат. Приложение переделано на микросервисную архитектуру с Clean Architecture в сервисах.

## Архитектура

Проект состоит из следующих микросервисов:
- **auth-service** (порт 8081): Аутентификация и авторизация пользователей
- **user-service** (порт 8082): Управление пользователями
- **employee-service** (порт 8083): Управление сотрудниками
- **dataTable-service** (порт 8084): Управление таблицами данных

Каждый сервис имеет свою базу данных PostgreSQL и общается через RabbitMQ.

## Функциональность

- **Аутентификация и авторизация**: Вход в систему с использованием JWT токенов
- **Управление пользователями**: Регистрация, профили пользователей
- **Управление сотрудниками**: CRUD операции с данными сотрудников
- **Формирование списков**: Создание и редактирование списков педагогических работников
- **Автоматизированный расчёт выплат**: Расчёт зарплаты на основе различных параметров
- **Просмотр и редактирование таблиц данных**: Интерфейс для работы с табличными данными

## Технологии

### Backend (каждый микросервис)
- **Java 21**
- **Spring Boot 3.5.7**
- **Spring Data JPA** - для работы с базой данных
- **PostgreSQL** - отдельная БД для каждого сервиса
- **RabbitMQ** - для асинхронного общения между сервисами
- **OpenAPI/Swagger** - для документации API
- **Lombok** - для сокращения boilerplate кода
- **Clean Architecture**: domain, application, infrastructure, presentation слои

### Frontend
- **React 19** - библиотека для создания пользовательского интерфейса
- **React Router** - для навигации
- **Testing Library** - для тестирования компонентов

### Инфраструктура
- **Docker Compose** - для запуска БД и RabbitMQ
- **Gradle** - для сборки backend сервисов
- **npm** - для управления зависимостями frontend

### Production Features
- **Service Discovery**: Eureka Server
- **Configuration Management**: Spring Cloud Config Server
- **API Gateway**: Spring Cloud Gateway с JWT аутентификацией
- **Circuit Breaker**: Resilience4j для защиты от cascade failures
- **Distributed Tracing**: Zipkin для отслеживания запросов
- **Service Mesh**: Istio для продвинутого управления трафиком
- **Containerization**: Docker с multi-stage builds
- **Orchestration**: Kubernetes с Helm charts
- **CI/CD**: GitHub Actions для автоматизации сборки и развертывания
- **Saga Patterns**: Choreography-based для распределенных транзакций

## Установка и запуск

### Предварительные требования
- Java 21
- Node.js и npm
- Docker и Docker Compose
- Gradle (или использовать gradlew)

### Запуск инфраструктуры
1. Запустите базы данных и RabbitMQ:
```bash
docker-compose up -d
```

### Запуск микросервисов

#### 1. Запуск инфраструктуры
```bash
docker-compose up -d
```

#### 2. Запуск Eureka Server
```bash
cd services/eureka-server
./gradlew bootRun
```

#### 3. Запуск Config Server
```bash
cd services/config-server
./gradlew bootRun
```

#### 4. Запуск API Gateway
```bash
cd services/api-gateway
./gradlew bootRun
```

#### 5. Запуск бизнес-сервисов
```bash
cd services/auth-service
./gradlew bootRun

cd services/user-service
./gradlew bootRun

cd services/employee-service
./gradlew bootRun

cd services/dataTable-service
./gradlew bootRun
```

### Запуск frontend
```bash
cd frontend
npm install
npm start
```
## Production Deployment

### Docker Containerization

Все сервисы контейнеризированы с multi-stage builds для оптимизации размера образов.

```bash
# Сборка всех образов
docker-compose build

# Запуск в production режиме
docker-compose -f docker-compose.yml up -d
```

### Kubernetes & Helm Deployment

#### Предварительные требования
- Kubernetes cluster (Minikube, EKS, GKE, etc.)
- kubectl configured
- Helm 3.x

#### Развертывание с Helm

```bash
# Установка chart
helm install salary-app ./helm/salary-app

# Обновление
helm upgrade salary-app ./helm/salary-app

# Удаление
helm uninstall salary-app
```

#### Ручное развертывание

```bash
# Применение конфигураций
kubectl apply -f k8s/

# Проверка статуса
kubectl get pods
kubectl get services
```

### Service Mesh с Istio

Для продвинутого управления трафиком и observability:

```bash
# Установка Istio
istioctl install

# Применение конфигураций
kubectl apply -f istio/
```

### CI/CD Pipeline

Проект включает GitHub Actions pipeline для автоматизированной сборки и развертывания:

- Автоматическое тестирование
- Сборка Docker образов
- Публикация в registry
- Деплой в Kubernetes (опционально)

## Структура проекта

```
salary-app/
├── services/                    # Микросервисы
│   ├── auth-service/
│   ├── user-service/
│   ├── dataTable-service/
│   ├── api-gateway/
│   ├── eureka-server/
│   └── config-server/
├── istio/                       # Service Mesh конфигурации
├── k8s/                         # Kubernetes manifests
├── helm/                        # Helm charts
│   └── salary-app/
├── frontend/                    # React приложение
├── docker-compose.yml           # Локальная разработка
├── .github/workflows/           # CI/CD pipelines
└── README.md
```

## Архитектура

```
┌─────────────────┐    ┌─────────────────┐
│   API Gateway   │────│   Eureka Server │
│   (Port 8080)   │    │   (Port 8761)   │
└─────────────────┘    └─────────────────┘
          │                       │
          ├───────────────────────┼───────────────────────
          │                       │
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│  Auth Service   │    │  User Service   │    │ DataTable Svc   │
│   (Port 8081)   │    │   (Port 8082)   │    │   (Port 8084)    │
└─────────────────┘    └─────────────────┘    └─────────────────┘
          │                       │                       │
          └───────────────────────┼───────────────────────┘
                                  │
                       ┌─────────────────┐
                       │   PostgreSQL    │
                       │   RabbitMQ      │
                       │   Zipkin        │
                       └─────────────────┘
```

## Мониторинг и Observability

- **Health Checks**: Spring Boot Actuator (`/actuator/health`)
- **Metrics**: Micrometer (готово для Prometheus)
- **Distributed Tracing**: Zipkin (`http://localhost:9411`)
- **Service Discovery**: Eureka (`http://localhost:8761`)
- **Circuit Breaker**: Resilience4j с настраиваемыми threshold
- **Service Mesh**: Istio для traffic management

## Безопасность

- **JWT Authentication**: Валидация токенов в API Gateway
- **Role-based Access**: Передача ролей через headers
- **CORS**: Настроен для frontend
- **Security Headers**: Через Spring Security

## API Документация

Каждый сервис имеет Swagger UI:
- API Gateway: `http://localhost:8080/swagger-ui.html`
- Auth Service: `http://localhost:8081/swagger-ui.html`
- User Service: `http://localhost:8082/swagger-ui.html`
- DataTable Service: `http://localhost:8084/swagger-ui.html`

## Следующие шаги

1. **Monitoring Stack**: Prometheus + Grafana для метрик и алертинга
2. **Log Aggregation**: ELK Stack для централизованного логирования
3. **API Documentation**: Swagger UI aggregation через Gateway
4. **Database Migration**: Flyway вместо Liquibase
5. **Event Sourcing**: Для сложных бизнес-процессов
6. **API Versioning**: Стратегия версионирования API
7. **Rate Limiting**: Защита от перегрузок
8. **End-to-End Testing**: Интеграционные тесты для Saga patterns
## Сервисы и порты

- **Eureka Server**: http://localhost:8761
- **Config Server**: http://localhost:8888
- **API Gateway**: http://localhost:8080
- **Auth Service**: http://localhost:8081
- **User Service**: http://localhost:8082
- **Employee Service**: http://localhost:8083
- **DataTable Service**: http://localhost:8084

## API Документация

Каждый сервис имеет Swagger UI:
- Auth Service: http://localhost:8081/swagger-ui
- User Service: http://localhost:8082/swagger-ui
- Employee Service: http://localhost:8083/swagger-ui
- DataTable Service: http://localhost:8084/swagger-ui

## API через Gateway

Все запросы теперь идут через API Gateway:
- `POST http://localhost:8080/api/auth/login`
- `GET http://localhost:8080/api/users`
- `GET http://localhost:8080/api/datatables`

## Переменные окружения

Для каждого сервиса можно настроить:
- `DB_USERNAME` - имя пользователя БД (по умолчанию: postgres)
- `DB_PASSWORD` - пароль БД (по умолчанию: 2580)
- `TOKEN_SIGNING_KEY` - ключ для JWT (для auth-service)

## Продакшн-функции

### 🏗️ Saga Patterns
Реализованы распределенные транзакции с использованием Choreography-based Saga:
- **User Creation Saga**: При создании пользователя публикуется событие в RabbitMQ
- **Compensation**: В случае ошибки публикуется событие компенсации

### 🔌 Circuit Breaker
Интеграция Resilience4j для отказоустойчивости:
- **API Gateway**: Circuit Breaker для каждого сервиса
- **Конфигурация**: 50% failure rate threshold, автоматическое восстановление

### 📊 Distributed Tracing
Zipkin для трассировки распределенных запросов:
- **Dashboard**: http://localhost:9411
- **Sleuth**: Автоматическая трассировка всех HTTP запросов

### 🔐 API Gateway Security
JWT аутентификация на уровне Gateway:
- **Фильтр**: JwtAuthenticationFilter проверяет токены
- **Headers**: Передает user info downstream сервисам

### 🌐 Service Mesh (Istio)
Базовая конфигурация Istio:
- **Gateway**: Внешний доступ через Istio Gateway
- **VirtualService**: Маршрутизация трафика
- **DestinationRule**: Circuit Breaker и connection pooling

### 🚀 CI/CD Pipeline
GitHub Actions для автоматизации:
- **Тестирование**: Сборка и unit тесты всех сервисов
- **Docker**: Сборка и push образов в Docker Hub
- **Условия**: Автоматический запуск на push в main/develop

## Запуск с Docker Compose

```bash
# Полный запуск всех сервисов
docker-compose up --build

# Или с override файлом
docker-compose -f docker-compose.yml -f docker-compose.override.yml up --build
```

## Мониторинг

- **Eureka Dashboard**: http://localhost:8761
- **Zipkin Dashboard**: http://localhost:9411
- **RabbitMQ Management**: http://localhost:15672
- **Actuator Endpoints**: `/actuator/health`, `/actuator/metrics`

## Istio Deployment

```bash
# Установка Istio
istioctl install

# Применение конфигураций
kubectl apply -f istio/

# Просмотр dashboard
istioctl dashboard kiali
```
3. Настройте переменные окружения:
   - `DB_USERNAME` - имя пользователя БД
   - `DB_PASSWORD` - пароль пользователя БД
   - `TOKEN_SIGNING_KEY` - ключ для подписи JWT токенов

### Запуск backend
1. Перейдите в корневую директорию проекта
2. Выполните скрипт запуска:
   ```powershell
   .\run-backend.ps1
   ```
   Или вручную:
   ```bash
   export DB_USERNAME=postgres
   export DB_PASSWORD=2580
   export TOKEN_SIGNING_KEY=your-secret-key
   ./gradlew bootRun
   ```
3. Backend будет доступен на `http://localhost:8080`
4. Swagger UI доступен по адресу `http://localhost:8080/swagger-ui`

### Запуск frontend
1. Перейдите в директорию `frontend`
2. Установите зависимости:
   ```bash
   npm install
   ```
3. Запустите приложение:
   ```bash
   npm start
   ```
4. Frontend будет доступен на `http://localhost:3000`

## Структура проекта

```
salary-app/
├── src/main/java/by/bntu/salaryapp/     # Исходный код backend
├── src/main/resources/                  # Ресурсы backend
│   ├── application.properties           # Конфигурация приложения
│   └── db/                              # Миграции Liquibase
├── src/test/                            # Тесты backend
├── frontend/                            # Исходный код frontend
│   ├── src/
│   │   ├── components/                  # React компоненты
│   │   ├── pages/                       # Страницы приложения
│   │   ├── services/                    # Сервисы для API
│   │   └── context/                     # React контекст
│   └── public/                          # Статические файлы
├── build.gradle                         # Конфигурация Gradle
├── settings.gradle                      # Настройки Gradle
└── package.json                         # Зависимости frontend (корень)
```

## API документация

После запуска backend, документация API доступна через Swagger UI по адресу `http://localhost:8080/swagger-ui`.

## Тестирование

### Backend тесты
```bash
./gradlew test
```

### Frontend тесты
```bash
cd frontend
npm test
```

## Сборка для продакшена

### Backend
```bash
./gradlew build
```

### Frontend
```bash
cd frontend
npm run build
```

## Разработка

- Используйте IDE с поддержкой Java (IntelliJ IDEA, Eclipse) для backend
- Используйте VS Code или другую IDE для frontend
- Для отладки backend используйте встроенные инструменты Spring Boot
- Для отладки frontend используйте React DevTools

## Контрибьютинг

1. Форкните репозиторий
2. Создайте ветку для вашей фичи (`git checkout -b feature/AmazingFeature`)
3. Зафиксируйте изменения (`git commit -m 'Add some AmazingFeature'`)
4. Отправьте в ветку (`git push origin feature/AmazingFeature`)
5. Создайте Pull Request

## Лицензия

Этот проект лицензирован под MIT License - см. файл [LICENSE](LICENSE) для деталей.