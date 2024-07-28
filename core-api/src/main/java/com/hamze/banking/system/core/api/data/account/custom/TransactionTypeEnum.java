package com.hamze.banking.system.core.api.data.account.custom;

import com.hamze.banking.system.shared.data.base.enumeration.IMappedEnum;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum TransactionTypeEnum implements IMappedEnum<String> {
    Open("D1001"),
    Deposit("D1002"),
    Withdraw("D1003"),
    Transfer("D1004");

    private static final Map<String, TransactionTypeEnum> VALUE_MAP = new HashMap<>();

    static {
        for (TransactionTypeEnum value : TransactionTypeEnum.values()) {
            VALUE_MAP.put(value.transactionType, value);
        }
    }

    private final String transactionType;

    TransactionTypeEnum(String transactionType) {
        this.transactionType = transactionType;
    }

    @SuppressWarnings("unused")
    public static TransactionTypeEnum getByValue(String transactionType) {
        TransactionTypeEnum value = VALUE_MAP.get(transactionType);
        if (value == null) {
            throw new IllegalArgumentException("Bad transaction type [" + transactionType + "] is provided.");
        }
        return value;
    }

    @Override
    public String getMapping() {
        return getTransactionType();
    }
}
