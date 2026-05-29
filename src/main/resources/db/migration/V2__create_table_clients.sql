create table Clients(
    -- BaseEntity:
        id UUID PRIMARY KEY,
        version INT NOT NULL,
        created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
        created_by VARCHAR(100) NOT NULL,
        last_modified_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
        last_modified_by VARCHAR(100),
        deleted BOOLEAN NOT NULL DEFAULT FALSE,

    -- Client
        name VARCHAR(150) NOT NULL,
        email VARCHAR(150) UNIQUE NOT NULL,
        password_hash VARCHAR(60) NOT NULL,
        failed_login_attempts INT NOT NULL DEFAULT 0,
        lock_until TIMESTAMP WITHOUT TIME ZONE,

        CONSTRAINT uq_clients_email UNIQUE (email),
        CONSTRAINT chk_password_hash_exact_size CHECK (LENGTH(password_hash) = 60)

);