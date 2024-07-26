package com.hamze.banking.system.core.api.criteria.query.condition;

public class NotNullCondition<T> extends AGenericConditionItem<T> {

    public NotNullCondition(T conditionData) {
        super(ConditionTypeEnum.NotNull, conditionData);
    }

}
