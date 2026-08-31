package com.zentra.zentra_flow.identity.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class Adress {

    @Column(name = "cep", length = 9)
    String cep;

    @Column(name = "street", length = 150)
    String street;

    @Column(name = "number", length = 10)
    String number;

    @Column(name = "neighborhood", length = 100)
    String neighborhood;

    @Column(name = "city", length = 100)
    String city;

    @Column(name = "uf", length = 2)
    String uf;

    @Column(name = "complement", length = 100)
    String complement;

    private Adress(String cep, String street, String number, String neighborhood, String city, String uf, String complement) {
        this.cep = cep;
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.city = city;
        this.uf = uf;
        this.complement = complement;
    }
}
