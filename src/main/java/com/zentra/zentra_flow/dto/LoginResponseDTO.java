package com.zentra.zentra_flow.dto;

public record LoginResponseDTO(
        String token,
        String type,
        String email
) {}
