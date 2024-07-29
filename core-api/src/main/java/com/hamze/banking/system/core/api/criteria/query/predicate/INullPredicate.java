package com.hamze.banking.system.core.api.criteria.query.predicate;

import com.blazebit.persistence.CriteriaBuilder;
import com.hamze.banking.system.core.api.criteria.query.condition.IGenericConditionItem;
import com.hamze.banking.system.data.access.entity.ABaseEntity;

public interface INullPredicate<E extends ABaseEntity,
                                T extends IGenericConditionItem<?>>
        extends IPredicate<E, T> {

    CriteriaBuilder<E> buildPredicate(CriteriaBuilder<E> cb, String fieldName);

    @Override
    default CriteriaBuilder<E> buildPredicate(CriteriaBuilder<E> cb, String fieldName, T condition) throws IllegalArgumentException{
        throw new UnsupportedOperationException("Null predicate factory does not have condition, so use the method which has no condition.");
    }
}