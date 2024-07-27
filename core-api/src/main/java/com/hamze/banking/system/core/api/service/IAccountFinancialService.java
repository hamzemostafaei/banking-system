package com.hamze.banking.system.core.api.service;

import com.hamze.banking.system.core.api.criteria.AccountTurnoverCriteria;
import com.hamze.banking.system.core.api.data.account.AccountTurnoverDTO;
import com.hamze.banking.system.core.api.data.account.custom.TransactionRequestDTO;
import com.hamze.banking.system.core.api.data.account.custom.TransferRequestDTO;
import com.hamze.banking.system.core.api.data.account.custom.VoucherDTO;
import com.hamze.banking.system.data.access.entity.AccountTurnoverEntity;
import com.hamze.banking.system.data.access.entity.id.AccountTurnoverEntityId;
import com.hamze.banking.system.data.access.repository.api.IAccountTurnoverRepository;

public interface IAccountFinancialService extends ICoreService<AccountTurnoverDTO,
                                                               AccountTurnoverEntity,
                                                               AccountTurnoverEntityId,
                                                               AccountTurnoverCriteria,
                                                               IAccountTurnoverRepository> {

    VoucherDTO credit(TransactionRequestDTO request, IBankAccountService bankAccountService);
    VoucherDTO debit(TransactionRequestDTO request, IBankAccountService bankAccountService);
    VoucherDTO transfer(TransferRequestDTO request, IBankAccountService bankAccountService);
}
