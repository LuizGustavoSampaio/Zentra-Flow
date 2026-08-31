ALTER TABLE clients ADD COLUMN person_type VARCHAR(20);
ALTER TABLE clients ADD COLUMN document_number VARCHAR(20);

ALTER TABLE clients
    ADD CONSTRAINT uq_clients_document UNIQUE (document_number);

CREATE TABLE patients (
      id UUID PRIMARY KEY REFERENCES clients(id),
      medical_history TEXT,
      cep VARCHAR(9),
      street VARCHAR(150),
      number VARCHAR(10),
      neighborhood VARCHAR(100),
      city VARCHAR(100),
      uf VARCHAR(2),
      complement VARCHAR(100)
);