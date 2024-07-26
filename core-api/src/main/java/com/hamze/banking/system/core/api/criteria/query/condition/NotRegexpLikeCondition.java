package com.hamze.banking.system.core.api.criteria.query.condition;

public class NotRegexpLikeCondition<T> extends AGenericConditionItem<T> {

    public NotRegexpLikeCondition(T conditionData) {
        super(ConditionTypeEnum.NotRegexpLike, conditionData);
    }

}
