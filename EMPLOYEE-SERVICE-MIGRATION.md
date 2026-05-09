# Employee-Service Microservice Migration Guide

## 📊 Architecture Overview

```
Clean Architecture Layers (in monolith src/main/java/by/bntu/salaryapp/):

┌─────────────────────────────────────────┐
│   PRESENTATION (REST Controllers)       │  ← HTTP Layer
├─────────────────────────────────────────┤
│   APPLICATION (Services, DTOs)          │  ← Business Logic
├─────────────────────────────────────────┤
│   DOMAIN (Models, Entities)             │  ← Business Rules
├─────────────────────────────────────────┤
│   INFRASTRUCTURE (Persistence, Config)  │  ← Technical Details
└─────────────────────────────────────────┘
```

---

## 📁 COMPLETE FILE MIGRATION LIST

### 1️⃣ DOMAIN LAYER
**Source**: `src/main/java/by/bntu/salaryapp/domain/`  
**Target**: `services/employee-service/src/main/java/by/bntu/salaryapp/domain/`

#### Base Classes (2 files)
```
model/
  ├── BaseEntity.java                      # Base entity with ID
  └── BaseAuditingEntity.java              # Base with audit fields
```

#### Employee Models (5 files)
```
model/employee/
  ├── Employee.java                        # Main employee entity
  ├── Position.java                        # Job position
  ├── Qualification.java                   # Education qualification
  ├── Experience.java                      # Work experience
  └── Subject.java                         # Teaching subject (M2M with Employee)
```

#### Enums (3 files)
```
common/enums/
  ├── CoefficientType.java                 # Salary coefficient types
  ├── ColumnDataType.java                  # DataTable column types
  └── MatchType.java                       # Matching criteria types
```

**Subtotal**: 10 files

---

### 2️⃣ APPLICATION LAYER
**Source**: `src/main/java/by/bntu/salaryapp/application/`  
**Target**: `services/employee-service/src/main/java/by/bntu/salaryapp/application/`

#### DTOs - 10 files
```
dto/employee/
  ├── employee/
  │   ├── EmployeeDto.java                 # Employee data transfer object
  │   └── EmployeeFilterDto.java           # Employee filter criteria
  ├── position/
  │   ├── PositionDto.java
  │   └── PositionFilterDto.java
  ├── experience/
  │   ├── ExperienceDto.java
  │   └── ExperienceFilterDto.java
  ├── qualification/
  │   ├── QualificationDto.java
  │   └── QualificationFilterDto.java
  └── subject/
      ├── SubjectDto.java
      └── SubjectFilterDto.java
```

#### Service Interfaces - 5 files
```
service/interfaces/
  ├── employee/
  │   ├── PositionService.java             # Position CRUD interface
  │   ├── QualificationService.java        # Qualification CRUD interface
  │   ├── ExperienceService.java           # Experience CRUD interface
  │   └── SubjectService.java              # Subject CRUD interface
  └── SalaryCalculationService.java        # Salary calculation logic
```

#### Service Implementations - 5 files
```
service/implementations/
  ├── employee/
  │   ├── PositionServiceImpl.java
  │   ├── QualificationServiceImpl.java
  │   ├── ExperienceServiceImpl.java
  │   └── SubjectServiceImpl.java
  └── SalaryCalculationServiceImpl.java
```

**Subtotal**: 20 files

---

### 3️⃣ INFRASTRUCTURE LAYER
**Source**: `src/main/java/by/bntu/salaryapp/infrastructure/`  
**Target**: `services/employee-service/src/main/java/by/bntu/salaryapp/infrastructure/`

#### Repositories - 5 files
```
persistence/repository/
  └── employee/
      ├── EmployeeRepository.java          # JpaRepository for Employee
      ├── PositionRepository.java
      ├── ExperienceRepository.java
      ├── QualificationRepository.java
      └── SubjectRepository.java
```

#### JPA Specifications (Query Builders) - 5 files
```
persistence/specifications/
  └── employee/
      ├── EmployeeSpecification.java       # Dynamic queries for Employee
      ├── PositionSpecification.java
      ├── ExperienceSpecification.java
      ├── QualificationSpecification.java
      └── SubjectSpecification.java
```

#### Mappers - 11 files
```
mapper/
  ├── MapStructConfig.java                 # MapStruct configuration
  └── employee/
      ├── employee/
      │   ├── EmployeeMapper.java          # DTO ↔ Entity mapper
      │   └── EmployeeListMapper.java      # List mapper
      ├── position/
      │   ├── PositionMapper.java
      │   └── PositionListMapper.java
      ├── experience/
      │   ├── ExperienceMapper.java
      │   └── ExperienceListMapper.java
      ├── qualification/
      │   ├── QualificationMapper.java
      │   └── QualificationListMapper.java
      └── subject/
          ├── SubjectMapper.java
          └── SubjectListMapper.java
```

#### Configuration - 4 files
```
config/
  ├── AuditingConfig.java                  # Spring Data Auditing (createdBy, etc.)
  ├── EncoderConfig.java                   # Password encoder bean
  ├── SecurityConfig.java                  # JWT & CORS configuration
  └── DataInitializer.java                 # Optional: initial data loader
```

#### Entry Points & Filters - 2 files
```
entryPoints/
  └── JwtAuthenticationEntryPoint.java     # JWT error handler

filter/
  └── JwtAuthenticationFilter.java         # JWT token validation filter
```

**Subtotal**: 27 files

---

### 4️⃣ PRESENTATION LAYER (REST API)
**Source/Target**: `src/main/java/by/bntu/salaryapp/presentation/`

#### API Constants - 1 file
```
common/
  └── ApiEndpoints.java                    # Endpoint path constants
```

#### Controllers - 5 files (⚠️ NEED TO CREATE)
```
rest/employee/
  ├── EmployeeController.java              # GET/POST/PUT/DELETE /employees
  ├── PositionController.java              # GET/POST/PUT/DELETE /positions
  ├── ExperienceController.java            # GET/POST/PUT/DELETE /experiences
  ├── QualificationController.java         # GET/POST/PUT/DELETE /qualifications
  └── SubjectController.java               # GET/POST/PUT/DELETE /subjects
```

**Note**: Controllers don't exist in monolith yet and need to be created based on user-service pattern.

**Subtotal**: 6 files (1 existing + 5 to create)

---

### 5️⃣ SUPPORT & CONFIGURATION FILES

#### Java Classes - 2 files
```
src/main/java/by/bntu/salaryapp/
  ├── SalaryAppApplication.java            # Entry point (rename for microservice)
  └── JwtAuthenticationResponse.java       # JWT response DTO
```

#### Database Migrations - Variable
```
src/main/resources/
  └── db/changelog/
      ├── 01-create-employees-table.yaml
      ├── 02-create-positions-table.yaml
      ├── 03-create-qualifications-table.yaml
      ├── 04-create-experience-table.yaml
      ├── 05-create-subjects-table.yaml
      └── 06-employee-subject-many-to-many.yaml
      (Copy all employee-related migration files)
```

#### Properties Files - 2 files
```
src/main/resources/
  ├── application.properties                # Local development config
  └── application-docker.properties         # Docker environment config
```

**Subtotal**: 4+ files

---

## 📊 SUMMARY BY CATEGORY

| Category | Count | Examples |
|----------|-------|----------|
| **Domain Models** | 7 | Employee, Position, Subject, etc. |
| **DTOs** | 10 | EmployeeDto, PositionDto, etc. |
| **Service Interfaces** | 5 | PositionService, QualificationService |
| **Service Implementations** | 5 | *ServiceImpl classes |
| **Repositories** | 5 | *Repository JPA interfaces |
| **Specifications** | 5 | Dynamic query builders |
| **Mappers** | 11 | MapStruct entity↔DTO converters |
| **Configuration** | 4 | Security, Auditing, Encoder |
| **Filters/Entry Points** | 2 | JWT authentication |
| **Controllers** | 5 | REST endpoints (to create) |
| **Support** | 6 | Entry point, DTOs, migrations, properties |
| **TOTAL** | **65+** | — |

---

## 🔄 PACKAGE STRUCTURE FOR EMPLOYEE-SERVICE

```
services/employee-service/src/main/java/by/bntu/salaryapp/
│
├── domain/
│   ├── model/
│   │   ├── BaseEntity.java
│   │   ├── BaseAuditingEntity.java
│   │   └── employee/
│   │       ├── Employee.java
│   │       ├── Position.java
│   │       ├── Qualification.java
│   │       ├── Experience.java
│   │       └── Subject.java
│   └── common/
│       └── enums/
│           ├── CoefficientType.java
│           ├── ColumnDataType.java
│           └── MatchType.java
│
├── application/
│   ├── dto/
│   │   └── employee/
│   │       ├── employee/
│   │       ├── position/
│   │       ├── experience/
│   │       ├── qualification/
│   │       └── subject/
│   └── service/
│       ├── interfaces/
│       │   ├── employee/
│       │   └── SalaryCalculationService.java
│       └── implementations/
│           ├── employee/
│           └── SalaryCalculationServiceImpl.java
│
├── infrastructure/
│   ├── persistence/
│   │   ├── repository/employee/
│   │   └── specifications/employee/
│   ├── mapper/employee/
│   ├── config/
│   ├── entryPoints/
│   └── filter/
│
├── presentation/
│   ├── common/
│   │   └── ApiEndpoints.java
│   └── rest/
│       ├── employee/ (controllers to create)
│       └── auth/
│
├── SalaryAppApplication.java
└── JwtAuthenticationResponse.java

src/main/resources/
├── db/changelog/ (migration files)
├── application.properties
└── application-docker.properties
```

---

## 🔗 CROSS-SERVICE DEPENDENCIES

### Will Need from Other Microservices:
1. **User Service** - User entity, authentication info
2. **DataTable Service** - When implementing Row ↔ Employee relationships
3. **Config Server** - External configuration properties

### Shared Infrastructure Code to Copy:
- Security configuration (JWT, CORS)
- Auditing configuration
- Authentication filters
- Base entity classes

---

## ⚡ Next Steps

1. ✅ **Create directory structure** in `services/employee-service/src/main/java/`
2. 📋 **Copy all files** from monolith following the structure above
3. 🔧 **Refactor imports** to match new package location
4. 📝 **Create REST controllers** (not present in monolith)
5. ⚙️ **Update application.properties** for microservice configuration
6. 🔐 **Configure service registration** with Eureka server
7. 🧪 **Test dependencies** between services

---

## 📌 Key Files by Priority

### CRITICAL (Copy First - 17 files)
- Base classes: `BaseEntity.java`, `BaseAuditingEntity.java`
- Core models: `Employee.java`, `Position.java`, `Subject.java`, etc.
- Core services: `*ServiceImpl.java` files
- Core repositories: `*Repository.java` files

### HIGH (17 files)
- All DTOs for data transfer
- MapStruct mappers
- Specifications for queries

### MEDIUM (15 files)
- Service interfaces
- Configuration files
- Filters & entry points

### LOW (5 files)
- REST Controllers (create new, not copy)
- Entry point classes

---

## 🚀 Implementation Order
1. **Domain Layer** → Business entities
2. **Infrastructure Layer** → Database & mappers
3. **Application Layer** → Business logic
4. **Presentation Layer** → REST APIs

