package com.zentra.zentra_flow.identity.api;

import com.zentra.zentra_flow.identity.api.dto.DriverIndividualRegistrationRequestDTO;
import com.zentra.zentra_flow.identity.api.dto.PatientRegistrationRequestDTO;
import com.zentra.zentra_flow.identity.api.dto.RegistrationResponseDTO;
import com.zentra.zentra_flow.identity.application.DriverIndividualRegistrationService;
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
@RequestMapping("/api/drivers/individual")
@RequiredArgsConstructor
public class DriverIndividualController {

    private final DriverIndividualRegistrationService DriverIndividualRegistrationService;

    @PostMapping
    public ResponseEntity<RegistrationResponseDTO> register(@RequestBody @Valid DriverIndividualRegistrationRequestDTO request) {
        RegistrationResponseDTO response = DriverIndividualRegistrationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
