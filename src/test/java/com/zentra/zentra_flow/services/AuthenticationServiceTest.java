package com.zentra.zentra_flow.services;


import com.zentra.zentra_flow.dto.LoginRequestDTO;
import com.zentra.zentra_flow.dto.LoginResponseDTO;
import com.zentra.zentra_flow.entities.Administrator;
import com.zentra.zentra_flow.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private SecurityAuditLogger auditLogger;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthenticationService authenticationService;


    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(AuthenticationService.class, "maxAttempts", 5);
        ReflectionTestUtils.setField(AuthenticationService.class, "durationMinutes", 15);
    }

    @Test
    void returnsToken() {
        String email = "admin@zentraflow.com";
        String rawPassword = "senha123";
        String hashedPassword = "fake-hashed-password";

        Administrator admin = new Administrator("Admin", email, hashedPassword);
        LoginRequestDTO request = new LoginRequestDTO(email, rawPassword);

        when(clientRepository.findByEmail(email)).thenReturn(Optional.of(admin));
        when(passwordEncoder.matches(rawPassword, hashedPassword)).thenReturn(true);
        when(tokenService.generateToken(admin)).thenReturn("GPMkwY4tBP4ZUmujU0nC7qqFEsEaQA2ZorIHbuEsuFu");

        LoginResponseDTO response = authenticationService.login(request);

        assertThat(response.token()).isEqualTo("GPMkwY4tBP4ZUmujU0nC7qqFEsEaQA2ZorIHbuEsuFu");
        assertThat(response.email()).isEqualTo(email);
        assertThat(response.type()).isEqualTo("Bearer");

    }


}
