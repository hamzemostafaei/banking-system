package com.hamze.banking.system.data.access.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;
import com.hamze.banking.system.shared.data.base.enumeration.IMappedEnum;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum AccountStatusEnum implements IMappedEnum<Integer> {

    Active(0),
    Closed(1),
    Blocked(3);

    private static final Map<Integer, AccountStatusEnum> VALUE_MAP = new HashMap<>();

    static {
        for (AccountStatusEnum value : AccountStatusEnum.values()) {
            VALUE_MAP.put(value.getStatus(), value);
        }
    }

    @JsonValue
    private final Integer status;

    AccountStatusEnum(Integer status) {
        this.status = status;
    }

    public static AccountStatusEnum getByValue(Integer status) {
        if (status == null) {
            return null;
        }

        AccountStatusEnum value = VALUE_MAP.get(status);
        if (value == null) {
            throw new IllegalArgumentException("Bad status code [" + status + "] is provided.");
        }

        return value;
    }

    @Override
    public Integer getMapping() {
        return getStatus();
    }
}
