package com.hamze.banking.system.core.service.account.strategy;

import com.hamze.banking.system.core.api.data.account.custom.EntryNatureEnum;
import com.hamze.banking.system.core.api.data.account.custom.TransactionRequestDTO;
import com.hamze.banking.system.core.api.data.account.custom.TransactionTypeEnum;
import com.hamze.banking.system.core.api.data.account.custom.VoucherDTO;
import com.hamze.banking.system.core.api.service.IAccountTurnoverService;
import com.hamze.banking.system.core.api.service.ITransactionStrategy;
import org.springframework.stereotype.Component;

@Component
public class CreateTransactionStrategy implements ITransactionStrategy<TransactionRequestDTO> {

    private final IAccountTurnoverService accountTurnoverService;

    public CreateTransactionStrategy(IAccountTurnoverService accountTurnoverService) {
        this.accountTurnoverService = accountTurnoverService;
        TransactionStrategyRegistry.register(TransactionTypeEnum.Open,this);
    }

    @Override
    public VoucherDTO doTransaction(TransactionRequestDTO transactionRequest) {
        return accountTurnoverService.addEntry(transactionRequest, TransactionTypeEnum.Open, EntryNatureEnum.Credit);
    }
}
