package com.zentra.zentra_flow.identity.api.dto;

import java.util.UUID;

public record RegistrationResponseDTO(UUID id, String name, String email) { }
