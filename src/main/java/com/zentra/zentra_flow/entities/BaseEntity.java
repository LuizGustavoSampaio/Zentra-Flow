package com.zentra.zentra_flow.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
@SQLRestriction("deleted = false")
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Spring Boot 3 faz a mágica sozinho
    private UUID id;

    @Version
    private Integer version;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @LastModifiedBy
    private String lastModifiedBy;

    @Column(name = "deleted")
    private boolean deleted = false;

    public UUID getId() {
        return id;
    }


    public Integer getVersion() {
        return version;
    }


    public LocalDateTime getCreatedDate() {
        return createdDate;
    }


    public String getCreatedBy() {
        return createdBy;
    }


    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }


    public String getLastModifiedBy() {
        return lastModifiedBy;
    }



    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
