CREATE TABLE drivers (
     id UUID PRIMARY KEY REFERENCES clients(id),
     average_rate DOUBLE PRECISION DEFAULT 0.0,
     total_ratings INTEGER DEFAULT 0,
     driver_origin VARCHAR(20) NOT NULL
);

CREATE TABLE drivers_individual (
     id UUID PRIMARY KEY REFERENCES drivers(id),
     cnh VARCHAR(20) NOT NULL,
     vehicle_info VARCHAR(255)
);

CREATE TABLE drivers_company (
     id UUID PRIMARY KEY REFERENCES drivers(id),
     fleet_manager_license VARCHAR(20) NOT NULL,
     fleet_registration_number VARCHAR(50)
);