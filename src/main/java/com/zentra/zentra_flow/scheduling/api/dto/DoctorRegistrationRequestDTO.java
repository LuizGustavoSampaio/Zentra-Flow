package com.zentra.zentra_flow.scheduling.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public record DoctorRegistrationRequestDTO(

        @NotBlank(message = "Name is required.")
        String name,

        @NotBlank(message = "License number is required.")
        String licenseNumber,

        @NotEmpty(message = "At least one procedure must be informed.")
        List<UUID> procedureIds

) {
}
