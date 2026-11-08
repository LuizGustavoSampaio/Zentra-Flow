package com.zentra.zentra_flow.scheduling.application;


import com.zentra.zentra_flow.identity.domain.Clinic;
import com.zentra.zentra_flow.identity.infrastructure.ClientRepository;
import com.zentra.zentra_flow.scheduling.api.dto.DoctorRegistrationRequestDTO;
import com.zentra.zentra_flow.scheduling.api.dto.DoctorResponseDTO;
import com.zentra.zentra_flow.scheduling.application.DoctorRegistrationService;
import com.zentra.zentra_flow.scheduling.domain.Doctor;
import com.zentra.zentra_flow.scheduling.domain.Procedure;
import com.zentra.zentra_flow.scheduling.infrastructure.DoctorRepository;
import com.zentra.zentra_flow.scheduling.infrastructure.ProcedureRepository;
import com.zentra.zentra_flow.shared.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DoctorRegistrationServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private ProcedureRepository procedureRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private DoctorRegistrationService doctorRegistrationService;

    @Test
    void registersDoctorSuccessfullyWhenAllProceduresBelongToClinic() {
        String clinicEmail = "clinic@zentraflow.com";
        UUID clinicId = UUID.randomUUID();
        Clinic clinic = mock(Clinic.class);
        when(clinic.getId()).thenReturn(clinicId);

        UUID procedureId1 = UUID.randomUUID();
        UUID procedureId2 = UUID.randomUUID();
        Procedure procedure1 = mock(Procedure.class);
        Procedure procedure2 = mock(Procedure.class);
        when(procedure1.getId()).thenReturn(procedureId1);
        when(procedure2.getId()).thenReturn(procedureId2);

        DoctorRegistrationRequestDTO request = new DoctorRegistrationRequestDTO(
                "Dr. John Doe", "CRM-12345", List.of(procedureId1, procedureId2));

        when(clientRepository.findByEmail(clinicEmail)).thenReturn(Optional.of(clinic));
        when(procedureRepository.findAllByIdInAndClinic_Id(request.procedureIds(), clinicId))
                .thenReturn(List.of(procedure1, procedure2));
        when(doctorRepository.save(any(Doctor.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        DoctorResponseDTO response = doctorRegistrationService.register(clinicEmail, request);

        assertThat(response.name()).isEqualTo("Dr. John Doe");
        assertThat(response.licenseNumber()).isEqualTo("CRM-12345");
        assertThat(response.procedureIds()).containsExactlyInAnyOrder(procedureId1, procedureId2);
    }

    @Test
    void throwsExceptionWhenSomeProceduresDoNotBelongToClinic() {
        String clinicEmail = "clinic@zentraflow.com";
        UUID clinicId = UUID.randomUUID();
        Clinic clinic = mock(Clinic.class);
        when(clinic.getId()).thenReturn(clinicId);

        UUID procedureId1 = UUID.randomUUID();
        UUID procedureId2 = UUID.randomUUID();
        Procedure procedure1 = mock(Procedure.class);

        DoctorRegistrationRequestDTO request = new DoctorRegistrationRequestDTO(
                "Dr. John Doe", "CRM-12345", List.of(procedureId1, procedureId2));

        when(clientRepository.findByEmail(clinicEmail)).thenReturn(Optional.of(clinic));

        when(procedureRepository.findAllByIdInAndClinic_Id(request.procedureIds(), clinicId))
                .thenReturn(List.of(procedure1));

        assertThrows(ResourceNotFoundException.class,
                () -> doctorRegistrationService.register(clinicEmail, request));

        verify(doctorRepository, never()).save(any());
    }

    @Test
    void throwsExceptionWhenClinicNotFound() {
        String clinicEmail = "notfound@zentraflow.com";
        DoctorRegistrationRequestDTO request = new DoctorRegistrationRequestDTO(
                "Dr. Jane Doe", "CRM-99999", List.of(UUID.randomUUID()));

        when(clientRepository.findByEmail(clinicEmail)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class,
                () -> doctorRegistrationService.register(clinicEmail, request));

        verify(procedureRepository, never()).findAllByIdInAndClinic_Id(anyList(), any());
        verify(doctorRepository, never()).save(any());
    }
}


