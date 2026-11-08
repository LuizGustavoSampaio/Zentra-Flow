package com.zentra.zentra_flow.scheduling.application;


import com.zentra.zentra_flow.identity.domain.Clinic;
import com.zentra.zentra_flow.identity.infrastructure.ClientRepository;
import com.zentra.zentra_flow.scheduling.api.dto.ProcedureRegistrationRequestDTO;
import com.zentra.zentra_flow.scheduling.api.dto.ProcedureResponseDTO;
import com.zentra.zentra_flow.scheduling.domain.Procedure;
import com.zentra.zentra_flow.scheduling.domain.ProcedureType;
import com.zentra.zentra_flow.scheduling.infrastructure.ProcedureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcedureRegistrationService {

    private final ProcedureRepository procedureRepository;
    private final ClientRepository clientRepository;

    public ProcedureResponseDTO register(String clinicEmail, ProcedureRegistrationRequestDTO request) {
        Clinic clinic = (Clinic) clientRepository.findByEmail(clinicEmail)
                .orElseThrow(() -> new IllegalStateException("Authenticated clinic not found."));

        ProcedureType type = ProcedureType.valueOf(request.type().toUpperCase());

        Procedure procedure = new Procedure(request.name(), type, clinic);
        Procedure saved = procedureRepository.save(procedure);

        return new ProcedureResponseDTO(saved.getId(), saved.getName(), saved.getType().name());
    }

}
