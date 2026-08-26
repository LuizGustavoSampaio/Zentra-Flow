package com.zentra.zentra_flow.identity.api;

import com.zentra.zentra_flow.identity.api.dto.LoginRequestDTO;
import com.zentra.zentra_flow.identity.api.dto.LoginResponseDTO;
import com.zentra.zentra_flow.identity.application.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) {

        LoginResponseDTO response = authenticationService.login(request);

        return ResponseEntity.ok(response);

    }
}
