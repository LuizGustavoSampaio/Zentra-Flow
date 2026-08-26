package com.zentra.zentra_flow.identity.application;

import com.zentra.zentra_flow.identity.api.dto.LoginRequestDTO;
import com.zentra.zentra_flow.identity.api.dto.LoginResponseDTO;
import com.zentra.zentra_flow.identity.domain.Client;
import com.zentra.zentra_flow.identity.infrastructure.ClientRepository;
import com.zentra.zentra_flow.audit.application.SecurityAuditLogger;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final TokenService tokenService;
    private final SecurityAuditLogger auditLogger;
    private final ClientRepository clientRepository;
    private final BCryptPasswordEncoder passwordEncoder;



    @Value("${zentra-flow.security.max-login-attempts}")
    private int maxAttempts;

    @Value("${zentra-flow.security.lockout-duration-minutes}")
    private int durationMinutes;


    @Transactional
    public LoginResponseDTO login(LoginRequestDTO request) {

        String email = request.email();
        String password = request.password();

        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> {
                    auditLogger.log("LOGIN_FAILED",
                            "Attempted login with unregistered email: " + email);
                    return new RuntimeException("Invalid credentials.");
                });

        if (client.isAccountLocked()) {
            int minutesLeft = client.getMinutesUntilUnlock();
            auditLogger.log("LOGIN_BLOCKED",
                    "User " + email + " tried to log in with account blocked. Remain " + minutesLeft + " min.");
            throw new RuntimeException("Account temporarily blocked. Please try again in " + minutesLeft + " minutes.");
        }

        boolean isPassWordValid = passwordEncoder.matches(password, client.getPasswordHash());

        if (!isPassWordValid) {
            client.recordFailedLogin(maxAttempts, durationMinutes);
            clientRepository.save(client);

            if (client.isAccountLocked()) {
                auditLogger.log("ACCOUNT_LOCKED",
                        "Account " + email + "reached trial limit and was blocked by " + durationMinutes + " min.");
            } else {
                auditLogger.log("LOGIN_FAILED",
                        "Incorrect password for " + email + ". Attempt  nº " + client.getFailedLoginAttempts());
            }
            throw new RuntimeException("Invalid credentials.");
        }
        client.recordSuccessLogin();
        clientRepository.save(client);

        auditLogger.log("LOGIN_SUCCESS", "User " + email + " successfully authenticated.");

        String generateToken = tokenService.generateToken(client);
        return new LoginResponseDTO(generateToken, "Bearer", email);

    }
}
