CREATE TABLE avatar (
                        id         BIGINT PRIMARY KEY,
                        data       BYTEA,
                        file_path  VARCHAR(255),
                        file_size  BIGINT,
                        media_type VARCHAR(255),
                        student_id BIGINT REFERENCES student (student_id)
);

ALTER TABLE avatar RENAME COLUMN student_id TO student_student_id;

ALTER TABLE avatar ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY;