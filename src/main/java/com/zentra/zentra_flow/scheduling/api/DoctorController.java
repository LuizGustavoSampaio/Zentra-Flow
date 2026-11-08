package com.zentra.zentra_flow.scheduling.api;

import com.zentra.zentra_flow.scheduling.api.dto.DoctorRegistrationRequestDTO;
import com.zentra.zentra_flow.scheduling.api.dto.DoctorResponseDTO;
import com.zentra.zentra_flow.scheduling.application.DoctorRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clinics/me/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorRegistrationService doctorRegistrationService;

    @PostMapping
    @PreAuthorize("hasRole('CLINIC')")
    public ResponseEntity<DoctorResponseDTO> register(
            Authentication authentication,
            @RequestBody @Valid DoctorRegistrationRequestDTO request) {

        DoctorResponseDTO response = doctorRegistrationService.register(authentication.getName(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
