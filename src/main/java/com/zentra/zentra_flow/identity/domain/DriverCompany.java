package com.zentra.zentra_flow.identity.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "drivers_company")
@Getter
@NoArgsConstructor
public class DriverCompany extends Driver{

    @Column(name = "fleet_manager_license")
    private String fleetManagerLicense;

    @Column(name = "fleet_registration_number")
    private String fleetRegistrationNumber;

    public DriverCompany(String name, String email, String passwordHash, String cpf, String fleetManagerLicense, String fleetRegistrationNumber) {
        super(name, email, passwordHash,new Document(PersonType.INDIVIDUAL, cpf));
        this.fleetManagerLicense = fleetManagerLicense;
        this.fleetRegistrationNumber = fleetRegistrationNumber;
    }

}
