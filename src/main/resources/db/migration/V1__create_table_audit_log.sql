CREATE TABLE audit_log (
    -- BaseEntity:
                                id UUID PRIMARY KEY,
                                version INT NOT NULL,
                                created_date TIMESTAMP NOT NULL,
                                created_by VARCHAR(100) NOT NULL,
                                last_modified_date TIMESTAMP,
                                last_modified_by VARCHAR(100),
                                deleted BOOLEAN NOT NULL DEFAULT FALSE,

    -- Auditory:
                               action VARCHAR(50) NOT NULL,
                               description TEXT NOT NULL,
                               trace_id VARCHAR(10) NOT NULL
);