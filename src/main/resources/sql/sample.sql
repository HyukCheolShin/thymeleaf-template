-- 테이블 생성
CREATE TABLE sample (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    role VARCHAR(50) DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 코멘트 추가
COMMENT ON TABLE sample IS '샘플 테이블';
COMMENT ON COLUMN sample.id IS '샘플 ID (PK)';
COMMENT ON COLUMN sample.email IS '이메일';
COMMENT ON COLUMN sample.password IS '비밀번호';
COMMENT ON COLUMN sample.name IS '이름';
COMMENT ON COLUMN sample.role IS '권한';
COMMENT ON COLUMN sample.created_at IS '생성일시';
COMMENT ON COLUMN sample.updated_at IS '수정일시';

-- 샘플 데이터 삽입
INSERT INTO public.sample
(id, email, "password", "name", "role", created_at, updated_at)
VALUES(1, 'admin@naver.com', '$2a$10$eMIiZEDWP/VP..RbWoxx/OJry2s7hlsem5jv8tZQ5N7OXY3jpQkt2', 'admin', 'ADMIN', '2025-12-31 15:25:40.063', '2025-12-31 15:25:40.063');
INSERT INTO public.sample
(id, email, "password", "name", "role", created_at, updated_at)
VALUES(2, 'user@google.com', '$2a$10$sgZdzhFGSTsXnsTtE3lNW.VUivLy8UrKQOIPE6UecFZ8A8XhiLEKu', 'user', 'USER', '2025-12-31 15:25:57.182', '2025-12-31 15:25:57.182');