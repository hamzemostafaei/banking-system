package com.hamze.banking.system.core.api.service;

import com.hamze.banking.system.core.api.data.account.AccountDTO;
import com.hamze.banking.system.core.api.data.account.custom.TransactionRequestDTO;
import com.hamze.banking.system.core.api.data.account.custom.TransferRequestDTO;
import com.hamze.banking.system.core.api.data.account.custom.VoucherDTO;

public interface IBankAccountService {

    AccountDTO create(AccountDTO request);
    VoucherDTO credit(TransactionRequestDTO request);
    VoucherDTO debit(TransactionRequestDTO request);
    VoucherDTO transfer(TransferRequestDTO request);
    AccountDTO details(String accountNumber);
}
