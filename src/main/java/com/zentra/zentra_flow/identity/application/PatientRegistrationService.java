package com.zentra.zentra_flow.identity.application;

import com.zentra.zentra_flow.audit.application.SecurityAuditLogger;
import com.zentra.zentra_flow.identity.api.dto.PatientRegistrationRequestDTO;
import com.zentra.zentra_flow.identity.api.dto.RegistrationResponseDTO;
import com.zentra.zentra_flow.identity.domain.Address;
import com.zentra.zentra_flow.identity.domain.Patient;
import com.zentra.zentra_flow.identity.infrastructure.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PatientRegistrationService {

    private final ClientRepository clientRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SecurityAuditLogger auditLogger;
    private final ClientUniquenessValidator uniquenessValidator;

    @Transactional
    public RegistrationResponseDTO register(PatientRegistrationRequestDTO request) {

       uniquenessValidator.validate(request.account().email(), request.cpf());

        Address address = AddressMapper.toDomain(request.address());

        String hashedPassword = passwordEncoder.encode(request.account().password());

        Patient patient = new Patient(
          request.account().name(),request.account().email(), hashedPassword,
          request.cpf(), request.medicalHistory(), address
        );

        clientRepository.save(patient);
        auditLogger.log("PATIENT_REGISTERED", "New patient registered: " + request.account().email());

        return new RegistrationResponseDTO(patient.getId(), patient.getName(), patient.getEmail());
    }

}
