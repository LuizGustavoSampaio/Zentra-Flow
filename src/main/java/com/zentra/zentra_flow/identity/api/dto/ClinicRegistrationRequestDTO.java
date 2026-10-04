package com.zentra.zentra_flow.identity.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClinicRegistrationRequestDTO(

        @NotNull(message = "Account data is required.")
        @Valid
        AccountRequestDTO account,

        @NotBlank(message = "CNPJ is required.")
        String cnpj,

        String cnes,

        @NotNull(message = "Address is required.")
        @Valid
        AddressRequestDTO address

) {}
