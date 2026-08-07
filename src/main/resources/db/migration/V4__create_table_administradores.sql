CREATE TABLE administrators (
    id UUID PRIMARY KEY REFERENCES clients(id)
);