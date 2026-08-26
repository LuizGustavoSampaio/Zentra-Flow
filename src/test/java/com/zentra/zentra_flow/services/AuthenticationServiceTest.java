package com.zentra.zentra_flow.services;


import com.zentra.zentra_flow.audit.application.SecurityAuditLogger;
import com.zentra.zentra_flow.identity.api.dto.LoginRequestDTO;
import com.zentra.zentra_flow.identity.api.dto.LoginResponseDTO;
import com.zentra.zentra_flow.identity.application.AuthenticationService;
import com.zentra.zentra_flow.identity.application.TokenService;
import com.zentra.zentra_flow.identity.domain.Administrator;
import com.zentra.zentra_flow.identity.infrastructure.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        ReflectionTestUtils.setField(authenticationService, "maxAttempts", 5);
        ReflectionTestUtils.setField(authenticationService, "durationMinutes", 15);
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

    @Test
    void shouldThrowExceptionWhenEmailDoesNotExist() {

        String email = "notexist@zentraflow.com";
        LoginRequestDTO request = new LoginRequestDTO(email, "password123");

        when(clientRepository.findByEmail(email)).thenReturn(Optional.empty());


        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> authenticationService.login(request)
        );

        assertThat(exception.getMessage()).isEqualTo("Invalid credentials.");
        verify(auditLogger).log("LOGIN_FAILED", "Attempted login with unregistered email: " + email);
        verify(tokenService, never()).generateToken(any());
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsInvalid() {
        String email = "admin@zentraflow.com";
        String rawPassword = "senha123";
        String hashedPassword = "fake-hashed-password";

        Administrator admin = new Administrator("Admin", email, hashedPassword);
        LoginRequestDTO request = new LoginRequestDTO(email, rawPassword);

        when(clientRepository.findByEmail(email)).thenReturn(Optional.of(admin));
        when(passwordEncoder.matches(rawPassword, hashedPassword)).thenReturn(false);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> authenticationService.login(request)
        );

        assertThat(exception.getMessage()).isEqualTo("Invalid credentials.");
        assertThat(admin.getFailedLoginAttempts()).isEqualTo(1);
        verify(clientRepository).save(admin);
        verify(auditLogger).log(eq("LOGIN_FAILED"), anyString());
        verify(tokenService, never()).generateToken(any());
    }

    @Test
    void shouldThrowExceptionWhenAccountIsLocked() {
        String email = "admin@zentraflow.com";
        String hashedPassword = "fake-hashed-password";

        Administrator admin = new Administrator("Admin", email, hashedPassword);
        ReflectionTestUtils.setField(admin, "lockUntil", LocalDateTime.now().plusMinutes(10));

        LoginRequestDTO request = new LoginRequestDTO(email, "anypassword123");

        when(clientRepository.findByEmail(email)).thenReturn(Optional.of(admin));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> authenticationService.login(request)
        );

        assertThat(exception.getMessage()).contains("Account temporarily blocked");

        verify(auditLogger).log(eq("LOGIN_BLOCKED"), anyString());
        verify(clientRepository, never()).save(any());

    }
}
