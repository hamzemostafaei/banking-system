package com.hamze.banking.system.core.api.criteria.query.condition;

public class RegexpLikeCondition<T> extends AGenericConditionItem<T> {

    public RegexpLikeCondition(T conditionData) {
        super(ConditionTypeEnum.RegexpLike, conditionData);
    }

}
