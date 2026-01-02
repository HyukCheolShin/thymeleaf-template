# Thymeleaf Template Project Guide

본 문서는 **AIA생명 AA(Application Architecture)**팀에서 제공하는 **Spring Boot 웹 애플리케이션 표준 템플릿** 가이드입니다.
프로젝트에 참여하는 모든 개발자는 본 가이드를 숙지하고 개발 표준을 준수해 주시기 바랍니다.

---

## 1. 개요 (Overview)
이 프로젝트는 Spring Boot, Thymeleaf, MyBatis를 기반으로 구성된 웹 애플리케이션 템플릿입니다.
최신 Java 생태계(JDK 21)와 모던 프론트엔드 패턴(ES6+)을 적용하여 생산성과 유지보수성을 극대화하도록 설계되었습니다.

## 2. 기술 스택 (Tech Stack)

| 구분 | 기술 | 버전 | 비고 |
| :--- | :--- | :--- | :--- |
| **Language** | Java | **21** | LTS 버전 |
| **Framework** | Spring Boot | 3.5.9 | Spring Security 6 포함 |
| **ORM** | MyBatis | 3.0.5 | PostgreSQL 연동 |
| **DB** | PostgreSQL | - | |
| **Template Engine** | Thymeleaf | - | Server-side Rendering |
| **Frontend** | jQuery, Bootstrap | 3.7.1, 5.x | **ES6+ 문법 필수** |
| **Build Tool** | Maven | - | |

## 3. 핵심 아키텍처 및 설정 (Architecture & Config)

### 3.1. Layered Architecture
표준적인 계층형 아키텍처를 따릅니다.
- **Controller** (`com.example.template.controller`): 웹 요청 처리 및 뷰 반환.
- **Service** (`com.example.template.service`): 비즈니스 로직 및 트랜잭션 관리.
- **Mapper** (`com.example.template.mapper`): SQL 매핑 및 DB 접근 (MyBatis).
- **Common** (`com.example.template.common`): 전역 예외 처리, 유틸리티, 설정 등.

### 3.2. Security Configuration (`SecurityConfig.java`)
- **Authentication**: `CustomUserDetailsService`를 통해 DB(`users` 테이블) 기반 인증을 수행합니다.
- **Password Encoding**: `BCryptPasswordEncoder`를 사용하여 비밀번호를 안전하게 암호화합니다.
- **Login/Logout**:
    - 로그인 페이지: `/login` (커스텀 디자인 적용)
    - 로그아웃: `/logout` (세션 무효화)
    - 루트 경로(`/`) 접속 시 로그인 여부에 따라 `/users` 또는 `/login`으로 리다이렉트됩니다.
- **Access Control**: `/api/**`, `/users/**` 등 주요 비즈니스 로직은 인증된 사용자만 접근 가능합니다.

### 3.3. Logging (`LoggingInterceptor.java`)
- `WebMvcConfig`에 등록된 인터셉터를 통해 모든 HTTP 요청의 처리 시간을 측정하고 로깅합니다.
- **Log Pattern**: `[REQUEST] [METHOD] URL Duration`

### 3.4. Session Storage Strategy (중요)
본 프로젝트는 **Runtime Auto Detection**을 통해 별도의 프로필 설정 없이 환경에 적응합니다.
- **Auto Detection Logic**: 애플리케이션 시작 시(`main` 메소드) `localhost:6379` 연결을 시도합니다.
    - **연결 성공**: `spring.session.store-type=redis`로 설정 (Redis 세션 사용).
    - **연결 실패**: `spring.session.store-type=none`으로 설정 및 `RedisAutoConfiguration` 제외 (내장 Tomcat 세션 사용).
- **장점**: 개발자는 Redis 설치 여부와 관계없이 동일한 명령어로 애플리케이션을 실행할 수 있습니다.

## 4. 개발 표준 가이드 (Development Standards)

### 4.1. Java (Backend)
- **Stream API 사용**: 반복문이나 컬렉션 처리 시 가독성을 위해 Stream API 사용을 권장합니다.
- **타입 명시**: `var` 키워드 사용을 지양하고, 명시적인 타입(`Map<String, Object>` 등)을 선언하여 가독성을 높입니다.
- **Map 기반 DTO**: 유연한 데이터 처리를 위해 `Map` 및 `CamelCaseMap`을 적극 활용하되, 필요한 경우 명시적 DTO 클래스를 생성합니다.

```java
// Good Example (UserService.java)
Stream.of("name", "email").filter(...).findFirst().ifPresent(...);
```

### 4.2. JavaScript (Frontend)
- **ES6+ 문법 필수**: `var` 대신 `const`, `let`을 사용하고, `Arrow Function`(`=>`)을 사용합니다.
- **함수 정의 순서 (중요)**: `const`로 선언된 함수는 호이스팅(Hoisting)되지 않으므로, **반드시 호출하기 전에 정의**해야 합니다. (TDZ 방지)
- **Template Literals**: 문자열 연결 시 `+` 연산자 대신 백틱(`` ` ``)을 사용합니다.

```javascript
/* Good Example */
$(() => {
    // 1. 함수 정의
    const loadData = () => { ... };

    // 2. 로직 실행 (정의 이후 호출)
    loadData(); 
});
```

## 5. 주요 폴더 구조 (Directory Structure)

```
src/main/
├── java/com/example/template/
│   ├── common/             # 공통 모듈 (Exception, Interceptor, Utopia)
│   ├── config/             # Spring 설정 (Security, WebMvc 등)
│   ├── controller/         # Web Controller
│   ├── mapper/             # MyBatis Client Interface
│   └── service/            # Business Logic
└── resources/
    ├── mapper/             # MyBatis XML Files
    ├── static/             # JS, CSS, Images
    ├── templates/          # Thymeleaf HTML
    └── application.yaml    # 통합 설정 (DB, Log, Session 등)
```

## 6. 시작하기 (Getting Started)

1. **DB 설정**: `src/main/resources/application.yaml` 파일에서 PostgreSQL 접속 정보를 본인 환경에 맞게 수정합니다.
2. **데이터 초기화**: `src/main/resources/sql/user.sql` 스크립트를 실행하여 테이블 생성 및 기초 데이터를 적재합니다.
3. **빌드**: 프로젝트 루트에서 다음 명령어를 실행합니다.
   ```bash
   ./mvnw clean package
   ```
3. **빌드**: 프로젝트 루트에서 다음 명령어를 실행합니다.
   ```bash
   ./mvnw clean package
   ```
4. **실행 (Execution)**:
   - 별도의 옵션 없이 실행하면, 애플리케이션이 자동으로 환경을 감지합니다.
     ```bash
     java -jar target/thymeleaf-template-0.0.1-SNAPSHOT.jar
     ```
5. **접속**: 브라우저에서 `http://localhost:8080/users` 접속.

---
**Written by Application Architect**
