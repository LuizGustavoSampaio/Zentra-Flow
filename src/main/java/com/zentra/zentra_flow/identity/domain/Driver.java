package com.zentra.zentra_flow.identity.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "drivers")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@NoArgsConstructor
public abstract class Driver extends Client{

    @Column(name = "average_rate")
    private Double averageRate = 0.0;

    @Column(name = "total_ratings")
    private Integer totalRatings = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "driver_origin")
    private DriverOrigin origin;

    protected Driver(String name, String email, String passwordHash, Document document) {
        super(name, email, passwordHash, Role.DRIVER, document);
        this.origin = DriverOrigin.OWN_REGISTRATION;
    }

}
