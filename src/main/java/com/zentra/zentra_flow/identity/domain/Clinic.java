package com.zentra.zentra_flow.identity.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clinics")
@NoArgsConstructor
@Getter
public class Clinic extends Client{

    @Column(name = "average_rate")
    private Double averageRate = 0.0;

    @Column(name = "total_ratings")
    private Integer totalRatings = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status", nullable = false, length = 20)
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;

    @Column(name = "cnes", length = 20)
    private String cnes;

    @Embedded
    private Adress adress;

    public Clinic(String name, String email, String passwordHash,String cnpj, String cnes, Adress adress) {
        super(name, email, passwordHash, Role.CLINIC, new Document(PersonType.LEGAL_ENTITY, cnpj));
        this.cnes = cnes;
        this.adress = adress;
    }

}
