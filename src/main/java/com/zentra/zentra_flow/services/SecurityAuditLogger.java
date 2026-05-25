package com.zentra.zentra_flow.services;

import com.zentra.zentra_flow.entities.AuditLog;
import com.zentra.zentra_flow.repositories.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityAuditLogger {

    private final AuditLogRepository auditLogRepository;
    private static final String TRACE_ID_KEY = "traceId";

    /*
     * It registers an audit in the database and in the log file simultaneously.
     * @param acao O type of operation (Ex: "login-failled")
     * @param descricao O Detailed log description
     */

    public void log(String action, String description){
        String traceId = MDC.get(TRACE_ID_KEY);
        if(traceId == null || traceId.isEmpty()) {
            traceId = "SYSTEM";
        }

        AuditLog auditLog = new AuditLog(action, description, traceId);

        try {
            auditLogRepository.save(auditLog);
            log.info("Registered audit -> Action: [{}], Description: [{}]", action, description);
        } catch (Exception e) {
            log.error("Critic Error: Failed to save audit log to database! ", e);
        }

    }


}
