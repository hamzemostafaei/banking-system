package com.hamze.banking.system.core.service.account.strategy;

import com.hamze.banking.system.core.api.data.account.custom.ABaseTransactionRequestDTO;
import com.hamze.banking.system.core.api.data.account.custom.TransactionTypeEnum;
import com.hamze.banking.system.core.api.service.ITransactionStrategy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionStrategyRegistry {

    private static final Map<TransactionTypeEnum, ITransactionStrategy<? extends ABaseTransactionRequestDTO>> registry = new ConcurrentHashMap<>();

    public static <T extends ABaseTransactionRequestDTO> void register(TransactionTypeEnum transactionType, ITransactionStrategy<T> strategy) {
        registry.put(transactionType, strategy);
    }

    @SuppressWarnings("unchecked")
    public static <T extends ABaseTransactionRequestDTO> ITransactionStrategy<T> getStrategy(TransactionTypeEnum transactionType) {
        return (ITransactionStrategy<T>) registry.get(transactionType);
    }
}
