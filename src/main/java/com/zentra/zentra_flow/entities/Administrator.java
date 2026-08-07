package com.zentra.zentra_flow.entities;

import com.zentra.zentra_flow.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "administrators")
@NoArgsConstructor
public class Administrator extends Client{

    public Administrator(String name, String email, String passwordHash) {
        super(name, email, passwordHash, Role.ADMINISTRATOR);
    }

}
