package com.zentra.zentra_flow.identity.api.dto;

public record LoginResponseDTO(
        String token,
        String type,
        String email
) {}
