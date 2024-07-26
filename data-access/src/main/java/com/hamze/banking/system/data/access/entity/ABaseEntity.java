package com.hamze.banking.system.data.access.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class ABaseEntity implements Serializable {

    @Basic
    @Version
    @Column(name = "VERSION", nullable = false)
    private Integer version;

    @Basic
    @Column(name = "CREATION_DATE", nullable = false)
    private Timestamp creationDate;

    @Basic
    @Column(name = "CREATOR_USER_ID", nullable = false)
    private Long creatorUserId;

    @Basic
    @Column(name = "LAST_UPDATE")
    private Timestamp lastUpdate;

    @Basic
    @Column(name = "UPDATER_USER_ID")
    private Long updaterUserId;

    @PrePersist
    public void prePersist() {
        this.creatorUserId = 1L;
        this.creationDate = new Timestamp(new Date().getTime());
    }

    @PreUpdate
    public void preUpdate() {
        this.updaterUserId = 1L;
        this.lastUpdate = new Timestamp(new Date().getTime());
    }
}