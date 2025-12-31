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
INSERT INTO sample (email, password, name, role) VALUES 
('user1@example.com', 'password123', '홍길동', 'USER'),
('admin@example.com', 'admin123', '관리자', 'ADMIN'),
('user2@example.com', 'password123', '김철수', 'USER');

INSERT INTO sample (email, password, name, role) VALUES ('user3@example.com', 'password123', 'User3', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user4@example.com', 'password123', 'User4', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user5@example.com', 'password123', 'User5', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user6@example.com', 'password123', 'User6', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user7@example.com', 'password123', 'User7', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user8@example.com', 'password123', 'User8', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user9@example.com', 'password123', 'User9', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user10@example.com', 'password123', 'User10', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user11@example.com', 'password123', 'User11', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user12@example.com', 'password123', 'User12', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user13@example.com', 'password123', 'User13', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user14@example.com', 'password123', 'User14', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user15@example.com', 'password123', 'User15', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user16@example.com', 'password123', 'User16', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user17@example.com', 'password123', 'User17', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user18@example.com', 'password123', 'User18', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user19@example.com', 'password123', 'User19', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user20@example.com', 'password123', 'User20', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user21@example.com', 'password123', 'User21', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user22@example.com', 'password123', 'User22', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user23@example.com', 'password123', 'User23', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user24@example.com', 'password123', 'User24', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user25@example.com', 'password123', 'User25', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user26@example.com', 'password123', 'User26', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user27@example.com', 'password123', 'User27', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user28@example.com', 'password123', 'User28', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user29@example.com', 'password123', 'User29', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user30@example.com', 'password123', 'User30', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user31@example.com', 'password123', 'User31', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user32@example.com', 'password123', 'User32', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user33@example.com', 'password123', 'User33', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user34@example.com', 'password123', 'User34', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user35@example.com', 'password123', 'User35', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user36@example.com', 'password123', 'User36', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user37@example.com', 'password123', 'User37', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user38@example.com', 'password123', 'User38', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user39@example.com', 'password123', 'User39', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user40@example.com', 'password123', 'User40', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user41@example.com', 'password123', 'User41', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user42@example.com', 'password123', 'User42', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user43@example.com', 'password123', 'User43', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user44@example.com', 'password123', 'User44', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user45@example.com', 'password123', 'User45', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user46@example.com', 'password123', 'User46', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user47@example.com', 'password123', 'User47', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user48@example.com', 'password123', 'User48', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user49@example.com', 'password123', 'User49', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user50@example.com', 'password123', 'User50', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user51@example.com', 'password123', 'User51', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user52@example.com', 'password123', 'User52', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user53@example.com', 'password123', 'User53', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user54@example.com', 'password123', 'User54', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user55@example.com', 'password123', 'User55', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user56@example.com', 'password123', 'User56', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user57@example.com', 'password123', 'User57', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user58@example.com', 'password123', 'User58', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user59@example.com', 'password123', 'User59', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user60@example.com', 'password123', 'User60', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user61@example.com', 'password123', 'User61', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user62@example.com', 'password123', 'User62', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user63@example.com', 'password123', 'User63', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user64@example.com', 'password123', 'User64', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user65@example.com', 'password123', 'User65', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user66@example.com', 'password123', 'User66', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user67@example.com', 'password123', 'User67', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user68@example.com', 'password123', 'User68', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user69@example.com', 'password123', 'User69', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user70@example.com', 'password123', 'User70', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user71@example.com', 'password123', 'User71', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user72@example.com', 'password123', 'User72', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user73@example.com', 'password123', 'User73', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user74@example.com', 'password123', 'User74', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user75@example.com', 'password123', 'User75', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user76@example.com', 'password123', 'User76', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user77@example.com', 'password123', 'User77', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user78@example.com', 'password123', 'User78', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user79@example.com', 'password123', 'User79', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user80@example.com', 'password123', 'User80', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user81@example.com', 'password123', 'User81', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user82@example.com', 'password123', 'User82', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user83@example.com', 'password123', 'User83', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user84@example.com', 'password123', 'User84', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user85@example.com', 'password123', 'User85', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user86@example.com', 'password123', 'User86', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user87@example.com', 'password123', 'User87', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user88@example.com', 'password123', 'User88', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user89@example.com', 'password123', 'User89', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user90@example.com', 'password123', 'User90', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user91@example.com', 'password123', 'User91', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user92@example.com', 'password123', 'User92', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user93@example.com', 'password123', 'User93', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user94@example.com', 'password123', 'User94', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user95@example.com', 'password123', 'User95', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user96@example.com', 'password123', 'User96', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user97@example.com', 'password123', 'User97', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user98@example.com', 'password123', 'User98', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user99@example.com', 'password123', 'User99', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user100@example.com', 'password123', 'User100', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user101@example.com', 'password123', 'User101', 'USER');
INSERT INTO sample (email, password, name, role) VALUES ('user102@example.com', 'password123', 'User102', 'USER');
