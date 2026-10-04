package com.zentra.zentra_flow.identity.application;


import com.zentra.zentra_flow.audit.application.SecurityAuditLogger;
import com.zentra.zentra_flow.identity.api.dto.DriverCompanyRegistrationRequestDTO;
import com.zentra.zentra_flow.identity.api.dto.RegistrationResponseDTO;
import com.zentra.zentra_flow.identity.domain.Address;
import com.zentra.zentra_flow.identity.domain.DriverCompany;
import com.zentra.zentra_flow.identity.infrastructure.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverCompanyRegistrationService {

    private final ClientRepository clientRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SecurityAuditLogger auditLogger;
    private final ClientUniquenessValidator uniquenessValidator;

    @Transactional
    public RegistrationResponseDTO register(DriverCompanyRegistrationRequestDTO request) {

        uniquenessValidator.validate(request.account().email(), request.cnpj());

        Address address = AddressMapper.toDomain(request.address());

        String hashedPassword = passwordEncoder.encode(request.account().password());

        DriverCompany driver = new DriverCompany(
                request.account().name(), request.account().email(), hashedPassword,
                request.cnpj(), request.fleetManagerLicense(), request.fleetRegistrationNumber(), address
        );

        clientRepository.save(driver);
        auditLogger.log("DRIVER_REGISTERED", "New company driver registered: " + request.account().email());

        return new RegistrationResponseDTO(driver.getId(), driver.getName(), driver.getEmail());
    }


}
