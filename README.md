# Salary App

## Описание

WEB-приложение формирования списков педагогических работников лицея БНТУ с автоматизированным расчётом выплат. Приложение позволяет управлять данными о сотрудниках, формировать списки и автоматически рассчитывать заработную плату на основе заданных параметров.

## Функциональность

- **Аутентификация и авторизация**: Вход в систему с использованием JWT токенов
- **Управление пользователями**: Регистрация, профили пользователей
- **Админ-панель**: Управление данными и настройками
- **Формирование списков**: Создание и редактирование списков педагогических работников
- **Автоматизированный расчёт выплат**: Расчёт зарплаты на основе различных параметров
- **Просмотр и редактирование таблиц данных**: Интерфейс для работы с табличными данными

## Технологии

### Backend
- **Java 21**
- **Spring Boot 3.5.7**
- **Spring Security** - для аутентификации и авторизации
- **Spring Data JPA** - для работы с базой данных
- **PostgreSQL** - основная база данных
- **H2** - база данных для тестирования
- **JWT** - для токенов аутентификации
- **Liquibase** - для миграций базы данных
- **OpenAPI/Swagger** - для документации API
- **MapStruct** - для маппинга объектов
- **Lombok** - для сокращения boilerplate кода

### Frontend
- **React 19** - библиотека для создания пользовательского интерфейса
- **React Router** - для навигации
- **Testing Library** - для тестирования компонентов

### Сборка и управление зависимостями
- **Gradle** - для сборки backend
- **npm** - для управления зависимостями frontend

## Установка и запуск

### Предварительные требования
- Java 21
- Node.js и npm
- PostgreSQL база данных
- Gradle (или использовать gradlew)

### Настройка базы данных
1. Установите PostgreSQL
2. Создайте базу данных `postgres`
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