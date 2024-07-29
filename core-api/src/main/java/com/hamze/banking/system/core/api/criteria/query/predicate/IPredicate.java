package com.hamze.banking.system.core.api.criteria.query.predicate;

import com.blazebit.persistence.CriteriaBuilder;
import com.hamze.banking.system.core.api.criteria.query.condition.IGenericConditionItem;
import com.hamze.banking.system.data.access.entity.ABaseEntity;

public interface IPredicate<E extends ABaseEntity, T extends IGenericConditionItem<?>> {

    CriteriaBuilder<E> buildPredicate(CriteriaBuilder<E> cb, String fieldName, T condition);

}