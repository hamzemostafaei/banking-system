package com.hamze.banking.system.core.api.data;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public abstract class ABaseDTO implements Serializable {
    private Integer version;
    private Timestamp creationDate;
    private Long creatorUserId;
    private Timestamp lastUpdate;
    private Long updaterUserId;
}
