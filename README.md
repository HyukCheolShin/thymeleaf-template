# Thymeleaf Template Project - Developer Guide

## 1. Project Overview
이 프로젝트는 **Spring Boot 3.5.9**와 **Thymeleaf**, **MyBatis**를 기반으로 한 웹 애플리케이션 템플릿입니다.  
보안(Security), 세션 관리(Redis), 로깅, 예외 처리가 사전에 구성되어 있어 빠른 개발 착수가 가능합니다.

---

## 2. Technical Stack

### Backend
- **Language**: Java 21
- **Framework**: Spring Boot 3.5.9
- **Database**: PostgreSQL
- **ORM**: MyBatis 3.0.5
- **Session**: Redis
- **Security**: Spring Security 6
- **Template Engine**: Thymeleaf
- **Build Tool**: Maven

### Frontend
- **HTML/CSS/JS**: Vanilla (Thymeleaf Integration)
- **Library**: jQuery 3.7.1
- **UI Framework**: Bootstrap 5

---

## 3. Project Structure (Architecture)

```
com.example.template
├── common                  # 공통 모듈
│   ├── dto                 # 공통 DTO
│   ├── exception           # 전역 예외 처리
│   ├── interceptor         # 웹 인터셉터
│   └── map                 # MyBatis용 맵핑 클래스
├── config                  # 설정 클래스
│   ├── RedisConfig.java    # Redis 및 세션 직렬화 설정
│   ├── SecurityConfig.java # Spring Security 설정
│   └── WebMvcConfig.java   # Web MVC 설정
├── controller              # 웹 컨트롤러
├── service                 # 비즈니스 로직
├── mapper                  # MyBatis 매퍼 인터페이스
└── ThymeleafTemplateApplication.java # 메인 클래스
```

---

## 4. Key Features

### 4.1. User Management
- 사용자 목록, 상세 조회, 수정, 삭제 기능 구현 (CRUD)
- Thymeleaf + Bootstrap 5 기반의 반응형 UI
- 검색 및 페이징 처리

### 4.2. Security & Session
- **Spring Security**: 로그인/로그아웃, BCrypt 패스워드 암호화
- **Redis Session**: 세션 정보를 Redis에 **JSON** 포맷으로 저장 (가독성 확보)
- **CSRF Protection**: 모든 POST/PUT/DELETE 요청에 CSRF 토큰 검증 필수

---

## 5. Development Guidelines

### 5.1. Naming Conventions
- **Class**: PascalCase (e.g., `UserService`)
- **Method/Variable**: camelCase (e.g., `findUserById`)
- **DB Table/Column**: snake_case (e.g., `user_info`)
- **URL**: kebab-case (e.g., `/api/user-profiles`)

### 5.2. Architecture Flow
`Controller` -> `Service` -> `Mapper` -> `Database`
- **Controller**: 요청 검증 및 응답 반환 (`ApiResponse` 사용 권장)
- **Service**: 비즈니스 로직 및 트랜잭션 처리 (`@Transactional`)
- **Mapper**: DB 쿼리 실행 (MyBatis XML)

### 5.3. Exception Handling
- 모든 예외는 `GlobalExceptionHandler`에서 중앙 처리됩니다.
- 비즈니스 로직 예외는 `IllegalArgumentException` 또는 커스텀 예외를 사용하세요.

### 5.4. Frontend (Thymeleaf & JS)
- **Layout**: `nav.html`, `head.html` 등 공통 프래그먼트 재사용 (`th:replace`)
### 5.4. Frontend (Thymeleaf & JS)
- **Modules**: `JS` 파일이 역할별로 분리되어 있습니다.
  - `api.js`: API 호출 및 공통 에러 핸들링 (App.api)
  - `utils.js`: 유틸리티 함수 (날짜 포맷 등)
  - `ui.js`: UI 관련 함수 (페이징 등)
- **AJAX**: `api.js`에 정의된 `App` 헬퍼 메소드를 사용하세요.
  - **GET**: `App.get(url, params, { success: fn })`
  - **POST**: `App.post(url, data, { success: fn })` (JSON 자동 변환)
  - **PUT**: `App.put(url, data, { success: fn })`
  - **DELETE**: `App.delete(url, data, { success: fn })`
  - *Response Code 체크(`if(res.code==='SUCCESS')`)는 내부에서 자동 처리되므로, 성공 로직만 작성하면 됩니다.*

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
