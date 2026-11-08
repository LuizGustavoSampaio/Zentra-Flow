package com.zentra.zentra_flow.scheduling.infrastructure;

import com.zentra.zentra_flow.scheduling.domain.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {}
