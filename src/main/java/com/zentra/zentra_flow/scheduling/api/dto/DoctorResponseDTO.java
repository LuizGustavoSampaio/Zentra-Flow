package com.zentra.zentra_flow.scheduling.api.dto;

import java.util.List;
import java.util.UUID;

public record DoctorResponseDTO(

        UUID id,
        String name,
        String licenseNumber,
        List<UUID> procedureIds

) {}
