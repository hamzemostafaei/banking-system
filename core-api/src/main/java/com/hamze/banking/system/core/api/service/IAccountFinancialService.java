package com.hamze.banking.system.core.api.service;

import com.hamze.banking.system.core.api.criteria.AccountTurnoverCriteria;
import com.hamze.banking.system.core.api.data.account.AccountTurnoverDTO;
import com.hamze.banking.system.core.api.data.account.custom.CreditAccountRequestDTO;
import com.hamze.banking.system.core.api.data.account.custom.DebitAccountRequestDTO;
import com.hamze.banking.system.core.api.data.account.custom.VoucherDTO;
import com.hamze.banking.system.data.access.entity.AccountTurnoverEntity;
import com.hamze.banking.system.data.access.repository.api.IAccountTurnoverRepository;

public interface IAccountFinancialService extends ICoreService<AccountTurnoverDTO,
                                                               AccountTurnoverEntity,
                                                               Long,
                                                               AccountTurnoverCriteria,
                                                               IAccountTurnoverRepository> {

    VoucherDTO credit(CreditAccountRequestDTO request,IBankAccountService bankAccountService);
    VoucherDTO debit(DebitAccountRequestDTO request,IBankAccountService bankAccountService);
}
