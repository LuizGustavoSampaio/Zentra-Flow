package com.zentra.zentra_flow.identity.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class Document {

    @Enumerated(EnumType.STRING)
    @Column(name = "person_type", length = 20)
    private PersonType type;

    @Column(name = "document_number", length = 20)
    private String number;

    public Document(PersonType type, String number){
        this.type = type;
        this.number = number;
    }

}
