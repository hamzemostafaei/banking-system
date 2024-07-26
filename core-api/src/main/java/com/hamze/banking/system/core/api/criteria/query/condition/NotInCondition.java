package com.hamze.banking.system.core.api.criteria.query.condition;

public class NotInCondition<T> extends AGenericConditionItem<T> {

    public NotInCondition(T conditionData) {
        super(ConditionTypeEnum.NotIn, conditionData);
    }
}
