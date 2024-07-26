package com.hamze.banking.system.core.api.criteria.query.condition;

public class LessThanCondition<T> extends AGenericConditionItem<T> {

    public LessThanCondition(T conditionData) {
        super(ConditionTypeEnum.LessThan, conditionData);
    }
}
