# Employee-Service File Copy Checklist

## Quick Reference - Files to Copy by Layer

### 🔴 PRIORITY 1: Domain Layer (10 files)
**Source**: `src/main/java/by/bntu/salaryapp/domain/`

- [ ] `model/BaseEntity.java`
- [ ] `model/BaseAuditingEntity.java`
- [ ] `model/employee/Employee.java`
- [ ] `model/employee/Position.java`
- [ ] `model/employee/Qualification.java`
- [ ] `model/employee/Experience.java`
- [ ] `model/employee/Subject.java`
- [ ] `common/enums/CoefficientType.java`
- [ ] `common/enums/ColumnDataType.java`
- [ ] `common/enums/MatchType.java`

---

### 🟠 PRIORITY 2: Infrastructure - Repositories & Specs (10 files)
**Source**: `src/main/java/by/bntu/salaryapp/infrastructure/`

#### Repositories
- [ ] `persistence/repository/employee/EmployeeRepository.java`
- [ ] `persistence/repository/employee/PositionRepository.java`
- [ ] `persistence/repository/employee/ExperienceRepository.java`
- [ ] `persistence/repository/employee/QualificationRepository.java`
- [ ] `persistence/repository/employee/SubjectRepository.java`

#### Specifications
- [ ] `persistence/specifications/employee/EmployeeSpecification.java`
- [ ] `persistence/specifications/employee/PositionSpecification.java`
- [ ] `persistence/specifications/employee/ExperienceSpecification.java`
- [ ] `persistence/specifications/employee/QualificationSpecification.java`
- [ ] `persistence/specifications/employee/SubjectSpecification.java`

---

### 🟡 PRIORITY 3: Application Layer - DTOs & Services (20 files)

#### DTOs
- [ ] `application/dto/employee/employee/EmployeeDto.java`
- [ ] `application/dto/employee/employee/EmployeeFilterDto.java`
- [ ] `application/dto/employee/position/PositionDto.java`
- [ ] `application/dto/employee/position/PositionFilterDto.java`
- [ ] `application/dto/employee/experience/ExperienceDto.java`
- [ ] `application/dto/employee/experience/ExperienceFilterDto.java`
- [ ] `application/dto/employee/qualification/QualificationDto.java`
- [ ] `application/dto/employee/qualification/QualificationFilterDto.java`
- [ ] `application/dto/employee/subject/SubjectDto.java`
- [ ] `application/dto/employee/subject/SubjectFilterDto.java`

#### Service Interfaces
- [ ] `application/service/interfaces/employee/PositionService.java`
- [ ] `application/service/interfaces/employee/QualificationService.java`
- [ ] `application/service/interfaces/employee/ExperienceService.java`
- [ ] `application/service/interfaces/employee/SubjectService.java`
- [ ] `application/service/interfaces/SalaryCalculationService.java`

#### Service Implementations
- [ ] `application/service/implementations/employee/PositionServiceImpl.java`
- [ ] `application/service/implementations/employee/QualificationServiceImpl.java`
- [ ] `application/service/implementations/employee/ExperienceServiceImpl.java`
- [ ] `application/service/implementations/employee/SubjectServiceImpl.java`
- [ ] `application/service/implementations/SalaryCalculationServiceImpl.java`

---

### 🟢 PRIORITY 4: Infrastructure - Mappers & Config (15 files)

#### Mappers
- [ ] `infrastructure/mapper/MapStructConfig.java`
- [ ] `infrastructure/mapper/employee/employee/EmployeeMapper.java`
- [ ] `infrastructure/mapper/employee/employee/EmployeeListMapper.java`
- [ ] `infrastructure/mapper/employee/position/PositionMapper.java`
- [ ] `infrastructure/mapper/employee/position/PositionListMapper.java`
- [ ] `infrastructure/mapper/employee/experience/ExperienceMapper.java`
- [ ] `infrastructure/mapper/employee/experience/ExperienceListMapper.java`
- [ ] `infrastructure/mapper/employee/qualification/QualificationMapper.java`
- [ ] `infrastructure/mapper/employee/qualification/QualificationListMapper.java`
- [ ] `infrastructure/mapper/employee/subject/SubjectMapper.java`
- [ ] `infrastructure/mapper/employee/subject/SubjectListMapper.java`

#### Configuration Files
- [ ] `infrastructure/config/AuditingConfig.java`
- [ ] `infrastructure/config/EncoderConfig.java`
- [ ] `infrastructure/config/SecurityConfig.java`
- [ ] `infrastructure/config/DataInitializer.java`

---

### 🔵 PRIORITY 5: Infrastructure - Authentication (2 files)
**Source**: `src/main/java/by/bntu/salaryapp/infrastructure/`

- [ ] `entryPoints/JwtAuthenticationEntryPoint.java`
- [ ] `filter/JwtAuthenticationFilter.java`

---

### 🟣 PRIORITY 6: Presentation Layer & Support (7 files)

#### Presentation
- [ ] `presentation/common/ApiEndpoints.java`

#### Support Classes
- [ ] `SalaryAppApplication.java` (rename/refactor)
- [ ] `JwtAuthenticationResponse.java`

#### Resources
- [ ] `src/main/resources/application.properties`
- [ ] `src/main/resources/application-docker.properties`
- [ ] `src/main/resources/db/changelog/` (all employee-related migrations)

---

### 📝 PRIORITY 7: Controllers to CREATE (5 files)
**These don't exist in monolith - create based on UserController pattern**

- [ ] `presentation/rest/employee/EmployeeController.java` → CRUD for employees
- [ ] `presentation/rest/employee/PositionController.java` → CRUD for positions
- [ ] `presentation/rest/employee/ExperienceController.java` → CRUD for experiences
- [ ] `presentation/rest/employee/QualificationController.java` → CRUD for qualifications
- [ ] `presentation/rest/employee/SubjectController.java` → CRUD for subjects

---

## 🔄 Implementation Workflow

### Step 1: Setup Directory Structure
```powershell
mkdir services/employee-service/src/main/java/by/bntu/salaryapp/{domain,application,infrastructure,presentation}
mkdir services/employee-service/src/main/resources/{db/changelog}
```

### Step 2: Copy Priority 1 (Domain Models)
Copy all 10 domain files first - these are foundation for everything else.

### Step 3: Copy Priority 2-5 (Infrastructure & Application)
Copy repositories, specifications, services, DTOs, mappers, config in order.

### Step 4: Copy Priority 6 (Support & Resources)
Copy configuration files and application properties.

### Step 5: Create Priority 7 (Controllers)
Create REST controllers using UserController as reference pattern.

### Step 6: Update Package References
- Replace `by.bntu.salaryapp` with service-specific package if needed
- Update imports in copied files for any service-specific paths

### Step 7: Configure Microservice
- Update `application.properties` with service-specific settings
- Configure Eureka client registration
- Set up service port
- Configure database connection for employee-service

### Step 8: Add Service Dependencies
- Copy parent `build.gradle` configuration
- Add inter-service communication dependencies if needed

---

## 📊 File Count Summary

| Category | Count | Priority |
|----------|-------|----------|
| Domain Models | 10 | 🔴 P1 |
| Repositories | 5 | 🟠 P2 |
| Specifications | 5 | 🟠 P2 |
| DTOs | 10 | 🟡 P3 |
| Service Interfaces | 5 | 🟡 P3 |
| Service Implementations | 5 | 🟡 P3 |
| Mappers | 11 | 🟢 P4 |
| Configuration | 4 | 🟢 P4 |
| Filters/Entry Points | 2 | 🔵 P5 |
| Support Files | 3 | 🟣 P6 |
| Controllers (CREATE) | 5 | 📝 P7 |
| Resources | 2+ | 🟣 P6 |
| **TOTAL** | **67+** | — |

---

## ✅ Validation Checklist After Copying

- [ ] All domain models compile without errors
- [ ] All DTOs follow the same pattern as existing ones
- [ ] All service interfaces have implementations
- [ ] All repositories extend JpaRepository
- [ ] All mappers are MapStruct annotated
- [ ] Security configuration is in place
- [ ] JWT filter is properly configured
- [ ] Application properties are updated
- [ ] Database migrations are in place
- [ ] REST controllers are created
- [ ] Service registers with Eureka
- [ ] Cross-service communication is configured

---

## 🔗 Reference Paths

**Monolith Source Root**:
```
c:\Users\disable\IdeaProjects\salary-app\
```

**Employee Service Target**:
```
c:\Users\disable\IdeaProjects\salary-app\services\employee-service\
```

**Package Path**:
```
src/main/java/by/bntu/salaryapp/
```

