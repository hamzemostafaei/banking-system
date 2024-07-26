package com.hamze.banking.system.core.api.criteria.query.condition;

public class GreaterThanOrEqualToCondition<T> extends AGenericConditionItem<T> {

    public GreaterThanOrEqualToCondition(T conditionData) {
        super(ConditionTypeEnum.GreaterThanOrEqualTo, conditionData);
    }
}
