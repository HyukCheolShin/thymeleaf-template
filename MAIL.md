Subject: [공지] Thymeleaf Template 프로젝트 배포 안내

팀원 여러분, 안녕하십니까.
표준 웹 애플리케이션 개발을 위한 **Thymeleaf Template** 프로젝트를 소개합니다.

본 프로젝트는 **Spring Boot 3.5.9** 기반으로, 개발 시작에 필요한 필수 인프라가 사전에 구성되어 있습니다.

### 📌 주요 구성 요소
1. **백엔드 인프라**
   - **Spring Security**: Form 로그인 및 CSRF 보안 적용
   - **세션 관리**: 로컬(In-Memory) 및 서버(Redis) 환경별 분리 적용
   - **표준 경로**: 로그 및 업로드 파일 경로를 `~/apps/template`로 일원화

2. **개발 편의성**
   - **API 로깅**: 요청 처리 시간 자동 로깅
   - **예외 처리**: 전역 예외 처리기(`GlobalExceptionHandler`) 제공
   - **실행 모드**: 로컬 개발 시 Redis 없이도 구동 가능한 `local-inmemory` 모드 지원

### 🚀 실행 방법
소스 코드 클론 후 아래 명령어로 실행할 수 있습니다.

```bash
# 기본 실행 (로컬 개발용)
./mvnw spring-boot:run
```

자세한 내용은 프로젝트 내 **[README.md](README.md)**를 참고해 주십시오.

감사합니다.
