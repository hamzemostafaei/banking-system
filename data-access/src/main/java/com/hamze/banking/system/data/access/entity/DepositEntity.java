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
@Table(name = "DEPOSIT")
public class DepositEntity extends ABaseEntity {
    @Id
    @Column(name = "DEPOSIT_ID")
    private Long depositId;

    @Column(name = "DEPOSIT_NUMBER", length = 21)
    private String depositNumber;

    @Column(name = "CUSTOMER_NUMBER")
    private Integer customerNumber;

    @Column(name = "DEPOSIT_TITLE", length = 256)
    private String depositTitle;

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

}