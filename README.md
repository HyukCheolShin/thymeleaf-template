# Thymeleaf Template Project

## 1. 프로젝트 개요 (Project Overview)
본 프로젝트는 **Spring Boot 3.5.9**와 **Thymeleaf**를 기반으로 구축된 웹 애플리케이션 템플릿입니다.
기본적인 **세션 관리(Redis/In-Memory)**, **로깅**, **예외 처리** 인프라가 구성되어 있어 비즈니스 로직 개발을 즉시 시작할 수 있습니다.

---

## 2. 기술 스택 (Tech Stack)

### Backend
| 기술 (Technology) | 버전 (Version) | 용도 (Usage) |
| --- | --- | --- |
| **Java** | **21 (LTS)** | 프로그래밍 언어 |
| **Spring Boot** | 3.5.9 | 웹 프레임워크 |
| **MyBatis** | 3.0.5 | SQL Mapper 프레임워크 |
| **PostgreSQL** | Latest | 메인 데이터베이스 |
| **Spring Security** | 6.x | 인증 및 권한 관리 |
| **Spring Session** | 3.x | Redis 기반 세션 클러스터링 |
| **Jasypt** | 3.0.5 | 설정 파일 암호화 |

### Frontend
| 기술 (Technology) | 버전 (Version) | 용도 (Usage) |
| --- | --- | --- |
| **Thymeleaf** | 3.1 | 서버 사이드 템플릿 엔진 |
| **Bootstrap** | 5.3 | 반응형 UI 프레임워크 |
| **jQuery** | 3.7.1 | DOM 조작 및 AJAX 통신 |

---

## 3. 핵심 기능 (Key Features)

### 3.1. 사용자 관리 (User Management)
- **CRUD**: 사용자 목록(페이징, 검색), 상세, 등록, 수정, 삭제 기능 구현
- **파일 첨부**: 프로필 이미지 업로드 (로컬 파일 시스템)

### 3.2. 보안 (Security)
- **Form 설인**: BCrypt 비밀번호 암호화 및 CSRF 방어
- **접근 제어**: 페이지 및 리소스별 접근 권한 설정

### 3.3. 인프라 (Infrastructure)
- **세션 관리**: 
  - **In-Memory**: 로컬 개발용 (Tomcat Session)
  - **Redis**: 운영/검증 서버용 (Spring Session Redis)
- **요청 로깅**: `LoggingInterceptor`를 통한 API 요청 시간 추적
- **표준 디렉토리**: 로그 및 파일 리소스를 `~/apps/template/` (또는 `C:/apps/template/`) 경로에서 관리

---

## 4. 환경별 프로파일 (Environment Profiles)

| 프로파일 | 세션 | 주요 설정 파일 | 특징 |
| --- | --- | --- | --- |
| **local-inmemory** | Tomcat | `application-local-inmemory.yaml` | 기본 개발 모드, Redis 불필요 |
| **local-redis** | Redis | `application-local-redis.yaml` | Redis 연동 테스트 |
| **dev** | Redis | `application-dev.yaml` | 개발 서버 환경 |
| **stg** | Redis | `application-stg.yaml` | 검증 서버 환경 (`~/apps/template` 경로 사용) |
| **prd** | Redis | `application-prd.yaml` | 운영 서버 환경 (`~/apps/template` 경로 사용) |

---

## 5. 실행 방법 (How to Run)

### 5.1. 실행 명령어
```bash
# 로컬 개발 모드 (기본)
./mvnw spring-boot:run

# Redis 연동 모드
./mvnw spring-boot:run -Dspring-boot.run.profiles=local-redis
```

## 6. 프로젝트 구조 (Project Structure)
```
src/main/java/com/example/template
├── common          # 공통 모듈 (DTO, Exception, Interceptor)
├── config          # 스프링 설정
├── controller      # 컨트롤러
├── service         # 서비스
└── mapper          # MyBatis 매퍼
```
