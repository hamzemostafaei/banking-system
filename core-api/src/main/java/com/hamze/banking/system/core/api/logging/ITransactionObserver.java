package com.hamze.banking.system.core.api.logging;

import com.hamze.banking.system.core.api.data.account.custom.TransactionTypeEnum;
import com.hamze.banking.system.core.api.data.account.custom.VoucherDTO;

public interface ITransactionObserver {
    void onTransaction(String accountNumber, TransactionTypeEnum transactionType, double amount, VoucherDTO voucherResponse);
}
