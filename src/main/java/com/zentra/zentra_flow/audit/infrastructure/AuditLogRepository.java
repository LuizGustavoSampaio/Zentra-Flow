package com.zentra.zentra_flow.audit.infrastructure;

import com.zentra.zentra_flow.audit.domain.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog,UUID> {
}
