package com.zentra.zentra_flow.identity.application;

import com.zentra.zentra_flow.audit.application.SecurityAuditLogger;
import com.zentra.zentra_flow.identity.api.dto.DriverIndividualRegistrationRequestDTO;
import com.zentra.zentra_flow.identity.api.dto.RegistrationResponseDTO;
import com.zentra.zentra_flow.identity.domain.Address;
import com.zentra.zentra_flow.identity.domain.Driver;
import com.zentra.zentra_flow.identity.domain.DriverIndividual;
import com.zentra.zentra_flow.identity.infrastructure.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverIndividualRegistrationService {

    private final ClientRepository clientRepository;
    private final SecurityAuditLogger auditLogger;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ClientUniquenessValidator uniquenessValidator;

    @Transactional
    public RegistrationResponseDTO register(DriverIndividualRegistrationRequestDTO request) {

        uniquenessValidator.validate(request.account().email(), request.cpf());

        Address address = AddressMapper.toDomain(request.address());

        String hashedPassword = passwordEncoder.encode(request.account().password());

        DriverIndividual driver = new DriverIndividual(request.account().name(), request.account().email(), hashedPassword,
                                    request.cpf(), request.cnh(), request.vehicleInfo(), address);

        clientRepository.save(driver);
        auditLogger.log("DRIVER_REGISTERED", "New individual driver registered: " + request.account().email());

        return new RegistrationResponseDTO(driver.getId(), driver.getName(), driver.getEmail());

    }
}
