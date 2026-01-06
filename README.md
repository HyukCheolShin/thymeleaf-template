# Thymeleaf 템플릿 프로젝트 - 개발자 가이드

## 1. 프로젝트 개요 (Project Overview)
이 프로젝트는 **Spring Boot 3.5.9**와 **Thymeleaf**, **MyBatis**, **PostgreSQL**을 기반으로 한 웹 애플리케이션 템플릿입니다.  
보안, 세션 관리, 로깅, 예외 처리 등 필수 기능이 사전 구성되어 있어 즉시 비즈니스 로직 개발이 가능합니다.

---

## 2. 기술 스택 (Technical Stack)

### 백엔드 (Backend)
- **프레임워크**: Spring Boot 3.5.9 (Java 21)
- **퍼시스턴스**: MyBatis 3.0.5, PostgreSQL
- **보안**: Spring Security 6 (BCrypt, CSRF)
- **세션**: Spring Session Data Redis
- **유틸리티**: Lombok, Jasypt 3.0.5 (설정 암호화)

### 프론트엔드 (Frontend)
- **템플릿 엔진**: Thymeleaf + Extras Spring Security
- **라이브러리**: jQuery 3.7.1, Bootstrap 5.3
- **아키텍처**: 모듈화된 JavaScript & AJAX Wrapper

---

## 3. 주요 기능 (Key Features)

### 3.1. 핵심 기능 (Core Features)
- **사용자 관리**: 사용자 목록, 상세, 등록(파일첨부), 수정, 삭제 (전체 CRUD)
- **파일 업로드**: 로컬 파일 시스템 저장 및 DB 메타데이터 관리
- **로그인/인증**: Form 기반 로그인, 로그아웃, 접근 제어

### 3.2. 아키텍처 및 인프라 (Architecture & Infrastructure)
- **요청 로깅**: `LoggingInterceptor`를 통해 모든 요청의 처리 시간을 측정 및 로깅 (정적 리소스 제외)
- **설정 암호화**: `Jasypt`를 사용하여 민감한 설정 정보(DB 패스워드 등) 암호화 (`ENC(...)`)
- **전역 예외 처리**: `GlobalExceptionHandler`에서 비즈니스 및 시스템 예외 중앙 처리

### 3.3. 세션 관리 정책 (Session Management Policy)
- **글로벌 타임아웃**: **30분** (모든 환경 공통 적용)
- **In-Memory 모드** (`local-inmemory`): Tomcat Native Session 사용 (Spring Session 비활성화로 성능 최적화)
- **Redis 모드** (`local-redis`, `dev`, `stg`, `prd`): Spring Session + Redis 연동

### 3.4. 프론트엔드 아키텍처 (Frontend Architecture)
- **API Wrapper**: `api.js`의 `App.get/post/put/delete`를 통한 표준화된 비동기 통신
- **모듈 (Modules)**:
  - `ui.js`: 페이징 렌더링 등 UI 제어
  - `utils.js`: 날짜 포맷팅 등 유틸리티

---

## 4. 프로젝트 구조 (Project Structure)
```
src/main/java/com/example/template
├── common                  # 공통 모듈 (Interceptor, Exception, DTO)
├── config                  # 설정 (Redis, Security, WebMvc, Jasypt)
├── controller              # API 및 View 컨트롤러
├── service                 # 비즈니스 로직
└── mapper                  # MyBatis Mapper Interface
```

---

## 5. 실행 방법 (How to Run)

### 사전 요구사항 (Prerequisites)
- Java 21+
- PostgreSQL (Port 5432)
- Redis (`local-inmemory` 사용 시 선택 사항)

### 실행 명령어 (Run Commands)
1. **로컬 개발 (In-Memory Session)** - *빠른 개발용 (권장)*
   ```bash
   ./mvnw spring-boot:run
   ```

2. **로컬 개발 (Redis Session)** - *Redis 연동 테스트용*
   ```bash
   ./mvnw spring-boot:run -Dspring-boot.run.profiles=local-redis
   ```
