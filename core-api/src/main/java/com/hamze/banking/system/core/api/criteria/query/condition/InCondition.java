package com.hamze.banking.system.core.api.criteria.query.condition;

public class InCondition<T> extends AGenericConditionItem<T> {

    public InCondition(T conditionData) {
        super(ConditionTypeEnum.In, conditionData);
    }
}
