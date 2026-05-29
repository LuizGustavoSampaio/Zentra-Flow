UPDATE audit_log
SET created_date = NOW()
WHERE created_date IS NULL;

ALTER TABLE audit_log
    ALTER COLUMN created_date TYPE TIMESTAMP WITHOUT TIME ZONE,
    ALTER COLUMN created_date SET NOT NULL,
    ALTER COLUMN last_modified_date TYPE TIMESTAMP WITHOUT TIME ZONE;