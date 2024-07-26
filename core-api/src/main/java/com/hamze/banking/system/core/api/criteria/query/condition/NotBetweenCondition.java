package com.hamze.banking.system.core.api.criteria.query.condition;

public class NotBetweenCondition<T> extends AGenericConditionItem<T> {

    public NotBetweenCondition(T conditionData) {
        super(ConditionTypeEnum.NotBetween, conditionData);
    }
}
