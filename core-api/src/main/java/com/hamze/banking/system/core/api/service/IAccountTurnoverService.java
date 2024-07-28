package com.hamze.banking.system.core.api.service;

import com.hamze.banking.system.core.api.criteria.AccountTurnoverCriteria;
import com.hamze.banking.system.core.api.data.account.AccountTurnoverDTO;
import com.hamze.banking.system.core.api.data.account.custom.EntryNatureEnum;
import com.hamze.banking.system.core.api.data.account.custom.TransactionRequestDTO;
import com.hamze.banking.system.core.api.data.account.custom.TransactionTypeEnum;
import com.hamze.banking.system.core.api.data.account.custom.VoucherDTO;
import com.hamze.banking.system.data.access.entity.AccountTurnoverEntity;
import com.hamze.banking.system.data.access.entity.id.AccountTurnoverEntityId;
import com.hamze.banking.system.data.access.repository.api.IAccountTurnoverRepository;

import java.util.Date;

public interface IAccountTurnoverService extends ICoreService<AccountTurnoverDTO,
                                                               AccountTurnoverEntity,
                                                               AccountTurnoverEntityId,
                                                               AccountTurnoverCriteria,
                                                               IAccountTurnoverRepository> {

    VoucherDTO addEntry(TransactionRequestDTO request,TransactionTypeEnum transactionType,EntryNatureEnum nature);
    VoucherDTO addEntry(TransactionRequestDTO request,
                        TransactionTypeEnum transactionType,
                        EntryNatureEnum nature,
                        Long turnoverNumber,
                        Date turnoverDate,
                        Integer entryNumber);
}
