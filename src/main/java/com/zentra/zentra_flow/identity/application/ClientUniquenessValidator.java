package com.zentra.zentra_flow.identity.application;

import com.zentra.zentra_flow.identity.infrastructure.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.zentra.zentra_flow.shared.exception.ResourceAlreadyExistsException;

@Component
@RequiredArgsConstructor
public class ClientUniquenessValidator {

    private final ClientRepository clientRepository;

    public void validate(String email, String documentNumber) {
        if (clientRepository.existsByEmail(email)) {
            throw new ResourceAlreadyExistsException("This email address is already registered.");
        }

        if(clientRepository.existsByDocument_Number(documentNumber)) {
            throw new ResourceAlreadyExistsException("This document number is already registered.");
        }
    }

}
