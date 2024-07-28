package com.hamze.banking.system.core.api.data.account.custom;

import com.hamze.banking.system.shared.data.base.enumeration.IMappedEnum;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum EntryNatureEnum implements IMappedEnum<Integer> {
    Debit(0),
    Credit(1);

    private static final Map<Integer, EntryNatureEnum> VALUE_MAP = new HashMap<>();

    static {
        for (EntryNatureEnum value : EntryNatureEnum.values()) {
            VALUE_MAP.put(value.code, value);
        }
    }

    private final Integer code;

    EntryNatureEnum(Integer code) {
        this.code = code;
    }

    public static EntryNatureEnum getByValue(Integer code) {
        EntryNatureEnum value = VALUE_MAP.get(code);
        if (value == null) {
            throw new IllegalArgumentException("Bad code [" + code + "] is provided.");
        }
        return value;
    }

    @Override
    public Integer getMapping() {
        return getCode();
    }
}
