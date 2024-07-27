package com.hamze.banking.system.data.access.entity;

import com.hamze.banking.system.data.access.entity.id.AccountTurnoverEntityId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "ACCOUNT_TURNOVER")
public class AccountTurnoverEntity extends ABaseEntity {

    @EmbeddedId
    private AccountTurnoverEntityId turnoverId;

    @Column(name = "ACCOUNT_NUMBER", length = 19)
    private String accountNumber;

    @Column(name = "DESCRIPTION", nullable = false, length = 256)
    private String description;

    @Column(name = "TRANSACTION_TYPE", nullable = false)
    private String transactionType;

    @Column(name = "AMOUNT", nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(name = "ENTRY_BALANCE", nullable = false, precision = 18, scale = 2)
    private BigDecimal entryBalance;
}
