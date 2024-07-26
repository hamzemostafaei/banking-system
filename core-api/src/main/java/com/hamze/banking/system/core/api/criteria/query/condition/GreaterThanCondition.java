package com.hamze.banking.system.core.api.criteria.query.condition;

public class GreaterThanCondition<T> extends AGenericConditionItem<T> {

    public GreaterThanCondition(T conditionData) {
        super(ConditionTypeEnum.GreaterThan, conditionData);
    }
}
