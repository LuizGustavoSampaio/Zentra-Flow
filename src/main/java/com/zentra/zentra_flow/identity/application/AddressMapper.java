package com.zentra.zentra_flow.identity.application;

import com.zentra.zentra_flow.identity.api.dto.AddressRequestDTO;
import com.zentra.zentra_flow.identity.domain.Address;

public final class AddressMapper {

    private AddressMapper() {
    }

    public static Address toDomain(AddressRequestDTO dto) {
        return new Address(
                dto.cep(), dto.street(), dto.number(), dto.neighborhood(), dto.city(), dto.uf(), dto.complement()
        );
    }
}
