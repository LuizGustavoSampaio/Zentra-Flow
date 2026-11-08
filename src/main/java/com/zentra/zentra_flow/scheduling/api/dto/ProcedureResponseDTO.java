package com.zentra.zentra_flow.scheduling.api.dto;

import java.util.UUID;

public record ProcedureResponseDTO(

        UUID id,
        String name,
        String type

) { }
