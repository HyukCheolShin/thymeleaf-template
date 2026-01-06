# Thymeleaf Template Project - Developer Guide

## 1. Project Overview
이 프로젝트는 **Spring Boot 3.5.9**와 **Thymeleaf**, **MyBatis**를 기반으로 한 웹 애플리케이션 템플릿입니다.  
보안(Security), 세션 관리(Redis), 로깅, 예외 처리가 사전에 구성되어 있어 빠른 개발 착수가 가능합니다.

---

## 2. Technical Stack

### Backend
- **Language**: Java 21
- **Framework**: Spring Boot 3.5.9
- **Database**: PostgreSQL (Driver included)
- **ORM**: MyBatis 3.0.5
- **Cache/Session**: Redis (Spring Session)
- **Security**: Spring Security 6
- **Template Engine**: Thymeleaf (with Security Extras)
- **Build Tool**: Maven

### Frontend
- **HTML/CSS/JS**: Vanilla (Thymeleaf Integration)

---

## 3. Project Structure (Architecture)

```
com.example.template
├── common                  # 공통 모듈
│   ├── dto                 # 공통 DTO (ApiResponse 등)
│   ├── exception           # 전역 예외 처리 (GlobalExceptionHandler)
│   ├── interceptor         # 웹 인터셉터 (LoggingInterceptor)
│   └── map                 # MyBatis용 맵핑 클래스
├── config                  # 설정 클래스
│   ├── RedisConfig.java    # Redis 및 세션 직렬화 설정 (JSON)
│   ├── SecurityConfig.java # Spring Security 설정
│   └── WebMvcConfig.java   # Web MVC 설정 (Interceptor 등록)
├── controller              # 웹 컨트롤러
├── service                 # 비즈니스 로직
├── mapper                  # MyBatis 매퍼 인터페이스
└── ThymeleafTemplateApplication.java # 메인 클래스
```

---

## 4. Configuration & Profiles

### `application.yaml`
- **Profiles**:
    - `default`: 기본 설정 (DB 연결 등)
    - `redis`: Redis 세션 사용 시 활성화 필요
- **Database**:
    - URL: `jdbc:postgresql://localhost:5432/postgres`
    - Username/Password: `postgres` / `1234`
- **Redis**:
    - Host/Port: `localhost:6379`
    - Session Serializer: JSON (가독성 확보)

---

## 5. Development Guidelines

### 5.1. Naming Conventions
- **Class**: PascalCase (e.g., `UserService`)
- **Method/Variable**: camelCase (e.g., `findUserById`)
- **DB Table/Column**: snake_case (e.g., `user_info`)
- **URL**: kebab-case (e.g., `/api/user-profiles`)

### 5.2. Exception Handling
- 모든 예외는 `GlobalExceptionHandler`에서 처리됩니다.
- 비즈니스 로직에서 예외 발생 시 `IllegalArgumentException` 또는 커스텀 예외를 throw 하십시오.
- 클라이언트 응답 포맷: `ApiResponse<T>`

### 5.3. Session Management
- **Redis**를 세션 저장소로 사용합니다.
- `redis` 프로필이 활성화되어야 동작합니다.
- 모든 세션 데이터는 **JSON**으로 직렬화되어 저장되므로 `redis-cli`에서 가독성이 보장됩니다.

---

## 6. How to Run

### Prerequisites
1. **Java 21** Installed
2. **PostgreSQL** Running (Port 5432)
3. **Redis** Running (Port 6379)

### Run Application
```bash
# 기본 실행
./mvnw spring-boot:run

# Redis 프로필 활성화 실행 (세션 연동 시 필수)
./mvnw spring-boot:run -Dspring-boot.run.profiles=redis
```

---

## 7. Troubleshooting

### Redis Serialization Issue
- **증상**: `redis-cli` 조회 시 문자가 깨져 보임.
- **해결**: `RedisConfig`에 `GenericJackson2JsonRedisSerializer`가 적용되어 있는지 확인하십시오.
- **주의**: 직렬화 방식 변경 후 반드시 `redis-cli flushall`로 기존 데이터를 날려야 에러가 발생하지 않습니다.
