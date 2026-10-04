package com.zentra.zentra_flow.identity.api;

import com.zentra.zentra_flow.identity.api.dto.DriverCompanyRegistrationRequestDTO;
import com.zentra.zentra_flow.identity.api.dto.RegistrationResponseDTO;
import com.zentra.zentra_flow.identity.application.DriverCompanyRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/drivers/company")
@RequiredArgsConstructor
public class DriverCompanyController {

    private final DriverCompanyRegistrationService driverCompanyRegistrationService;

    @PostMapping
    public ResponseEntity<RegistrationResponseDTO> register(@RequestBody @Valid DriverCompanyRegistrationRequestDTO request) {
        RegistrationResponseDTO response = driverCompanyRegistrationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
