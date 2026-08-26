package com.zentra.zentra_flow.identity.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "clients",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_clients_email", columnNames = "email")
        }
)
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@NoArgsConstructor
public abstract class Client extends BaseEntity {

    public Client(String name, String email, String passwordHash, Role role) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.failedLoginAttempts = 0;
        this.role = role;
    }

    @Setter(AccessLevel.NONE)
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 30)
    private Role role;

    @Setter
    @Column(name = "name", nullable = false, length = 150)
    @NotBlank(message = "The Name field cannot be empty.")
    private String name;

    @Setter
    @Column(name = "email", unique = true, nullable = false, length = 150)
    @NotBlank(message = "The Email field cannot be empty.")
    private String email;

    @Column(name = "password_hash", nullable = false)
    @Size(min = 60, max = 60,  message = "O hash da senha deve ter exatamente 60 caracteres.")
    private String passwordHash;

    @Setter
    @Column(name = "failed_login_attempts", nullable = false)
    private Integer failedLoginAttempts = 0;

    @Setter
    @Column(name = "lock_until")
    private LocalDateTime lockUntil;

    /*Block login if there are too many attempts.*/
    public void recordFailedLogin(int maxAttempts, int durationMinutes){
        this.failedLoginAttempts++;
        if(this.failedLoginAttempts >= maxAttempts) {
            this.lockUntil = LocalDateTime.now().plusMinutes(durationMinutes);
        }
    }

    /* reset failedLoginAttempts and lockUntil if login is correct */
    public void recordSuccessLogin(){
        this.failedLoginAttempts = 0;
        this.lockUntil = null;
    }

    /* Check if it is blocked. */
    public boolean isAccountLocked() {
        if(this.lockUntil == null){
            return false;
        }
        return LocalDateTime.now().isBefore(this.lockUntil);
    }

    /*Returns how many minutes are left to unlock the login.*/
    public int getMinutesUntilUnlock() {
        if (lockUntil == null || !isAccountLocked()) {
            return 0;
        }
        return (int) Duration.between(LocalDateTime.now(), this.lockUntil).toMinutes();
    }
}
