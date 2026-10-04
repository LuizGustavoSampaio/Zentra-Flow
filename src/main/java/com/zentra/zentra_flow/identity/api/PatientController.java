package com.zentra.zentra_flow.identity.api;

import com.zentra.zentra_flow.identity.api.dto.PatientRegistrationRequestDTO;
import com.zentra.zentra_flow.identity.api.dto.RegistrationResponseDTO;
import com.zentra.zentra_flow.identity.application.PatientRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientRegistrationService patientRegistrationService;

    @PostMapping
    public ResponseEntity<RegistrationResponseDTO> register(@RequestBody @Valid PatientRegistrationRequestDTO request) {
        RegistrationResponseDTO response = patientRegistrationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
