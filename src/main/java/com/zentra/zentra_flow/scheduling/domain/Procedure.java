package com.zentra.zentra_flow.scheduling.domain;

import com.zentra.zentra_flow.identity.domain.BaseEntity;
import com.zentra.zentra_flow.identity.domain.Clinic;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "procedures")
public class Procedure extends BaseEntity {

    @Column(nullable = false, length =  150)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProcedureType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id", nullable = false)
    private Clinic clinic;

    public Procedure(String name, ProcedureType type, Clinic clinic) {
        this.name = name;
        this.type = type;
        this.clinic = clinic;
    }
}
