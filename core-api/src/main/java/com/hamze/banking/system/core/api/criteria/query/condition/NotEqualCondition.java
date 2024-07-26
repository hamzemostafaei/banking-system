package com.hamze.banking.system.core.api.criteria.query.condition;

public class NotEqualCondition<T> extends AGenericConditionItem<T> {

    public NotEqualCondition(T conditionData) {
        super(ConditionTypeEnum.NotEqual, conditionData);
    }
}
