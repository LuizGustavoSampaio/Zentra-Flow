package com.zentra.zentra_flow.identity.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PatientRegistrationRequestDTO(

        @NotNull(message = "Name, password and email is required.")
        @Valid
        AccountRequestDTO account,

        @NotBlank(message  = "A CPF is required.")
        String cpf,

        String medicalHistory,

        @NotNull @Valid AddressRequestDTO address

) {}

