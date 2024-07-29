package com.hamze.banking.system.core.api.logging;

import com.hamze.banking.system.core.api.data.account.custom.ABaseTransactionRequestDTO;
import com.hamze.banking.system.core.api.data.account.custom.TransactionTypeEnum;
import com.hamze.banking.system.core.api.data.account.custom.VoucherDTO;

public interface ITransactionObserver {
    void onTransaction(ABaseTransactionRequestDTO request, TransactionTypeEnum transactionType, VoucherDTO voucherResponse);
}
