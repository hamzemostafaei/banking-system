package com.hamze.banking.system.core.api.criteria.query.condition;

public class NotLikeCondition<T> extends AGenericConditionItem<T> {

    public NotLikeCondition(T conditionData) {
        super(ConditionTypeEnum.NotLike, conditionData);
    }

}
