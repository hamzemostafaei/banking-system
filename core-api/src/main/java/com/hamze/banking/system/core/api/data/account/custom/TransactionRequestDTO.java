package com.hamze.banking.system.core.api.data.account.custom;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
public class TransactionRequestDTO extends ABaseTransactionRequestDTO{
    private String accountNumber;
    private String description;
    private BigDecimal amount;
}
