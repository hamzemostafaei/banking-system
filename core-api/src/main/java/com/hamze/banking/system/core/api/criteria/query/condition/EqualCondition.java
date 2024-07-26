package com.hamze.banking.system.core.api.criteria.query.condition;

public class EqualCondition<T> extends AGenericConditionItem<T> {

    public EqualCondition(T conditionData) {
        super(ConditionTypeEnum.Equal, conditionData);
    }
}