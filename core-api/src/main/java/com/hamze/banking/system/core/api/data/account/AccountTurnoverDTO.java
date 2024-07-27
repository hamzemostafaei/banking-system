package com.hamze.banking.system.core.api.data.account;

import com.hamze.banking.system.core.api.data.ABaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class AccountTurnoverDTO extends ABaseDTO {
    private Long turnoverId;
    private String accountNumber;
    private Long turnoverNumber;
    private Date turnoverDate;
    private Integer entryNumber;
    private String description;
    private String transactionType;
    private BigDecimal amount;
    private BigDecimal entryBalance;
}
