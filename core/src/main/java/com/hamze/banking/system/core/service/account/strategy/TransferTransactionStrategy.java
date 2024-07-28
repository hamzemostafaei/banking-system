package com.hamze.banking.system.core.service.account.strategy;

import com.hamze.banking.system.core.api.data.account.custom.EntryNatureEnum;
import com.hamze.banking.system.core.api.data.account.custom.TransactionTypeEnum;
import com.hamze.banking.system.core.api.data.account.custom.TransferRequestDTO;
import com.hamze.banking.system.core.api.data.account.custom.VoucherDTO;
import com.hamze.banking.system.core.api.service.IAccountTurnoverService;
import com.hamze.banking.system.core.api.service.ITransactionStrategy;
import com.hamze.banking.system.shared.util.SnowFlakeUniqueIDGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TransferTransactionStrategy implements ITransactionStrategy<TransferRequestDTO> {

    private final IAccountTurnoverService accountTurnoverService;
    @Value("${com.hamze.banking.system.node-id}")
    private Long nodeId;

    public TransferTransactionStrategy(IAccountTurnoverService accountTurnoverService) {
        this.accountTurnoverService = accountTurnoverService;
        TransactionStrategyRegistry.register(TransactionTypeEnum.Transfer, this);
    }

    @Override
    public VoucherDTO doTransaction(TransferRequestDTO transactionRequest) {

        TransactionTypeEnum transactionType = TransactionTypeEnum.Transfer;
        Long turnoverNumber = SnowFlakeUniqueIDGenerator.generateNextId(nodeId);
        Date turnoverDate = new Date();

        accountTurnoverService.addEntry(transactionRequest.getSource(), transactionType, EntryNatureEnum.Debit, turnoverNumber, turnoverDate, 1);
        accountTurnoverService.addEntry(transactionRequest.getDestination(), transactionType, EntryNatureEnum.Credit, turnoverNumber, turnoverDate, 2);

        VoucherDTO response = new VoucherDTO();
        response.setTurnoverDate(turnoverDate);
        response.setTurnoverNumber(turnoverNumber);

        return response;
    }
}
