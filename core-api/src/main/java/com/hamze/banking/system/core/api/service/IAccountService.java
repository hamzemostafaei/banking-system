package com.hamze.banking.system.core.api.service;

import com.hamze.banking.system.core.api.criteria.AccountCriteria;
import com.hamze.banking.system.core.api.data.account.AccountDTO;
import com.hamze.banking.system.data.access.entity.AccountEntity;
import com.hamze.banking.system.data.access.repository.api.IAccountRepository;

public interface IAccountService extends ICoreService<AccountDTO,
                                                      AccountEntity,
                                                      String,
                                                      AccountCriteria,
                                                      IAccountRepository> {

    AccountDTO create(AccountDTO request);
}
