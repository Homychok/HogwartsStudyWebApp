ALTER TABLE faculty ADD UNIQUE (name);

ALTER TABLE student ADD CONSTRAINT check_positive check (age > 16);