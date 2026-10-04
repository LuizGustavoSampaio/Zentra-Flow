package com.zentra.zentra_flow.identity.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressRequestDTO(

        @NotBlank(message  = "A CEP is required.")
        String cep,

        @NotBlank(message = "A street is required.")
        String street,

        String number,

        String neighborhood,

        @NotBlank(message = "A city is required.")
        String city,

        @NotBlank(message = "A UF is required." )
        @Size(min = 2, max = 2, message = "UF must have exactly 2 characters.")
        String uf,

        String complement

) {}
