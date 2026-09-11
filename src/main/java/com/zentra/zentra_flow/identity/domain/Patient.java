package com.zentra.zentra_flow.identity.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "patients")
@Getter
@NoArgsConstructor
public class Patient extends Client {

    @Column(name = "medical_history")
    private String medicalHistory;

    @Embedded
    private Adress adress;

    Patient(String name, String email, String passwordHash,String cpf, String medicalHistory, Adress adress){
        super(name, email, passwordHash, Role.PATIENT, new Document(PersonType.INDIVIDUAL, cpf));
        this.medicalHistory = medicalHistory;
        this.adress = adress;
    }

}
