package com.hamze.banking.system.core.api.criteria.query.condition;

public class LikeCondition<T> extends AGenericConditionItem<T> {

    public LikeCondition(T conditionData) {
        super(ConditionTypeEnum.Like, conditionData);
    }

}