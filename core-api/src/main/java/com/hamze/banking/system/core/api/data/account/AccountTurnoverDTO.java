package com.hamze.banking.system.core.api.data.account;

import com.hamze.banking.system.core.api.data.ABaseDTO;
import com.hamze.banking.system.data.access.entity.id.AccountTurnoverEntityId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
public class AccountTurnoverDTO extends ABaseDTO {
    private AccountTurnoverEntityId turnoverId;
    private String accountNumber;
    private String description;
    private String transactionType;
    private BigDecimal amount;
    private BigDecimal entryBalance;
}
