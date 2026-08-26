package com.zentra.zentra_flow.identity.api.dto;

public record LoginRequestDTO(
        String email,
        String password
) {}
