package com.hamze.banking.system.core.service.account.strategy;

import com.hamze.banking.system.core.api.data.account.custom.ABaseTransactionRequestDTO;
import com.hamze.banking.system.core.api.data.account.custom.TransactionTypeEnum;
import com.hamze.banking.system.core.api.service.ITransactionStrategy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionStrategyRegistry {

    private static final Map<TransactionTypeEnum, ITransactionStrategy<? extends ABaseTransactionRequestDTO>> registry = new ConcurrentHashMap<>();

    public static void register(TransactionTypeEnum transactionType, ITransactionStrategy<? extends ABaseTransactionRequestDTO> strategy) {
        registry.put(transactionType, strategy);
    }

    public static ITransactionStrategy<? extends ABaseTransactionRequestDTO> getStrategy(TransactionTypeEnum transactionType) {
        return registry.get(transactionType);
    }
}
