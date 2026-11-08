CREATE TABLE procedures (
    -- BaseEntity:
    id UUID PRIMARY KEY,
    version INT NOT NULL,
    created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    last_modified_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT FALSE,

    -- Procedure
    name VARCHAR(150) NOT NULL,
    type VARCHAR(20) NOT NULL,
    clinic_id UUID NOT NULL REFERENCES clinics(id)
);

CREATE TABLE doctors (
    -- BaseEntity:
    id UUID PRIMARY KEY,
    version INT NOT NULL,
    created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    last_modified_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT FALSE,

    -- Doctor
    name VARCHAR(150) NOT NULL,
    license_number VARCHAR(20) NOT NULL
);

CREATE TABLE doctor_clinic (
    doctor_id UUID NOT NULL REFERENCES doctors(id),
    clinic_id UUID NOT NULL REFERENCES clinics(id),
    PRIMARY KEY (doctor_id, clinic_id)
);

CREATE TABLE doctor_procedure (
    doctor_id UUID NOT NULL REFERENCES doctors(id),
    procedure_id UUID NOT NULL REFERENCES procedures(id),
    PRIMARY KEY (doctor_id, procedure_id)
);