package com.hamze.banking.system.data.access.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "ACCOUNT_TURNOVER")
public class AccountTurnoverEntity extends ABaseEntity {

    @Id
    @Column(name = "TURNOVER_ID", length = 19)
    private Long turnoverId;

    @Column(name = "ACCOUNT_NUMBER", length = 19)
    private String accountNumber;

    @Column(name = "TURNOVER_NUMBER")
    private Long turnoverNumber;

    @Column(name = "TURNOVER_DATE")
    private Date turnoverDate;

    @Column(name = "ENTRY_NUMBER", nullable = false)
    private Integer entryNumber;

    @Column(name = "DESCRIPTION", nullable = false, length = 256)
    private String description;

    @Column(name = "TRANSACTION_TYPE", nullable = false)
    private String transactionType;

    @Column(name = "AMOUNT", nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(name = "ENTRY_BALANCE", nullable = false, precision = 18, scale = 2)
    private BigDecimal entryBalance;

}
