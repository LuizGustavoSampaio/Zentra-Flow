package com.zentra.zentra_flow.services;

import com.zentra.zentra_flow.entities.Client;
import com.zentra.zentra_flow.repositories.ClientRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final ClientRepository clientRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${zentra-flow.security.max-login-attempts}")
    private int maxAttempts;

    @Value("${zentra-flow.security.lockout-duration-minutes}")
    private int durationMinutes;

    @Transactional
    public String login(String email,String rawPassword) {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("AUDIT [LOGIN_FAILED] - Attempt to log in with an unregistered email address: {}", email);
                    return new RuntimeException("Invalid credentials.");
                });

        if (client.isAccountLocked()) {
            int minutesLeft = client.getMinutesUntilUnlock();
            log.warn("AUDIT [LOGIN_BLOCKED] - User {} tried to log in but the account is blocked for {} more minutes.", email, minutesLeft);
            throw new RuntimeException("Account temporarily blocked. Please try again in " + minutesLeft + " minutes.");
        }

        boolean isPassWordValid = passwordEncoder.matches(rawPassword, client.getPasswordHash());

        if (!isPassWordValid) {
            client.recordFailedLogin(maxAttempts, durationMinutes);
            clientRepository.save(client);

            if (client.isAccountLocked()) {
                log.error("AUDIT [ACCOUNT_LOCKED] - Account {} has reached the error limit and has been BLOCKED for {} minutes.", email, durationMinutes);
            } else {
                log.warn("AUDIT [LOGIN_FAILED] - Incorrect password for user {}. Current attempts: {}", email, client.getFailedLoginAttempts());
            }
            throw new RuntimeException("Invalid credentials.");
        }
        client.recordSuccessLogin();
        clientRepository.save(client);

        log.info("AUDIT [LOGIN_SUCCESS] - User {} successfully authenticated.", email);

        return "Login successful!";

    }
}
