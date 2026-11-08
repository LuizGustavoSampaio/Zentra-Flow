package com.zentra.zentra_flow.scheduling.api.dto;

import jakarta.validation.constraints.NotBlank;

public record ProcedureRegistrationRequestDTO(

        @NotBlank(message = "Name is required.")
        String name,

        @NotBlank(message = "type is required.")
        String type

) {}
