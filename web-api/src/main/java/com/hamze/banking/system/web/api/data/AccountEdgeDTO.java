package com.hamze.banking.system.web.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hamze.banking.system.data.access.enumeration.AccountStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class AccountEdgeDTO {

    @JsonProperty("accountNumber")
    private String accountNumber;

    @JsonProperty("customerNumber")
    private Integer customerNumber;

    @JsonProperty("accountTitle")
    private String accountTitle;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("openDate")
    private Date openDate;

    @JsonProperty("closeDate")
    private Date closeDate;

    @JsonProperty("status")
    private AccountStatusEnum status;

    @JsonProperty("openAmount")
    private BigDecimal openAmount;

    @JsonProperty("balance")
    private BigDecimal balance = BigDecimal.ZERO;

    @JsonProperty("holder")
    private CustomerEdgeDTO holder;
}
