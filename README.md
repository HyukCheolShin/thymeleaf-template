# IPRO Project (Spring Boot Web Template)

## 1. 프로젝트 개요 (Overview)
**IPRO Project**는 엔터프라이즈급 웹 애플리케이션 구축을 위해 설계된 **Spring Boot 3.5 기반의 템플릿 프로젝트**입니다.
복잡한 비즈니스 요구사항을 유연하게 수용할 수 있도록 **Multi Datasource**, **Redis 세션 클러스터링**, **보안(Security/Jasypt)**, **공통 예외 처리** 등 견고한 인프라가 사전에 구성되어 있습니다.

개발자는 초기 설정이나 인프라 구축에 시간을 쏟는 대신, 핵심 비즈니스 로직(User, File, Secondary 등) 개발에 즉시 집중할 수 있습니다.

---

## 2. 기술 스택 (Tech Stack)

### Backend
| 기술 (Technology) | 버전 (Version) | 설명 (Description) |
| --- | --- | --- |
| **Java** | **21 (LTS)** | 최신 모던 자바 문법 및 가상 스레드 활용 가능 |
| **Spring Boot** | **3.5.9** | 최신 스프링 부트 프레임워크 |
| **MyBatis** | 3.0.5 | SQL 매퍼 프레임워크 (Primary/Secondary 분리 구성) |
| **PostgreSQL** | Latest | 데이터베이스 (다중 데이터소스 지원) |
| **Spring Security** | 6.x | 인증(Authentication) 및 인가(Authorization) |
| **Spring Session** | 3.x | Redis 기반 분산 세션 관리 (운영 환경 필수) |
| **HikariCP** | Latest | 고성능 JDBC 커넥션 풀 |
| **P6Spy** | 1.12.1 | 쿼리 파라미터 바인딩 로깅 및 실행 시간 측정 |
| **Jasypt** | 3.0.5 | 설정 파일(YAML) 내 민감 정보 암호화 |

### Frontend
| 기술 (Technology) | 버전 (Version) | 설명 (Description) |
| --- | --- | --- |
| **Thymeleaf** | 3.1 | 서버 사이드 템플릿 엔진 (Layout Dialect 적용) |
| **Bootstrap** | 5.3 | 반응형 UI 및 컴포넌트 라이브러리 |
| **jQuery** | 3.7.1 | DOM 제어 및 AJAX 통신 간소화 |

---

## 3. 핵심 아키텍처 및 기능 (Architecture & Features)

### 3.1. 멀티 데이터소스 (Multi Datasource)
비즈니스 데이터와 보조(로그/이력) 데이터를 물리적으로 분리하여 관리합니다. 필요에 따라 Secondary DB를 켜거나 끌 수 있습니다.

- **Primary DB (Port 5432)**
  - **역할**: 회원, 주문, 파일 메타데이터 등 핵심 비즈니스 데이터 처리
  - **Java Config**: `PrimaryDatabaseConfig.java`
  - **Mapper 패키지**: `kr.co.aia.dmd.ipro` (Secondary 패키지 제외)
- **Secondary DB (Port 5433)**
  - **역할**: 대용량 로그, 이력성 데이터, 타 시스템 연동 데이터 등
  - **Java Config**: `SecondaryDatabaseConfig.java`
  - **Mapper 패키지**: `kr.co.aia.dmd.ipro.secondary.mapper`
  - **조건부 활성화**: `application.yaml`의 **`app.datasource.secondary.enabled`** 속성으로 활성화 여부를 제어합니다. (기본값: `false`)

### 3.2. 엔터프라이즈 보안 (Security)
- **인증(Authentication)**: `CustomUserDetailsService`를 통해 DB에 저장된 사용자 정보로 인증을 수행합니다.
- **암호화**:
  - **비밀번호**: BCrypt 해싱 알고리즘을 적용하여 안전하게 저장합니다.
  - **설정 파일**: Jasypt(`PBEWithMD5AndDES`)를 사용하여 DB 접속 정보, Redis 비밀번호 등 민감 데이터를 암호화(`ENC(...)`)하여 관리합니다.
- **웹 보안**: CSRF 토큰 자동 적용, XSS 방어(Thymeleaf Escaping) 등 기본 보안 정책이 적용되어 있습니다.

### 3.3. 시스템 인프라 (Infrastructure)
- **세션 클러스터링**:
  - `dev`, `prd` 등 서버 환경에서는 **Redis**를 사용하여 다중 인스턴스 간 세션을 공유합니다. (Scale-out 대응)
  - `local-inmemory` 프로파일에서는 Tomcat 내장 세션을 사용하여 Redis 없이도 개발이 가능합니다.
- **요청 로깅 (Request Logging)**:
  - `LoggingInterceptor`가 모든 API 요청을 가로채어 `[HTTP Method] [URI] [실행시간ms]` 형태의 로그를 남깁니다. 성능 모니터링에 유용합니다.
- **공통 예외 처리 (Global Exception Handling)**:
  - `GlobalExceptionHandler`(`@RestControllerAdvice`)에서 발생하는 모든 예외를 포착합니다.
  - `ResourceNotFoundException`, `MaxUploadSizeExceededException` 등 특정 예외를 핸들링하여 표준화된 JSON 응답(`ApiResponseDto`)을 반환합니다.

---

## 4. 프로젝트 구조 (Project Structure)

소스 코드는 `common`(공통 인프라)과 `ipro`(비즈니스 로직) 영역으로 명확하게 구분되어 있습니다.

```text
src/main/java/kr/co/aia/dmd
├── common                  # [공통] 프로젝트 전반에서 사용되는 인프라 및 유틸리티
│   ├── config              # 스프링 설정 (Security, DB, Redis, Jasypt, WebMvc)
│   ├── dto                 # 공통 DTO (PageRequest, ApiResponse 등)
│   ├── exception           # 전역 예외 처리 및 커스텀 예외 클래스
│   ├── interceptor         # 요청 로깅 및 가로채기
│   └── map                 # MyBatis용 Map 유틸리티 (CamelCaseMap 등)
├── ipro                    # [비즈니스] 핵심 업무 로직 모듈
│   ├── user                # [모듈] 사용자 관리 (Primary DB)
│   │   ├── controller      # View 및 API 컨트롤러
│   │   ├── service         # 비즈니스 로직
│   │   ├── mapper          # MyBatis 인터페이스
│   │   └── vo              # Value Object
│   ├── file                # [모듈] 파일 관리
│   │   └── controller      # 파일 업로드/다운로드 API
│   └── secondary           # [모듈] 보조 DB 예시 (Secondary DB)
│       ├── controller      # 조건부 빈 생성 적용됨
│       ├── service         # 조건부 빈 생성 적용됨
│       └── mapper          # Secondary 전용 매퍼
```

---

## 5. 모듈별 상세 기능 (Module Details)

### **A. 사용자 모듈 (User Module)**
- **로그인/로그아웃**: Spring Security 연동 Form Login (Session 기반)
- **사용자 관리**:
    - 목록 조회 (페이징, 검색어 필터링)
    - 상세 조회 및 정보 수정
    - 신규 사용자 등록
    - 사용자 삭제
- **중복 체크**: ID/Email 중복 검사 API 제공

### **B. 파일 모듈 (File Module)**
- **표준 업로드**: `file.upload-dir` 설정 경로(기본: `C:/apps/template/uploads/` 또는 `${user.home}...`)에 파일을 저장합니다.
- **보안**: 업로드된 파일명은 UUID로 변환되어 저장되며, DB에는 원본 파일명과 저장 경로가 기록됩니다.
- **다운로드**: 고유 ID(Ref)를 통해 저장된 파일을 스트리밍 방식으로 다운로드합니다.

### **C. Secondary DB 모듈 (Example)**
- **목적**: 멀티 DB 접속 및 트랜잭션 분리 패턴을 보여주는 레퍼런스 모듈입니다.
- **기능**: Secondary DB 연결 상태 확인 및 현재 시간 조회 API (`/api/secondary/test`)
- **제어**: 사용하지 않을 경우 `app.datasource.secondary.enabled: false` 설정으로 리소스를 절약할 수 있습니다.

---

## 6. 환경 설정 및 프로파일 (Configuration)

### 6.1. 프로파일 (Profile)
| 프로파일 | 파일명 | 용도 | 특징 |
| --- | --- | --- | --- |
| **local-inmemory** | `application-local-inmemory.yaml` | 로컬 개발 (기본) | Redis 미사용, Tomcat 세션, Jasypt/평문 혼용 가능 |
| **local-redis** | `application-local-redis.yaml` | 로컬 검증 | 로컬에서 Redis 연동 및 세션 클러스터링 테스트 시 사용 |
| **dev / stg / prd** | `application-{env}.yaml` | 서버 배포 | Redis 세션 필수, Jasypt 암호화 필수, 운영 경로 사용 |

### 6.2. 주요 설정 속성 (Properties)
- **`app.datasource.secondary.enabled`**: Secondary DB 사용 여부 (true: 사용, false: 미사용)
- **`file.upload-dir`**: 파일 업로드 경로
- **`logging.level.root`**: 로깅 레벨 설정

---

## 7. 실행 및 테스트 (How to Run)

### 7.1. 사전 준비 (Prerequisites)
- **Java 21** JDK 설치 확인
- **PostgreSQL** 설치 및 DB 생성 (`ipro` 등)
- (선택) Redis (로컬 테스트 시 `local-redis` 프로파일 사용 시 필요)

### 7.2. 실행 명령어 (Command)
```bash
# 1. 기본 실행 (로컬 모드 - In-Memory Session)
./mvnw spring-boot:run

# 2. Redis 모드로 실행 (Redis 구동 필요)
./mvnw spring-boot:run -Dspring-boot.run.profiles=local-redis

# 3. 암호화 키 적용 실행 (배포 시 필수)
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Djasypt.encryptor.password=your_secret_key"

# 4. 배포용 패키징 (JAR 생성)
./mvnw clean package
```

### 7.3. 주요 테스트 URL
- **메인/로그인**: `http://localhost:8080/`
- **사용자 목록**: `http://localhost:8080/user/list`
- **Secondary DB 테스트**: `http://localhost:8080/api/secondary/test` (Enabled 상태일 때만 접근 가능)
