package com.hamze.banking.system.core.api.service;

import com.hamze.banking.system.core.api.criteria.AccountCriteria;
import com.hamze.banking.system.core.api.data.account.AccountDTO;
import com.hamze.banking.system.core.api.data.account.custom.TransactionRequestDTO;
import com.hamze.banking.system.core.api.data.account.custom.TransferRequestDTO;
import com.hamze.banking.system.core.api.data.account.custom.VoucherDTO;
import com.hamze.banking.system.data.access.entity.AccountEntity;
import com.hamze.banking.system.data.access.repository.api.IAccountRepository;

public interface IBankAccountService extends ICoreService<AccountDTO,
                                                          AccountEntity,
                                                          String,
                                                          AccountCriteria,
                                                          IAccountRepository> {

    AccountDTO createAccount(AccountDTO request);
    VoucherDTO credit(TransactionRequestDTO request);
    VoucherDTO debit(TransactionRequestDTO request);

    VoucherDTO transfer(TransferRequestDTO request);
}
