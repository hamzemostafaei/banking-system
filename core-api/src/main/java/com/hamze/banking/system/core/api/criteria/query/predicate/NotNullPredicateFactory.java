package com.hamze.banking.system.core.api.criteria.query.predicate;

import com.blazebit.persistence.CriteriaBuilder;
import com.hamze.banking.system.core.api.criteria.query.condition.ConditionTypeEnum;
import com.hamze.banking.system.core.api.criteria.query.condition.IGenericConditionItem;
import com.hamze.banking.system.data.access.entity.ABaseEntity;
import org.springframework.stereotype.Component;

@Component("NotNullPredicate")
public class NotNullPredicateFactory<E extends ABaseEntity,
                                     T extends IGenericConditionItem<?>>
        implements INullPredicateFactory<E, T> {

    public NotNullPredicateFactory() {
        PredicateFactoryRegistry.register(ConditionTypeEnum.NotNull,this);
    }

    @Override
    public CriteriaBuilder<E> buildPredicate(CriteriaBuilder<E> cb, String fieldName)throws IllegalArgumentException {
       return cb.where(fieldName).isNotNull();
    }
}