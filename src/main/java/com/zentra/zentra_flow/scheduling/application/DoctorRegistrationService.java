package com.zentra.zentra_flow.scheduling.application;

import com.zentra.zentra_flow.identity.domain.Clinic;
import com.zentra.zentra_flow.identity.infrastructure.ClientRepository;
import com.zentra.zentra_flow.scheduling.api.dto.DoctorRegistrationRequestDTO;
import com.zentra.zentra_flow.scheduling.api.dto.DoctorResponseDTO;
import com.zentra.zentra_flow.scheduling.domain.Doctor;
import com.zentra.zentra_flow.scheduling.domain.Procedure;
import com.zentra.zentra_flow.scheduling.infrastructure.DoctorRepository;
import com.zentra.zentra_flow.scheduling.infrastructure.ProcedureRepository;
import com.zentra.zentra_flow.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorRegistrationService {

    private final DoctorRepository doctorRepository;
    private final ProcedureRepository procedureRepository;
    private final ClientRepository clientRepository;

    public DoctorResponseDTO register(String clinicEmail, DoctorRegistrationRequestDTO request) {
        Clinic clinic = (Clinic) clientRepository.findByEmail(clinicEmail)
                .orElseThrow(() -> new IllegalStateException("Authenticated clinic not found."));

        List<Procedure> procedures = procedureRepository
                .findAllByIdInAndClinic_Id(request.procedureIds(), clinic.getId());

        if (procedures.size() != request.procedureIds().size()) {
            throw new ResourceNotFoundException("One or more procedures were not found for this clinic.");
        }

        Doctor doctor = new Doctor(request.name(), request.licenseNumber());
        doctor.assignClinic(clinic);
        procedures.forEach(doctor::assignProcedure);

        Doctor saved = doctorRepository.save(doctor);

        List<UUID> savedProcedureIds = saved.getProcedures().stream()
                .map(Procedure::getId)
                .collect(Collectors.toList());

        return new DoctorResponseDTO(saved.getId(), saved.getName(), saved.getLicenseNumber(), savedProcedureIds);
    }

}
