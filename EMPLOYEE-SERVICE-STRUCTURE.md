# Employee-Service Complete Directory Structure

## Current Monolith Structure (Source)

```
src/main/java/by/bntu/salaryapp/
│
├── domain/
│   ├── common/
│   │   └── enums/
│   │       ├── CoefficientType.java
│   │       ├── ColumnDataType.java
│   │       └── MatchType.java
│   ├── model/
│   │   ├── BaseEntity.java
│   │   ├── BaseAuditingEntity.java
│   │   ├── JwtAuthenticationResponse.java
│   │   ├── dataTable/
│   │   │   ├── Cell.java
│   │   │   ├── CellValue.java
│   │   │   ├── Column.java
│   │   │   ├── DataTable.java
│   │   │   ├── Row.java
│   │   │   ├── Coefficient.java
│   │   │   └── CoefficientRule.java
│   │   ├── employee/
│   │   │   ├── Employee.java              ✅ COPY
│   │   │   ├── Position.java              ✅ COPY
│   │   │   ├── Qualification.java         ✅ COPY
│   │   │   ├── Experience.java            ✅ COPY
│   │   │   └── Subject.java               ✅ COPY
│   │   └── user/
│   │       ├── User.java
│   │       └── Role.java
│   │
│   └── (rest of domain files)
│
├── presentation/
│   ├── common/
│   │   ├── ApiEndpoints.java              ✅ COPY
│   │   ├── dataTable/
│   │   ├── employee/
│   │   └── user/
│   └── rest/
│       ├── auth/
│       │   └── AuthController.java
│       ├── dataTable/
│       │   ├── CellController.java
│       │   ├── ColumnController.java
│       │   ├── DataTableController.java
│       │   ├── RowController.java
│       │   └── coefficient/
│       ├── employee/
│       │   └── (EMPTY - Controllers to create)
│       └── user/
│           └── UserController.java
│
├── application/
│   ├── dto/
│   │   ├── dataTable/
│   │   │   └── (dataTable DTOs)
│   │   ├── employee/                      ✅ COPY ALL
│   │   │   ├── employee/
│   │   │   │   ├── EmployeeDto.java
│   │   │   │   └── EmployeeFilterDto.java
│   │   │   ├── position/
│   │   │   │   ├── PositionDto.java
│   │   │   │   └── PositionFilterDto.java
│   │   │   ├── experience/
│   │   │   │   ├── ExperienceDto.java
│   │   │   │   └── ExperienceFilterDto.java
│   │   │   ├── qualification/
│   │   │   │   ├── QualificationDto.java
│   │   │   │   └── QualificationFilterDto.java
│   │   │   └── subject/
│   │   │       ├── SubjectDto.java
│   │   │       └── SubjectFilterDto.java
│   │   └── user/
│   │
│   └── service/
│       ├── exception/
│       │   └── (empty)
│       ├── interfaces/
│       │   ├── dataTable/
│       │   ├── employee/                  ✅ COPY ALL
│       │   │   ├── PositionService.java
│       │   │   ├── QualificationService.java
│       │   │   ├── ExperienceService.java
│       │   │   └── SubjectService.java
│       │   ├── SalaryCalculationService.java  ✅ COPY
│       │   └── user/
│       │
│       └── implementations/
│           ├── dataTable/
│           │   ├── CellServiceImpl.java
│           │   ├── ColumnServiceImpl.java
│           │   ├── DataTableServiceImpl.java
│           │   ├── RowServiceImpl.java
│           │   ├── CoefficientServiceImpl.java
│           │   └── CoefficientRuleServiceImpl.java
│           ├── employee/                  ✅ COPY ALL
│           │   ├── PositionServiceImpl.java
│           │   ├── QualificationServiceImpl.java
│           │   ├── ExperienceServiceImpl.java
│           │   └── SubjectServiceImpl.java
│           ├── SalaryCalculationServiceImpl.java  ✅ COPY
│           └── user/
│               └── UserServiceImpl.java
│
├── infrastructure/
│   ├── config/
│   │   ├── AuditingConfig.java            ✅ COPY
│   │   ├── DataInitializer.java           ✅ COPY
│   │   ├── EncoderConfig.java             ✅ COPY
│   │   └── SecurityConfig.java            ✅ COPY
│   │
│   ├── entryPoints/
│   │   └── JwtAuthenticationEntryPoint.java  ✅ COPY
│   │
│   ├── filter/
│   │   └── JwtAuthenticationFilter.java   ✅ COPY
│   │
│   ├── mapper/
│   │   ├── MapStructConfig.java           ✅ COPY
│   │   ├── dataTable/
│   │   └── employee/                      ✅ COPY ALL
│   │       ├── employee/
│   │       │   ├── EmployeeMapper.java
│   │       │   └── EmployeeListMapper.java
│   │       ├── position/
│   │       │   ├── PositionMapper.java
│   │       │   └── PositionListMapper.java
│   │       ├── experience/
│   │       │   ├── ExperienceMapper.java
│   │       │   └── ExperienceListMapper.java
│   │       ├── qualification/
│   │       │   ├── QualificationMapper.java
│   │       │   └── QualificationListMapper.java
│   │       └── subject/
│   │           ├── SubjectMapper.java
│   │           └── SubjectListMapper.java
│   │
│   └── persistence/
│       ├── repository/
│       │   ├── dataTable/
│       │   │   ├── CellRepository.java
│       │   │   ├── ColumnRepository.java
│       │   │   ├── DataTableRepository.java
│       │   │   ├── RowRepository.java
│       │   │   ├── CoefficientRepository.java
│       │   │   └── CoefficientRuleRepository.java
│       │   ├── employee/                  ✅ COPY ALL
│       │   │   ├── EmployeeRepository.java
│       │   │   ├── PositionRepository.java
│       │   │   ├── ExperienceRepository.java
│       │   │   ├── QualificationRepository.java
│       │   │   └── SubjectRepository.java
│       │   └── user/
│       │       └── UserRepository.java
│       │
│       └── specifications/
│           ├── dataTable/
│           │   ├── CellSpecification.java
│           │   ├── ColumnSpecification.java
│           │   ├── DataTableSpecification.java
│           │   ├── RowSpecification.java
│           │   ├── CoefficientSpecification.java
│           │   └── CoefficientRuleSpecification.java
│           ├── employee/                  ✅ COPY ALL
│           │   ├── EmployeeSpecification.java
│           │   ├── PositionSpecification.java
│           │   ├── ExperienceSpecification.java
│           │   ├── QualificationSpecification.java
│           │   └── SubjectSpecification.java
│           └── user/
│               └── UserSpecification.java
│
├── SalaryAppApplication.java              ✅ COPY & REFACTOR
└── JwtAuthenticationResponse.java         ✅ COPY

src/main/resources/
├── application.properties                 ✅ COPY & MODIFY
├── application-docker.properties          ✅ COPY & MODIFY
├── db/
│   └── changelog/
│       ├── 001-initial-schema.yaml        ✅ COPY
│       ├── 002-create-users-table.yaml    ⚠️  OPTIONAL
│       ├── 003-create-roles-table.yaml    ⚠️  OPTIONAL
│       ├── 004-create-employees-table.yaml    ✅ COPY
│       ├── 005-create-positions-table.yaml    ✅ COPY
│       ├── 006-create-qualifications-table.yaml  ✅ COPY
│       ├── 007-create-experience-table.yaml     ✅ COPY
│       ├── 008-create-subjects-table.yaml       ✅ COPY
│       ├── 009-employee-subject-m2m.yaml        ✅ COPY
│       ├── 010-create-dataTable.yaml        ⚠️  REFERENCE
│       └── ... (more migrations)
│
└── static/ (if any employee-related static files)
```

---

## Target Structure (Employee-Service)

```
services/employee-service/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── by/bntu/salaryapp/
│       │       │
│       │       ├── domain/
│       │       │   ├── common/
│       │       │   │   └── enums/
│       │       │   │       ├── CoefficientType.java
│       │       │   │       ├── ColumnDataType.java
│       │       │   │       └── MatchType.java
│       │       │   └── model/
│       │       │       ├── BaseEntity.java
│       │       │       ├── BaseAuditingEntity.java
│       │       │       └── employee/
│       │       │           ├── Employee.java
│       │       │           ├── Position.java
│       │       │           ├── Qualification.java
│       │       │           ├── Experience.java
│       │       │           └── Subject.java
│       │       │
│       │       ├── application/
│       │       │   ├── dto/
│       │       │   │   └── employee/
│       │       │   │       ├── employee/
│       │       │   │       │   ├── EmployeeDto.java
│       │       │   │       │   └── EmployeeFilterDto.java
│       │       │   │       ├── position/
│       │       │       │   │   ├── PositionDto.java
│       │       │       │   │   └── PositionFilterDto.java
│       │       │       │   ├── experience/
│       │       │       │   │   ├── ExperienceDto.java
│       │       │       │   │   └── ExperienceFilterDto.java
│       │       │       │   ├── qualification/
│       │       │       │   │   ├── QualificationDto.java
│       │       │       │   │   └── QualificationFilterDto.java
│       │       │       │   └── subject/
│       │       │       │       ├── SubjectDto.java
│       │       │       │       └── SubjectFilterDto.java
│       │       │   │
│       │       │   └── service/
│       │       │       ├── interfaces/
│       │       │       │   ├── employee/
│       │       │       │   │   ├── PositionService.java
│       │       │       │   │   ├── QualificationService.java
│       │       │       │   │   ├── ExperienceService.java
│       │       │       │   │   └── SubjectService.java
│       │       │       │   └── SalaryCalculationService.java
│       │       │       │
│       │       │       └── implementations/
│       │       │           ├── employee/
│       │       │           │   ├── PositionServiceImpl.java
│       │       │           │   ├── QualificationServiceImpl.java
│       │       │           │   ├── ExperienceServiceImpl.java
│       │       │           │   └── SubjectServiceImpl.java
│       │       │           └── SalaryCalculationServiceImpl.java
│       │       │
│       │       ├── infrastructure/
│       │       │   ├── config/
│       │       │   │   ├── AuditingConfig.java
│       │       │   │   ├── DataInitializer.java
│       │       │   │   ├── EncoderConfig.java
│       │       │   │   └── SecurityConfig.java
│       │       │   │
│       │       │   ├── entryPoints/
│       │       │   │   └── JwtAuthenticationEntryPoint.java
│       │       │   │
│       │       │   ├── filter/
│       │       │   │   └── JwtAuthenticationFilter.java
│       │       │   │
│       │       │   ├── mapper/
│       │       │   │   ├── MapStructConfig.java
│       │       │   │   └── employee/
│       │       │   │       ├── employee/
│       │       │   │       │   ├── EmployeeMapper.java
│       │       │   │       │   └── EmployeeListMapper.java
│       │       │   │       ├── position/
│       │       │   │       │   ├── PositionMapper.java
│       │       │   │       │   └── PositionListMapper.java
│       │       │   │       ├── experience/
│       │       │   │       │   ├── ExperienceMapper.java
│       │       │   │       │   └── ExperienceListMapper.java
│       │       │   │       ├── qualification/
│       │       │   │       │   ├── QualificationMapper.java
│       │       │   │       │   └── QualificationListMapper.java
│       │       │   │       └── subject/
│       │       │   │           ├── SubjectMapper.java
│       │       │   │           └── SubjectListMapper.java
│       │       │   │
│       │       │   └── persistence/
│       │       │       ├── repository/
│       │       │       │   └── employee/
│       │       │       │       ├── EmployeeRepository.java
│       │       │       │       ├── PositionRepository.java
│       │       │       │       ├── ExperienceRepository.java
│       │       │       │       ├── QualificationRepository.java
│       │       │       │       └── SubjectRepository.java
│       │       │       │
│       │       │       └── specifications/
│       │       │           └── employee/
│       │       │               ├── EmployeeSpecification.java
│       │       │               ├── PositionSpecification.java
│       │       │               ├── ExperienceSpecification.java
│       │       │               ├── QualificationSpecification.java
│       │       │               └── SubjectSpecification.java
│       │       │
│       │       ├── presentation/
│       │       │   ├── common/
│       │       │   │   └── ApiEndpoints.java
│       │       │   └── rest/
│       │       │       ├── employee/
│       │       │       │   ├── EmployeeController.java        [CREATE]
│       │       │       │   ├── PositionController.java        [CREATE]
│       │       │       │   ├── ExperienceController.java      [CREATE]
│       │       │       │   ├── QualificationController.java   [CREATE]
│       │       │       │   └── SubjectController.java         [CREATE]
│       │       │       └── auth/
│       │       │           └── AuthController.java
│       │       │
│       │       ├── SalaryAppApplication.java            [COPY & REFACTOR]
│       │       └── JwtAuthenticationResponse.java       [COPY]
│       │
│       └── resources/
│           ├── application.properties          [COPY & MODIFY]
│           ├── application-docker.properties   [COPY & MODIFY]
│           ├── db/
│           │   └── changelog/
│           │       ├── 001-initial-schema.yaml
│           │       ├── 004-create-employees-table.yaml
│           │       ├── 005-create-positions-table.yaml
│           │       ├── 006-create-qualifications-table.yaml
│           │       ├── 007-create-experience-table.yaml
│           │       ├── 008-create-subjects-table.yaml
│           │       └── 009-employee-subject-m2m.yaml
│           └── static/ (if needed)
│
├── build.gradle
├── Dockerfile
├── settings.gradle
└── [other gradle/build files]
```

---

## Summary of Files by Category

### 📦 DOMAIN LAYER (10 files)
```
base models (2)          │ employee models (5)      │ enums (3)
─────────────────────────┼──────────────────────────┼─────────────────
BaseEntity               │ Employee                 │ CoefficientType
BaseAuditingEntity       │ Position                 │ ColumnDataType
                         │ Qualification            │ MatchType
                         │ Experience               │
                         │ Subject                  │
```

### 🔄 APPLICATION LAYER (20 files)
```
DTOs (10)                           │ Services (10)
────────────────────────────────────┼──────────────────────────────
EmployeeDto                         │ PositionService
EmployeeFilterDto                   │ QualificationService
PositionDto                         │ ExperienceService
PositionFilterDto                   │ SubjectService
ExperienceDto                       │ SalaryCalculationService
ExperienceFilterDto                 │ (and 5 implementations)
QualificationDto                    │
QualificationFilterDto              │
SubjectDto                          │
SubjectFilterDto                    │
```

### 🛠️ INFRASTRUCTURE LAYER (27 files)
```
Repositories (5)         │ Specifications (5)       │ Mappers (11)             │ Config (4)    │ Auth (2)
─────────────────────────┼──────────────────────────┼──────────────────────────┼───────────────┼─────────────
EmployeeRepository       │ EmployeeSpecification    │ EmployeeMapper           │ Auditing      │ Jwt Entry
PositionRepository       │ PositionSpecification    │ EmployeeListMapper       │ Encoder       │ Jwt Filter
ExperienceRepository     │ ExperienceSpecification  │ PositionMapper           │ Security      │
QualificationRepository  │ QualificationSpecif.     │ PositionListMapper       │ DataInit      │
SubjectRepository        │ SubjectSpecification     │ ExperienceMapper         │               │
                         │                          │ ExperienceListMapper     │               │
                         │                          │ QualificationMapper      │               │
                         │                          │ QualificationListMapper  │               │
                         │                          │ SubjectMapper            │               │
                         │                          │ SubjectListMapper        │               │
                         │                          │ MapStructConfig          │               │
```

### 🎯 PRESENTATION LAYER (6 files)
```
Created Files                        │ To Create
─────────────────────────────────────┼─────────────────
ApiEndpoints.java                    │ EmployeeController
JwtAuthenticationResponse.java        │ PositionController
SalaryAppApplication.java (refactor) │ ExperienceController
                                     │ QualificationController
                                     │ SubjectController
```

### 📄 RESOURCES (Migrations & Config)
```
Database Migrations:                 │ Properties Files:
─────────────────────────────────────┼──────────────────────
001-initial-schema.yaml              │ application.properties
004-employees-table.yaml             │ application-docker.properties
005-positions-table.yaml             │
006-qualifications-table.yaml        │
007-experience-table.yaml            │
008-subjects-table.yaml              │
009-employee-subject-m2m.yaml        │
```

---

## ✅ Key Points

1. **Total Files to Copy**: ~60 files
2. **Files to Create**: 5 REST controllers (based on existing patterns)
3. **Files to Modify**: Application.properties, build.gradle, SecurityConfig
4. **Base Entities**: Must copy BaseEntity and BaseAuditingEntity for inheritance chain
5. **Mappers**: All 11 mappers required for DTO ↔ Entity conversion
6. **Database**: Copy all employee-related Liquibase migration files
7. **Security**: Authentication infrastructure needed for all services
8. **Resources**: Configuration and migration files are critical

