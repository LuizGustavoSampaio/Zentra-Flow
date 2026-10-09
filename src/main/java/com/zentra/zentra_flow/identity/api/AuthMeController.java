package com.zentra.zentra_flow.identity.api;


import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthMeController {

    @GetMapping("/me")
    public String me(Authentication authentication) {
        return "Authenticated as: " + authentication.getName();
    }

}
