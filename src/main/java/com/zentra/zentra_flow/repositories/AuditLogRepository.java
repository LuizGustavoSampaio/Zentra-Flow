package com.zentra.zentra_flow.repositories;

import com.zentra.zentra_flow.entities.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuditLogRepository extends JpaRepository<AuditLog,UUID> {
}
