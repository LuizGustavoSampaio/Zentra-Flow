package com.zentra.zentra_flow.scheduling.domain;

import com.zentra.zentra_flow.identity.domain.BaseEntity;
import com.zentra.zentra_flow.identity.domain.Clinic;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "doctors")
@Getter
@NoArgsConstructor
public class Doctor extends BaseEntity {

    @Column(nullable = false, length = 150)
    private String name;

    @Column(name = "license_number", nullable = false, length = 20)
    private String licenseNumber;

    @ManyToMany
    @JoinTable(
            name = "doctor_clinic",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "clinic_id")
    )
    private Set<Clinic> clinics = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "doctor_procedure",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "procedure_id")
    )
    private Set<Procedure> procedures = new HashSet<>();

    public Doctor(String name, String licenseNumber){
        this.name = name;
        this.licenseNumber = licenseNumber;
    }

    public void assignClinic(Clinic clinic){
        this.clinics.add(clinic);
    }

    public void assignProcedure(Procedure procedure){
        this.procedures.add(procedure);
    }
}
