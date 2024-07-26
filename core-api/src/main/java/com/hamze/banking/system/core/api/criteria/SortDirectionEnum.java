package com.hamze.banking.system.core.api.criteria;

import com.hamze.banking.system.shared.data.base.enumeration.IMappedEnum;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum SortDirectionEnum implements IMappedEnum<SortDirectionEnum, Integer> {

    Asc(0),
    Desc(1);

    private static final Map<Integer, SortDirectionEnum> VALUE_MAP = new HashMap<>();

    static {
        for (SortDirectionEnum value : SortDirectionEnum.values()) {
            VALUE_MAP.put(value.getCode(), value);
        }
    }

    private final int code;

    SortDirectionEnum(int code) {
        this.code = code;
    }

    public static SortDirectionEnum getByValue(Integer code) {
        if (code == null) {
            return null;
        }

        SortDirectionEnum value = VALUE_MAP.get(code);
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
