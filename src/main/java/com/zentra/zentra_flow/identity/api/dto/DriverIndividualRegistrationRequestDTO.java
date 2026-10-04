package com.zentra.zentra_flow.identity.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record DriverIndividualRegistrationRequestDTO(

        @NotNull(message = "Name, password and email is required.")
        @Valid
        AccountRequestDTO account,

        @NotBlank(message = "CPF is required.")
        String cpf,

        @NotBlank(message = "Driver's license (CNH) is required.")
        String cnh,

        String vehicleInfo,

        @NotNull(message = "Address is required.")
        @Valid
        AddressRequestDTO address

) {}
