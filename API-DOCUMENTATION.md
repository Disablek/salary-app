# Salary App - Полное описание API

## Обзор системы

Salary-App - это микросервисная архитектура для управления зарплатой сотрудников, состоящая из 4 основных сервисов:

| Сервис | Порт | Назначение |
|--------|------|-----------|
| **API Gateway** | 8080 | Единая точка входа для всех клиентов |
| **Auth-Service** | 8081 | Аутентификация и JWT токены |
| **User-Service** | 8082 | Управление пользователями системы |
| **Employee-Service** | 8083 | Управление данными сотрудников |
| **DataTable-Service** | 8084 | Таблицы данных и расчеты зарплаты |

---

## 1. АУТЕНТИФИКАЦИЯ (Auth-Service)

### Базовый URL: `http://localhost:8080/api/auth`

### 1.1 Вход в систему

```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "admin@salary-app.local",
  "password": "Admin@123"
}
```

**Ответ (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbkBzYWxhcnktYXBwLmxvY2FsIiwiaWF0IjoxNjMwNzAzMjAwLCJleHAiOjE2MzA3ODk2MDB9.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
}
```

**Ошибки:**
- `400 Bad Request` - Ошибка в формате запроса
- `401 Unauthorized` - Неверные учетные данные

**Пример использования в Frontend (TypeScript):**
```typescript
async function login(email: string, password: string) {
  const response = await fetch('http://localhost:8080/api/auth/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ email, password })
  });
  
  const data = await response.json();
  localStorage.setItem('token', data.token);
  return data.token;
}
```

---

## 2. УПРАВЛЕНИЕ ПОЛЬЗОВАТЕЛЯМИ (User-Service)

### Базовый URL: `http://localhost:8080/api/users`

⚠️ **Все эндпоинты требуют JWT токен в заголовке:**
```
Authorization: Bearer {token}
```

### 2.1 Получить всех пользователей

```http
GET /api/users
Authorization: Bearer {token}
```

**Ответ (200 OK):**
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440001",
    "email": "admin@salary-app.local",
    "firstName": "Admin",
    "lastName": "User",
    "role": "ADMIN",
    "active": true,
    "createdAt": "2024-01-15T10:00:00Z"
  },
  {
    "id": "550e8400-e29b-41d4-a716-446655440002",
    "email": "user@salary-app.local",
    "firstName": "John",
    "lastName": "Doe",
    "role": "USER",
    "active": true,
    "createdAt": "2024-01-15T11:00:00Z"
  }
]
```

### 2.2 Получить пользователя по ID

```http
GET /api/users/{id}
Authorization: Bearer {token}
```

**Параметры:**
- `id` (UUID) - ID пользователя

**Ответ (200 OK):**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440001",
  "email": "admin@salary-app.local",
  "firstName": "Admin",
  "lastName": "User",
  "role": "ADMIN",
  "active": true,
  "createdAt": "2024-01-15T10:00:00Z"
}
```

### 2.3 Создать нового пользователя

```http
POST /api/users
Authorization: Bearer {token}
Content-Type: application/json

{
  "email": "newuser@salary-app.local",
  "password": "SecurePass@123",
  "firstName": "Jane",
  "lastName": "Smith",
  "role": "USER"
}
```

**Ответ (201 Created):**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440003",
  "email": "newuser@salary-app.local",
  "firstName": "Jane",
  "lastName": "Smith",
  "role": "USER",
  "active": true,
  "createdAt": "2024-01-15T12:00:00Z"
}
```

### 2.4 Обновить пользователя

```http
PUT /api/users/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "email": "updated@salary-app.local",
  "firstName": "Jane",
  "lastName": "Smith-Updated",
  "role": "USER"
}
```

**Ответ (200 OK):** Обновленный объект пользователя

### 2.5 Удалить пользователя

```http
DELETE /api/users/{id}
Authorization: Bearer {token}
```

**Ответ (204 No Content)** - без тела ответа

**Пример использования в Frontend:**
```typescript
class UserService {
  private baseUrl = 'http://localhost:8080/api/users';
  
  private getHeaders() {
    return {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    };
  }

  async getAllUsers() {
    const response = await fetch(this.baseUrl, {
      headers: this.getHeaders()
    });
    return response.json();
  }

  async getUserById(id: string) {
    const response = await fetch(`${this.baseUrl}/${id}`, {
      headers: this.getHeaders()
    });
    return response.json();
  }

  async createUser(userData: any) {
    const response = await fetch(this.baseUrl, {
      method: 'POST',
      headers: this.getHeaders(),
      body: JSON.stringify(userData)
    });
    return response.json();
  }

  async updateUser(id: string, userData: any) {
    const response = await fetch(`${this.baseUrl}/${id}`, {
      method: 'PUT',
      headers: this.getHeaders(),
      body: JSON.stringify(userData)
    });
    return response.json();
  }

  async deleteUser(id: string) {
    await fetch(`${this.baseUrl}/${id}`, {
      method: 'DELETE',
      headers: this.getHeaders()
    });
  }
}
```

---

## 3. УПРАВЛЕНИЕ СОТРУДНИКАМИ (Employee-Service)

### Базовый URL: `http://localhost:8080/api`

⚠️ **Все эндпоинты требуют JWT токен**

### 3.1 Сотрудники (Employees)

#### Получить всех сотрудников

```http
GET /api/employees
Authorization: Bearer {token}
```

**Ответ (200 OK):**
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440010",
    "firstName": "Иван",
    "lastName": "Петров",
    "patronymic": "Иванович",
    "email": "ivan.petrov@company.com",
    "phoneNumber": "+375291234567",
    "position": {
      "id": "550e8400-e29b-41d4-a716-446655440100",
      "name": "Разработчик",
      "description": "Senior Developer"
    },
    "subjects": [
      {
        "id": "550e8400-e29b-41d4-a716-446655440200",
        "name": "Backend",
        "description": "Backend Development"
      }
    ],
    "qualifications": [
      {
        "id": "550e8400-e29b-41d4-a716-446655440300",
        "name": "Senior Developer",
        "level": "3"
      }
    ],
    "experience": {
      "id": "550e8400-e29b-41d4-a716-446655440400",
      "yearsOfExperience": 7,
      "startDate": "2017-01-15"
    },
    "active": true,
    "hireDate": "2017-01-15"
  }
]
```

#### Поиск сотрудников

```http
POST /api/employees/search
Authorization: Bearer {token}
Content-Type: application/json

{
  "firstName": "Иван",
  "positionId": "550e8400-e29b-41d4-a716-446655440100"
}
```

**Ответ:** Отфильтрованный список сотрудников

#### Получить сотрудника по ID

```http
GET /api/employees/{employeeId}
Authorization: Bearer {token}
```

#### Создать сотрудника

```http
POST /api/employees
Authorization: Bearer {token}
Content-Type: application/json

{
  "firstName": "Петр",
  "lastName": "Иванов",
  "patronymic": "Сергеевич",
  "email": "petr.ivanov@company.com",
  "phoneNumber": "+375297654321",
  "positionId": "550e8400-e29b-41d4-a716-446655440100",
  "subjectIds": ["550e8400-e29b-41d4-a716-446655440200"],
  "qualificationIds": ["550e8400-e29b-41d4-a716-446655440300"],
  "yearsOfExperience": 5,
  "hireDate": "2024-01-15"
}
```

#### Обновить сотрудника

```http
PUT /api/employees/{employeeId}
Authorization: Bearer {token}
Content-Type: application/json

{
  "firstName": "Петр",
  "lastName": "Иванов",
  "email": "petr.updated@company.com",
  "phoneNumber": "+375299999999",
  "positionId": "550e8400-e29b-41d4-a716-446655440100",
  "subjectIds": ["550e8400-e29b-41d4-a716-446655440200"],
  "qualificationIds": ["550e8400-e29b-41d4-a716-446655440300"],
  "yearsOfExperience": 6,
  "hireDate": "2024-01-15"
}
```

#### Удалить сотрудника

```http
DELETE /api/employees/{employeeId}
Authorization: Bearer {token}
```

---

### 3.2 Должности (Positions)

#### Получить все должности

```http
GET /api/positions
Authorization: Bearer {token}
```

**Ответ (200 OK):**
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440100",
    "name": "Разработчик",
    "description": "Senior Developer",
    "salaryBase": 2000.00,
    "active": true
  },
  {
    "id": "550e8400-e29b-41d4-a716-446655440101",
    "name": "Тестировщик",
    "description": "QA Engineer",
    "salaryBase": 1500.00,
    "active": true
  }
]
```

#### Создать должность

```http
POST /api/positions
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Менеджер проектов",
  "description": "Project Manager",
  "salaryBase": 1800.00
}
```

#### Получить должность по ID

```http
GET /api/positions/{positionId}
Authorization: Bearer {token}
```

#### Обновить должность

```http
PUT /api/positions/{positionId}
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Менеджер проектов",
  "description": "Senior Project Manager",
  "salaryBase": 2000.00
}
```

#### Удалить должность

```http
DELETE /api/positions/{positionId}
Authorization: Bearer {token}
```

---

### 3.3 Предметы/Специальности (Subjects)

#### Получить все предметы

```http
GET /api/subjects
Authorization: Bearer {token}
```

**Ответ (200 OK):**
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440200",
    "name": "Backend",
    "description": "Backend Development",
    "active": true
  },
  {
    "id": "550e8400-e29b-41d4-a716-446655440201",
    "name": "Frontend",
    "description": "Frontend Development",
    "active": true
  }
]
```

#### Создать предмет

```http
POST /api/subjects
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "DevOps",
  "description": "DevOps и инфраструктура"
}
```

#### Остальные операции с предметами

- `GET /api/subjects/{subjectId}` - Получить предмет по ID
- `PUT /api/subjects/{subjectId}` - Обновить предмет
- `DELETE /api/subjects/{subjectId}` - Удалить предмет

---

### 3.4 Квалификации (Qualifications)

#### Получить все квалификации

```http
GET /api/qualifications
Authorization: Bearer {token}
```

**Ответ (200 OK):**
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440300",
    "name": "Senior Developer",
    "level": "3",
    "description": "Опыт более 5 лет",
    "active": true
  },
  {
    "id": "550e8400-e29b-41d4-a716-446655440301",
    "name": "Junior Developer",
    "level": "1",
    "description": "Опыт менее 2 лет",
    "active": true
  }
]
```

#### Создать квалификацию

```http
POST /api/qualifications
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Middle Developer",
  "level": "2",
  "description": "Опыт 2-5 лет"
}
```

#### Остальные операции с квалификациями

- `GET /api/qualifications/{qualificationId}` - Получить квалификацию по ID
- `PUT /api/qualifications/{qualificationId}` - Обновить квалификацию
- `DELETE /api/qualifications/{qualificationId}` - Удалить квалификацию

---

### 3.5 Опыт (Experiences)

#### Получить весь опыт

```http
GET /api/experiences
Authorization: Bearer {token}
```

**Ответ (200 OK):**
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440400",
    "yearsOfExperience": 7,
    "startDate": "2017-01-15",
    "endDate": null,
    "description": "Работает в компании",
    "active": true
  }
]
```

#### Создать запись об опыте

```http
POST /api/experiences
Authorization: Bearer {token}
Content-Type: application/json

{
  "yearsOfExperience": 5,
  "startDate": "2019-06-01",
  "endDate": null,
  "description": "Текущая работа"
}
```

#### Остальные операции с опытом

- `GET /api/experiences/{experienceId}` - Получить опыт по ID
- `PUT /api/experiences/{experienceId}` - Обновить опыт
- `DELETE /api/experiences/{experienceId}` - Удалить запись об опыте

---

## 4. ТАБЛИЦЫ ДАННЫХ И РАСЧЕТЫ (DataTable-Service)

### Базовый URL: `http://localhost:8080/api/datatables`

⚠️ **Все эндпоинты требуют JWT токен**

### 4.1 Получить все таблицы данных

```http
GET /api/datatables
Authorization: Bearer {token}
```

**Ответ (200 OK):**
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440500",
    "name": "Зарплата за январь 2024",
    "description": "Расчет зарплаты за январь",
    "createdAt": "2024-01-15T10:00:00Z",
    "createdBy": "admin@salary-app.local",
    "active": true,
    "columns": [
      {
        "id": "550e8400-e29b-41d4-a716-446655440501",
        "name": "ФИО",
        "type": "STRING",
        "order": 1
      },
      {
        "id": "550e8400-e29b-41d4-a716-446655440502",
        "name": "Должность",
        "type": "STRING",
        "order": 2
      },
      {
        "id": "550e8400-e29b-41d4-a716-446655440503",
        "name": "Оклад",
        "type": "DECIMAL",
        "order": 3
      },
      {
        "id": "550e8400-e29b-41d4-a716-446655440504",
        "name": "Надбавка",
        "type": "DECIMAL",
        "order": 4
      },
      {
        "id": "550e8400-e29b-41d4-a716-446655440505",
        "name": "Всего",
        "type": "FORMULA",
        "order": 5
      }
    ],
    "rows": [
      {
        "id": "550e8400-e29b-41d4-a716-446655440600",
        "employeeId": "550e8400-e29b-41d4-a716-446655440010",
        "order": 1,
        "cells": [
          {
            "id": "550e8400-e29b-41d4-a716-446655440601",
            "columnId": "550e8400-e29b-41d4-a716-446655440501",
            "value": "Иван Петров"
          },
          {
            "id": "550e8400-e29b-41d4-a716-446655440602",
            "columnId": "550e8400-e29b-41d4-a716-446655440502",
            "value": "Разработчик"
          },
          {
            "id": "550e8400-e29b-41d4-a716-446655440603",
            "columnId": "550e8400-e29b-41d4-a716-446655440503",
            "value": "2000.00"
          },
          {
            "id": "550e8400-e29b-41d4-a716-446655440604",
            "columnId": "550e8400-e29b-41d4-a716-446655440504",
            "value": "300.00"
          },
          {
            "id": "550e8400-e29b-41d4-a716-446655440605",
            "columnId": "550e8400-e29b-41d4-a716-446655440505",
            "value": "2300.00"
          }
        ]
      }
    ]
  }
]
```

### 4.2 Получить таблицу по ID

```http
GET /api/datatables/{id}
Authorization: Bearer {token}
```

### 4.3 Создать новую таблицу

```http
POST /api/datatables
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Зарплата за февраль 2024",
  "description": "Расчет зарплаты за февраль",
  "columns": [
    {
      "name": "ФИО",
      "type": "STRING",
      "order": 1
    },
    {
      "name": "Должность",
      "type": "STRING",
      "order": 2
    },
    {
      "name": "Оклад",
      "type": "DECIMAL",
      "order": 3
    },
    {
      "name": "Надбавка",
      "type": "DECIMAL",
      "order": 4
    },
    {
      "name": "Всего",
      "type": "FORMULA",
      "order": 5,
      "formula": "SUM(COLUMN_3, COLUMN_4)"
    }
  ]
}
```

### 4.4 Обновить таблицу

```http
PUT /api/datatables/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Зарплата за февраль 2024 (обновленная)",
  "description": "Расчет зарплаты за февраль - обновленная версия"
}
```

### 4.5 Удалить таблицу

```http
DELETE /api/datatables/{id}
Authorization: Bearer {token}
```

---

## 5. СТРУКТУРА ОШИБОК

Все сервисы используют единую структуру ошибок:

### 400 Bad Request - Ошибка валидации

```json
{
  "timestamp": "2024-01-15T10:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "errors": [
    {
      "field": "email",
      "message": "Email should be valid"
    },
    {
      "field": "password",
      "message": "Password must be at least 8 characters"
    }
  ],
  "path": "/api/users"
}
```

### 401 Unauthorized - Неверная аутентификация

```json
{
  "timestamp": "2024-01-15T10:00:00Z",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid credentials",
  "path": "/api/auth/login"
}
```

### 403 Forbidden - Доступ запрещен

```json
{
  "timestamp": "2024-01-15T10:00:00Z",
  "status": 403,
  "error": "Forbidden",
  "message": "Access denied",
  "path": "/api/users"
}
```

### 404 Not Found - Ресурс не найден

```json
{
  "timestamp": "2024-01-15T10:00:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "User with id 550e8400-e29b-41d4-a716-446655440001 not found",
  "path": "/api/users/550e8400-e29b-41d4-a716-446655440001"
}
```

### 500 Internal Server Error - Ошибка сервера

```json
{
  "timestamp": "2024-01-15T10:00:00Z",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An unexpected error occurred",
  "path": "/api/datatables"
}
```

---

## 6. КОНФИГУРАЦИЯ ДЛЯ FRONTEND

### Рекомендуемые переменные окружения

```bash
# .env
REACT_APP_API_BASE_URL=http://localhost:8080
REACT_APP_AUTH_ENDPOINT=/api/auth/login
REACT_APP_USER_ENDPOINT=/api/users
REACT_APP_EMPLOYEE_ENDPOINT=/api/employees
REACT_APP_DATATABLE_ENDPOINT=/api/datatables
```

### Конфигурация CORS

**Разрешенные источники:**
- `http://localhost:3000` (локальная разработка Frontend)
- `http://localhost:8080` (API Gateway)

**Разрешенные методы:** GET, POST, PUT, DELETE, PATCH, OPTIONS

**Разрешенные заголовки:** Authorization, Content-Type

---

## 7. ПРИМЕР FRONTEND ПРИЛОЖЕНИЯ (React TypeScript)

### Auth Service

```typescript
// services/authService.ts
class AuthService {
  private baseUrl = 'http://localhost:8080/api/auth';

  async login(email: string, password: string): Promise<string> {
    const response = await fetch(`${this.baseUrl}/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ email, password })
    });

    if (!response.ok) {
      throw new Error('Login failed');
    }

    const data = await response.json();
    localStorage.setItem('token', data.token);
    return data.token;
  }

  logout(): void {
    localStorage.removeItem('token');
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }
}

export default new AuthService();
```

### API Client

```typescript
// services/apiClient.ts
class ApiClient {
  private baseUrl = 'http://localhost:8080';

  private getHeaders(): HeadersInit {
    const token = localStorage.getItem('token');
    return {
      'Content-Type': 'application/json',
      ...(token && { 'Authorization': `Bearer ${token}` })
    };
  }

  async get<T>(endpoint: string): Promise<T> {
    const response = await fetch(`${this.baseUrl}${endpoint}`, {
      method: 'GET',
      headers: this.getHeaders()
    });
    return this.handleResponse<T>(response);
  }

  async post<T>(endpoint: string, data: any): Promise<T> {
    const response = await fetch(`${this.baseUrl}${endpoint}`, {
      method: 'POST',
      headers: this.getHeaders(),
      body: JSON.stringify(data)
    });
    return this.handleResponse<T>(response);
  }

  async put<T>(endpoint: string, data: any): Promise<T> {
    const response = await fetch(`${this.baseUrl}${endpoint}`, {
      method: 'PUT',
      headers: this.getHeaders(),
      body: JSON.stringify(data)
    });
    return this.handleResponse<T>(response);
  }

  async delete<T>(endpoint: string): Promise<T> {
    const response = await fetch(`${this.baseUrl}${endpoint}`, {
      method: 'DELETE',
      headers: this.getHeaders()
    });
    return this.handleResponse<T>(response);
  }

  private async handleResponse<T>(response: Response): Promise<T> {
    if (response.status === 204) {
      return {} as T;
    }

    const data = await response.json();

    if (!response.ok) {
      throw new Error(data.message || 'An error occurred');
    }

    return data;
  }
}

export default new ApiClient();
```

### Employee Service

```typescript
// services/employeeService.ts
import apiClient from './apiClient';

interface Employee {
  id: string;
  firstName: string;
  lastName: string;
  patronymic: string;
  email: string;
  phoneNumber: string;
  position: Position;
  subjects: Subject[];
  qualifications: Qualification[];
  experience: Experience;
  active: boolean;
  hireDate: string;
}

class EmployeeService {
  async getAllEmployees(): Promise<Employee[]> {
    return apiClient.get<Employee[]>('/api/employees');
  }

  async getEmployeeById(id: string): Promise<Employee> {
    return apiClient.get<Employee>(`/api/employees/${id}`);
  }

  async createEmployee(employee: Omit<Employee, 'id'>): Promise<Employee> {
    return apiClient.post<Employee>('/api/employees', employee);
  }

  async updateEmployee(id: string, employee: Partial<Employee>): Promise<Employee> {
    return apiClient.put<Employee>(`/api/employees/${id}`, employee);
  }

  async deleteEmployee(id: string): Promise<void> {
    return apiClient.delete(`/api/employees/${id}`);
  }

  async searchEmployees(filters: any): Promise<Employee[]> {
    return apiClient.post<Employee[]>('/api/employees/search', filters);
  }
}

export default new EmployeeService();
```

---

## 8. ТЕСТИРОВАНИЕ API

### Используя curl

```bash
# Вход в систему
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@salary-app.local",
    "password": "Admin@123"
  }'

# Получить всех пользователей
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer {token}"

# Получить всех сотрудников
curl -X GET http://localhost:8080/api/employees \
  -H "Authorization: Bearer {token}"
```

### Используя Postman

1. Импортируйте collection из JSON файла с примерами всех эндпоинтов
2. Установите переменную `{{base_url}}` = `http://localhost:8080`
3. Установите переменную `{{token}}` из ответа login endpoint
4. Тестируйте каждый эндпоинт с необходимыми параметрами

---

## 9. АРХИТЕКТУРНЫЕ ОСОБЕННОСТИ

### JWT Token

Токен содержит:
- Subject (email пользователя)
- Issue At (время создания)
- Expiration (время истечения - обычно 1 час)

**Обновление токена:** Требуется повторная аутентификация

### Роли пользователей

- `ADMIN` - Полный доступ ко всем операциям
- `USER` - Ограниченный доступ (чтение данных, создание отчетов)

### Валидация данных

Все эндпоинты использую аннотацию `@Valid` для валидации входящих данных. Ошибки валидации возвращаются с статусом 400 Bad Request.

---

## 10. ДОПОЛНИТЕЛЬНЫЕ РЕСУРСЫ

### Документация API (Swagger/OpenAPI)

После запуска приложения документация доступна по адресу:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

### Здоровье сервисов

```http
GET http://localhost:8080/actuator/health
```

### Метрики

```http
GET http://localhost:8080/actuator/metrics
```

---

## Заключение

Данное описание API содержит всю необходимую информацию для создания Frontend приложения на React, Angular, Vue или любой другой фреймворк. API следует REST принципам и использует стандартные HTTP методы и коды ответов.

Для вопросов и поддержки обращайтесь к документации микросервисов в проекте.
