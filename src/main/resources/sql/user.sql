-- 테이블 생성
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    role VARCHAR(50) DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 코멘트 추가
COMMENT ON TABLE users IS '사용자 테이블';
COMMENT ON COLUMN users.id IS '사용자 ID (PK)';
COMMENT ON COLUMN users.email IS '이메일';
COMMENT ON COLUMN users.password IS '비밀번호';
COMMENT ON COLUMN users.name IS '이름';
COMMENT ON COLUMN users.role IS '권한';
COMMENT ON COLUMN users.created_at IS '생성일시';
COMMENT ON COLUMN users.updated_at IS '수정일시';

-- 사용자 데이터 삽입 (비밀번호는 1임)
INSERT INTO users
    (id, email, "password", "name", "role", created_at, updated_at)
VALUES
    (1, 'admin@gmail.com', '$2a$10$eMIiZEDWP/VP..RbWoxx/OJry2s7hlsem5jv8tZQ5N7OXY3jpQkt2', 'admin', 'ADMIN', '2025-12-31 15:25:40.063', '2025-12-31 15:25:40.063'),
    (2, 'user2@gmail.com', '$2a$10$eMIiZEDWP/VP..RbWoxx/OJry2s7hlsem5jv8tZQ5N7OXY3jpQkt2', 'user2', 'USER', '2025-12-31 15:25:57.182', '2025-12-31 15:25:57.182'),
    (3, 'user3@gmail.com', '$2a$10$eMIiZEDWP/VP..RbWoxx/OJry2s7hlsem5jv8tZQ5N7OXY3jpQkt2', 'user3', 'USER', '2025-12-31 15:25:57.182', '2025-12-31 15:25:57.182'),
    (4, 'user4@gmail.com', '$2a$10$eMIiZEDWP/VP..RbWoxx/OJry2s7hlsem5jv8tZQ5N7OXY3jpQkt2', 'user4', 'USER', '2025-12-31 15:25:57.182', '2025-12-31 15:25:57.182'),
    (5, 'user5@gmail.com', '$2a$10$eMIiZEDWP/VP..RbWoxx/OJry2s7hlsem5jv8tZQ5N7OXY3jpQkt2', 'user5', 'USER', '2025-12-31 15:25:57.182', '2025-12-31 15:25:57.182'),
    (6, 'user6@gmail.com', '$2a$10$eMIiZEDWP/VP..RbWoxx/OJry2s7hlsem5jv8tZQ5N7OXY3jpQkt2', 'user6', 'USER', '2025-12-31 15:25:57.182', '2025-12-31 15:25:57.182'),
    (7, 'user7@gmail.com', '$2a$10$eMIiZEDWP/VP..RbWoxx/OJry2s7hlsem5jv8tZQ5N7OXY3jpQkt2', 'user7', 'USER', '2025-12-31 15:25:57.182', '2025-12-31 15:25:57.182'),
    (8, 'user8@gmail.com', '$2a$10$eMIiZEDWP/VP..RbWoxx/OJry2s7hlsem5jv8tZQ5N7OXY3jpQkt2', 'user8', 'USER', '2025-12-31 15:25:57.182', '2025-12-31 15:25:57.182'),
    (9, 'user9@gmail.com', '$2a$10$eMIiZEDWP/VP..RbWoxx/OJry2s7hlsem5jv8tZQ5N7OXY3jpQkt2', 'user9', 'USER', '2025-12-31 15:25:57.182', '2025-12-31 15:25:57.182'),
    (10, 'user10@gmail.com', '$2a$10$eMIiZEDWP/VP..RbWoxx/OJry2s7hlsem5jv8tZQ5N7OXY3jpQkt2', 'user10', 'USER', '2025-12-31 15:25:57.182', '2025-12-31 15:25:57.182'),
    (11, 'user11@gmail.com', '$2a$10$eMIiZEDWP/VP..RbWoxx/OJry2s7hlsem5jv8tZQ5N7OXY3jpQkt2', 'user11', 'USER', '2025-12-31 15:25:57.182', '2025-12-31 15:25:57.182'),
    (12, 'user12@gmail.com', '$2a$10$eMIiZEDWP/VP..RbWoxx/OJry2s7hlsem5jv8tZQ5N7OXY3jpQkt2', 'user12', 'USER', '2025-12-31 15:25:57.182', '2025-12-31 15:25:57.182');


ALTER SEQUENCE users_id_seq RESTART WITH 13;
