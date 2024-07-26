package com.hamze.banking.system.core.api.criteria.query.condition;

import com.hamze.banking.system.shared.data.base.enumeration.IMappedEnum;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum ConditionTypeEnum implements IMappedEnum<ConditionTypeEnum, Integer> {

    Equal(0, EqualCondition.class),
    NotEqual(1, NotEqualCondition.class),
    GreaterThan(2, GreaterThanCondition.class),
    GreaterThanOrEqualTo(3, GreaterThanOrEqualToCondition.class),
    LessThan(4, LessThanCondition.class),
    LessThanOrEqualTo(5, LessThanOrEqualToCondition.class),
    In(6, InCondition.class),
    NotIn(7, NotInCondition.class),
    Between(8, BetweenCondition.class),
    NotBetween(9, NotBetweenCondition.class),
    Like(10, LikeCondition.class),
    NotLike(11, NotLikeCondition.class),
    RegexpLike(12, RegexpLikeCondition.class),
    NotRegexpLike(13, NotRegexpLikeCondition.class),
    Null(14, NullCondition.class),
    NotNull(15, NotNullCondition.class);

    private static final Map<Integer, ConditionTypeEnum> VALUE_MAP = new HashMap<>();

    static {
        for (ConditionTypeEnum value : ConditionTypeEnum.values()) {
            VALUE_MAP.put(value.getTypeCode(), value);
        }
    }

    private final int typeCode;

    private final Class<?> conditionItemType;

    ConditionTypeEnum(int typeCode, Class<?> conditionItemType) {
        this.typeCode = typeCode;
        this.conditionItemType = conditionItemType;
    }

    public static ConditionTypeEnum getByValue(Integer typeCode) {
        if (typeCode == null) {
            return null;
        }

        ConditionTypeEnum value = VALUE_MAP.get(typeCode);
        if (value == null) {
            throw new IllegalArgumentException("Bad type code [" + typeCode + "] is provided.");
        }

        return value;
    }

    @Override
    public Integer getMapping() {
        return getTypeCode();
    }
}