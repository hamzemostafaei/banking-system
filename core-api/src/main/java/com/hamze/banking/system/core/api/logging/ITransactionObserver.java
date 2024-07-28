package com.hamze.banking.system.core.api.logging;

import com.hamze.banking.system.core.api.data.account.custom.VoucherDTO;

public interface ITransactionObserver {
    void onTransaction(String accountNumber, String transactionType, double amount, VoucherDTO voucherResponse);
}
