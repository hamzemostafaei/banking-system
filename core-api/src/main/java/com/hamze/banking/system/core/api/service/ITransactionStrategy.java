package com.hamze.banking.system.core.api.service;

import com.hamze.banking.system.core.api.data.account.custom.ABaseTransactionRequestDTO;
import com.hamze.banking.system.core.api.data.account.custom.VoucherDTO;

public interface ITransactionStrategy<T extends ABaseTransactionRequestDTO> {
    VoucherDTO doTransaction(T transactionRequest);
}
