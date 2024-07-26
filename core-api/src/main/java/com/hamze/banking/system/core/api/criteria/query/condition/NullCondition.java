package com.hamze.banking.system.core.api.criteria.query.condition;

public class NullCondition<T> extends AGenericConditionItem<T> {

    public NullCondition(T conditionData) {
        super(ConditionTypeEnum.Null, conditionData);
    }

}
