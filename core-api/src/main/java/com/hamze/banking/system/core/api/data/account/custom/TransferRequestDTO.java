package com.hamze.banking.system.core.api.data.account.custom;

import lombok.Data;

@Data
public class TransferRequestDTO {
    private TransactionRequestDTO source;
    private TransactionRequestDTO destination;
}
