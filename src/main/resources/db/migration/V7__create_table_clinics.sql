CREATE TABLE clinics (
     id UUID PRIMARY KEY REFERENCES clients(id),
     cnes VARCHAR(20),
     average_rate DOUBLE PRECISION DEFAULT 0.0,
     total_ratings INTEGER DEFAULT 0,
     approval_status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
     cep VARCHAR(9),
     street VARCHAR(150),
     number VARCHAR(10),
     neighborhood VARCHAR(100),
     city VARCHAR(100),
     uf VARCHAR(2),
     complement VARCHAR(100)
);