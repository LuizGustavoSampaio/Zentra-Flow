package com.zentra.zentra_flow.scheduling.api;

import com.zentra.zentra_flow.scheduling.api.dto.ProcedureRegistrationRequestDTO;
import com.zentra.zentra_flow.scheduling.api.dto.ProcedureResponseDTO;
import com.zentra.zentra_flow.scheduling.application.ProcedureRegistrationService;
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
@RequestMapping("/api/clinics/me/procedures")
@RequiredArgsConstructor
public class ProcedureController {

    private final ProcedureRegistrationService procedureRegistrationService;

    @PostMapping
    @PreAuthorize("hasRole('CLINIC')")
    public ResponseEntity<ProcedureResponseDTO> register(
            Authentication authentication,
            @RequestBody @Valid ProcedureRegistrationRequestDTO request) {

        ProcedureResponseDTO response = procedureRegistrationService.register(authentication.getName(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
