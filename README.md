# IPRO Project (Spring Boot Web Template)

## 1. 프로젝트 개요 (Overview)
**IPRO Project**는 엔터프라이즈급 웹 애플리케이션 구축을 위한 **Spring Boot 3.5 기반의 템플릿 프로젝트**입니다.
복잡한 비즈니스 요구사항을 수용할 수 있도록 **Multi Datasource**, **Redis 세션 클러스터링**, **보안(Security/Jasypt)**, **공통 예외 처리** 등 견고한 인프라가 사전에 구성되어 있습니다.

개발자는 설정이나 인프라 구축에 시간을 쏟는 대신, 핵심 비즈니스 로직(User, File, Secondary 등) 개발에 즉시 집중할 수 있습니다.

---

## 2. 기술 스택 (Tech Stack)

### Backend
| 기술 (Technology) | 버전 (Version) | 설명 (Description) |
| --- | --- | --- |
| **Java** | **21 (LTS)** | 최신 모던 자바 문법 및 가상 스레드 활용 가능 |
| **Spring Boot** | **3.5.9** | 웹 애플리케이션 프레임워크 |
| **MyBatis** | 3.0.5 | SQL 매퍼 프레임워크 (Primary/Secondary 분리) |
| **PostgreSQL** | Latest | 데이터베이스 (다중 데이터소스 지원) |
| **Spring Security** | 6.x | 인증(Authentication) 및 인가(Authorization) |
| **Spring Session** | 3.x | Redis 기반 분산 세션 관리 |
| **HikariCP** | Latest | 고성능 JDBC 커넥션 풀 |
| **Jasypt** | 3.0.5 | 설정 파일(YAML) 내 민감 정보 암호화 |

### Frontend
| 기술 (Technology) | 버전 (Version) | 설명 (Description) |
| --- | --- | --- |
| **Thymeleaf** | 3.1 | 서버 사이드 템플릿 엔진 (Layout Dialect 적용) |
| **Bootstrap** | 5.3 | 반응형 UI 구성 |
| **jQuery** | 3.7.1 | DOM 제어 및 AJAX 통신 |

---

## 3. 핵심 아키텍처 및 기능 (Architecture & Features)

### 3.1. 멀티 데이터소스 (Multi Datasource)
비즈니스 데이터와 보조(로그/이력) 데이터를 물리적으로 분리하여 관리합니다.
- **Primary DB (Port 5432)**
  - 역할: 회원, 주문 등 핵심 비즈니스 데이터 처리
  - Java Config: `PrimaryDatabaseConfig.java`
  - Mapper 패키지: `kr.co.aia.dmd.ipro` (Secondary 제외)
- **Secondary DB (Port 5433)**
  - 역할: 대용량 로그, 이력성 데이터 처리
  - Java Config: `SecondaryDatabaseConfig.java`
  - Mapper 패키지: `kr.co.aia.dmd.ipro.secondary.mapper`

### 3.2. 엔터프라이즈 보안 (Security)
- **인증(Authentication)**: `CustomUserDetailsService`를 통한 DB 기반 사용자 인증
- **암호화**:
  - 비밀번호: BCrypt 해싱 알고리즘 적용
  - 설정 파일: Jasypt(`PBEWithMD5AndDES`)를 사용하여 DB 접속 정보 등 민감 데이터 암호화 (`ENC(...)`)
- **보호**: CSRF 토큰 자동 적용, XSS 방어(Thymeleaf Escaping)

### 3.3. 시스템 인프라 (Infrastructure)
- **세션 클러스터링**:
  - `dev`, `prd` 환경: **Redis**를 사용하여 다중 서버 간 세션 공유 (Spring Session)
  - `local` 환경: In-Memory (Tomcat) 세션으로 간편한 개발 지원
- **표준 로깅**:
  - `LoggingInterceptor`를 통해 모든 API 요청의 URI, 파라미터, 처리 시간을 자동으로 로깅 (MDC 활용 가능 구조)
- **공통 예외 처리**:
  - `GlobalExceptionHandler`에서 시스템 예외 및 비즈니스 예외를 일괄 포착하여 표준화된 에러 응답 반환

---

## 4. 프로젝트 구조 (Project Structure)

```text
src/main/java/kr/co/aia/dmd
├── common              # 공통 인프라 모듈
│   ├── config          # 스프링 설정 (Security, DB, Redis, Jasypt, WebMvc)
│   ├── dto             # 공통 DTO (PageRequest 등)
│   ├── exception       # 전역 예외 처리
│   └── interceptor     # 요청 로깅 인터셉터
├── ipro                # [모듈] 비즈니스 로직
│   ├── user            # [모듈] 사용자 관리 (Primary DB)
│   │   ├── controller      # View 및 API 컨트롤러 분리
│   │   ├── service         # 비즈니스 로직
│   │   ├── mapper          # MyBatis 인터페이스
│   │   └── vo              # Value Object
│   ├── file            # [모듈] 파일 관리
│   │   └── controller      # 파일 업로드/다운로드 API
│   └── secondary       # [모듈] 보조 DB 예시 (Secondary DB)
│       ├── controller
│       ├── service
│       └── mapper          # Secondary 전용 매퍼
```

---

## 5. 모듈별 상세 기능 (Module Details)

### **A. 사용자 모듈 (User Module)**
- **로그인/로그아웃**: Spring Security 연동 Form Login
- **사용자 관리**: 목록 조회(페이징, 검색어), 상세 조회, 등록, 수정, 삭제
- **중복 체크**: ID/Email 중복 검사 API 제공

### **B. 파일 모듈 (File Module)**
- **표준 업로드**: `C:/apps/template/uploads/` (설정 가능) 경로에 파일 저장
- **보안**: 파일명 UUID 변환 저장, 확장자 검증 로직 포함 가능
- **다운로드**: 저장된 파일 리소스 스트리밍 다운로드

### **C. Secondary DB 모듈**
- **목적**: 멀티 DB 접속 패턴을 보여주는 레퍼런스 모듈
- **기능**: Secondary DB 연결 상태 확인 및 현재 시간 조회 API (`/api/secondary/test`)

---

## 6. 환경 설정 및 프로파일 (Configuration)

| 프로파일 (Profile) | 파일명 | 용도 | 특징 |
| --- | --- | --- | --- |
| **local-inmemory** | `application-local-inmemory.yaml` | 로컬 개발 (기본) | Redis 미사용, Tomcat 세션, Jasypt/평문 혼용 가능 |
| **local-redis** | `application-local-redis.yaml` | 로컬 검증 | 로컬에서 Redis 연동 테스트 시 사용 |
| **dev / stg / prd** | `application-{env}.yaml` | 서버 배포 | Redis 세션 필수, Jasypt 암호화 필수, 운영 경로 사용 |

---

## 7. 실행 및 테스트 (How to Run)

### 7.1. 사전 준비 (Prerequisites)
- **Java 21** 설치 확인
- **PostgreSQL** 설치 및 DB 생성 (`ipro` 등)
- (선택) Redis (로컬 테스트 시 `local-redis` 프로파일 사용 시 필요)

### 7.2. 실행 명령어 (Command)
```bash
# 1. 기본 실행 (로컬 모드)
./mvnw spring-boot:run

# 2. Redis 모드로 실행
./mvnw spring-boot:run -Dspring-boot.run.profiles=local-redis

# 3. 암호화 키 적용 실행 (VM Option)
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Djasypt.encryptor.password=test"

# 3. 배포용 패키징 (JAR 생성)
./mvnw clean package
```

### 7.3. 주요 테스트 URL
- **메인/로그인**: `http://localhost:8080/`
- **사용자 목록**: `http://localhost:8080/user/list`
- **Secondary DB 테스트**: `http://localhost:8080/api/secondary/test`
