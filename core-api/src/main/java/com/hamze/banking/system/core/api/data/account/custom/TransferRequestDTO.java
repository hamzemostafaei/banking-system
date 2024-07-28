package com.hamze.banking.system.core.api.data.account.custom;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TransferRequestDTO extends ABaseTransactionRequestDTO {
    private TransactionRequestDTO source;
    private TransactionRequestDTO destination;
}
