package com.hamze.banking.system.core.api.criteria.query.condition;

public class LessThanOrEqualToCondition<T> extends AGenericConditionItem<T> {

    public LessThanOrEqualToCondition(T conditionData) {
        super(ConditionTypeEnum.LessThanOrEqualTo, conditionData);
    }
}
