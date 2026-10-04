package com.zentra.zentra_flow.identity.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DriverCompanyRegistrationRequestDTO(

        @NotNull(message = "Account data is required.")
        @Valid
        AccountRequestDTO account,

        @NotBlank(message = "CNPJ is required.")
        String cnpj,

        @NotBlank(message = "Fleet manager's license is required.")
        String fleetManagerLicense,

        String fleetRegistrationNumber,

        @NotNull(message = "Address is required.")
        @Valid
        AddressRequestDTO address

){}
