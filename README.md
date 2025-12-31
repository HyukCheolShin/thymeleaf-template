# Thymeleaf Template Project

Spring Boot와 Thymeleaf를 기반으로 구축된 템플릿 프로젝트입니다. 기본적인 CRUD 기능과 페이지네이션, 그리고 실무에서 유용한 MyBatis 설정들이 적용되어 있습니다.

## 주요 구현 내용

### 1. 게시판 CRUD 및 페이지네이션
- **목록 조회**: 페이징 처리된 샘플 데이터 목록을 조회합니다.
- **상세/등록/수정/삭제**: 기본적인 데이터 조작 기능을 제공합니다.
- **Frontend**: Thymeleaf와 jQuery(AJAX)를 사용하여 비동기 데이터 로딩 및 동적 페이지네이션 컨트롤을 구현했습니다.

### 2. 백엔드 아키텍처 개선
- **공통 페이지네이션 DTO**: `PageRequest`와 `PageResponse` 클래스를 도입하여 페이징 요청과 응답을 표준화했습니다.
- **Service 계층 리팩토링**: `Map` 기반의 파라미터 전달 방식을 개선하고, 비즈니스 로직을 명확히 분리했습니다.

### 3. 데이터 매핑 전략 (CamelCaseMap)
- **Map 결과 자동 변환**: MyBatis `resultType="map"` 사용 시, DB의 Snake Case 컬럼명(`created_at`)을 Java의 Camel Case(`createdAt`)로 자동 변환해주는 `CamelCaseMap`을 구현했습니다.
- **MyBatis 설정**: `call-setters-on-nulls: true`를 적용하여 NULL 값이 있는 컬럼도 Map 결과에 누락되지 않도록 설정했습니다.
- **명시적 컬럼 조회**: `SELECT *` 대신 필요한 컬럼을 명시적으로 나열하여 유지보수성과 성능을 고려했습니다.

### 4. 테스트 코드
- **SampleApiControllerTest**: API 엔드포인트에 대한 통합 테스트를 작성하여, 변경 사항(DTO 도입, 키명 변경 등) 발생 시 기능이 정상 동작함을 지속적으로 검증했습니다.

## 기술 스택
- **Java**: 21
- **Spring Boot**: 3.x
- **Database**: PostgreSQL
- **Persistence**: MyBatis
- **Template Engine**: Thymeleaf
- **Frontend**: jQuery, HTML/CSS

## 실행 방법
```bash
./mvnw spring-boot:run
```
접속: `http://localhost:8080/sample/list`
