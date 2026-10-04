package com.zentra.zentra_flow.identity.application;

import com.zentra.zentra_flow.audit.application.SecurityAuditLogger;
import com.zentra.zentra_flow.identity.api.dto.ClinicRegistrationRequestDTO;
import com.zentra.zentra_flow.identity.api.dto.RegistrationResponseDTO;
import com.zentra.zentra_flow.identity.domain.Address;
import com.zentra.zentra_flow.identity.domain.Clinic;
import com.zentra.zentra_flow.identity.infrastructure.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClinicRegistrationService {

    private final ClientRepository clientRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SecurityAuditLogger auditLogger;
    private final ClientUniquenessValidator uniquenessValidator;

    @Transactional
    public RegistrationResponseDTO register(ClinicRegistrationRequestDTO request){

        uniquenessValidator.validate(request.account().email(), request.cnpj());

        String hashedPassword = passwordEncoder.encode(request.account().password());

        Address address = AddressMapper.toDomain(request.address());

        Clinic clinic = new Clinic(
                request.account().name(), request.account().email(), hashedPassword,
                request.cnpj(), request.cnes(), address
        );

        clientRepository.save(clinic);
        auditLogger.log("CLINIC_REGISTERED", "New clinic registered, pending approval: " + request.account().email());

        return new RegistrationResponseDTO(clinic.getId(), clinic.getName(), clinic.getEmail());
    }
}
