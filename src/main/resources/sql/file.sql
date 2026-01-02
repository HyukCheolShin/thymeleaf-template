-- 파일 테이블 생성
CREATE TABLE files (
    id BIGSERIAL PRIMARY KEY,
    ref_table VARCHAR(50) NOT NULL,
    ref_id BIGINT NOT NULL,
    original_name VARCHAR(255) NOT NULL,
    save_name VARCHAR(255) NOT NULL,
    size BIGINT NOT NULL,
    extension VARCHAR(10) NOT NULL,
    path VARCHAR(500) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100)
);

-- 코멘트 추가
COMMENT ON TABLE files IS '파일 테이블';
COMMENT ON COLUMN files.id IS '파일 ID (PK)';
COMMENT ON COLUMN files.ref_table IS '참조 테이블명';
COMMENT ON COLUMN files.ref_id IS '참조 ID';
COMMENT ON COLUMN files.original_name IS '원본 파일명';
COMMENT ON COLUMN files.save_name IS '저장 파일명';
COMMENT ON COLUMN files.size IS '파일 크기';
COMMENT ON COLUMN files.extension IS '확장자';
COMMENT ON COLUMN files.path IS '저장 경로';
COMMENT ON COLUMN files.created_at IS '생성일시';
COMMENT ON COLUMN files.created_by IS '생성자';

-- 인덱스 추가 (조회 성능 향상)
CREATE INDEX idx_files_ref ON files (ref_table, ref_id);
