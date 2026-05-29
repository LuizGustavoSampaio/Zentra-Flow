package com.zentra.zentra_flow.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    public Client(String name, String email, String passwordHash, Integer failedLoginAttempts) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.failedLoginAttempts = failedLoginAttempts = 0;
    }

    @Setter
    @Column(name = "name", nullable = false, length = 150)
    @NotBlank(message = "The Name field cannot be empty.")
    private String name;

    @Setter
    @Column(name = "email", unique = true, nullable = false, length = 150)
    @NotBlank(message = "The Name field cannot be empty.")
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
        if(this.failedLoginAttempts > maxAttempts) {
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
        if(lockUntil == null || isAccountLocked()){
            return 0;
        }
        Duration duration = Duration.between(LocalDateTime.now(), this.lockUntil);
        return (int) duration.toMinutes();
    }
}
