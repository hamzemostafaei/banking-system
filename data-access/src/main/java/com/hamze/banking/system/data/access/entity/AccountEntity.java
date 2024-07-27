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
@Table(name = "ACCOUNT")
public class AccountEntity extends ABaseEntity {

    @Id
    @Column(name = "ACCOUNT_NUMBER", length = 21)
    private String accountNumber;

    @Column(name = "CUSTOMER_NUMBER")
    private Integer customerNumber;

    @Column(name = "ACCOUNT_TITLE", length = 256)
    private String accountTitle;

    @Column(name = "CURRENCY", length = 3)
    private String currency;

    @Column(name = "OPEN_DATE")
    private Date openDate;

    @Column(name = "CLOSE_DATE")
    private Date closeDate;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "OPEN_AMOUNT", precision = 22, scale = 2)
    private BigDecimal openAmount;

    @Column(name = "BALANCE", precision = 22, scale = 2)
    private BigDecimal balance;

}