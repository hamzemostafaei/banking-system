package com.hamze.banking.system.core.api.criteria.query.condition;


import com.hamze.banking.system.core.api.criteria.query.BetweenCriteriaDTO;

public class BetweenCondition<T extends BetweenCriteriaDTO<?, ?>> extends AGenericConditionItem<T> {

    public BetweenCondition(T conditionData) {
        super(ConditionTypeEnum.Between, conditionData);
    }
}
