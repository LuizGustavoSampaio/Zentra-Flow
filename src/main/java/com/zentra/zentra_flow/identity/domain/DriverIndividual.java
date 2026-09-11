package com.zentra.zentra_flow.identity.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "drivers_individual")
@NoArgsConstructor
public class DriverIndividual extends Driver {

    @Column(name = "cnh", nullable = false, length = 20)
    private String cnh;

    @Column(name = "Vehicle_info")
    private String vehicleInfo;

    public DriverIndividual(String name, String email, String passwordHash, String cpf, String cnh, String vehicleInfo) {
        super(name, email, passwordHash, new Document(PersonType.INDIVIDUAL, cpf));
        this.cnh = cnh;
        this.vehicleInfo = vehicleInfo;
    }


}
