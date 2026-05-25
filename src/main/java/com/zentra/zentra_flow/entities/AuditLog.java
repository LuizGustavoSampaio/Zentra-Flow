package com.zentra.zentra_flow.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "audit_log")
@Getter
@Setter
@NoArgsConstructor
public class AuditLog extends BaseEntity {

    @Column(nullable = false)
    private String action;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "trace_id", nullable = false)
    private String traceId;


    public AuditLog(String action, String description, String traceId){
        this.action = action;
        this.description = description;
        this.traceId = traceId;
    }
}