package com.hamze.banking.system.web.api.data.account.statement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class AccountTurnoverEdgeDTO {

    @JsonProperty("turnoverDate")
    private Date turnoverDate;

    @JsonProperty("turnoverNumber")
    private Long turnoverNumber;

    @JsonProperty("entryNumber")
    private Integer entryNumber;

    @JsonProperty("description")
    private String description;

    @JsonProperty("transactionType")
    private String transactionType;

    @JsonProperty("debit")
    private BigDecimal debit;

    @JsonProperty("credit")
    private BigDecimal credit;

    @JsonProperty("balance")
    private BigDecimal balance;

}
