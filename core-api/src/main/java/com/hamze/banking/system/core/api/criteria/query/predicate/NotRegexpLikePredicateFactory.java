package com.hamze.banking.system.core.api.criteria.query.predicate;

import com.blazebit.persistence.CriteriaBuilder;
import com.hamze.banking.system.core.api.criteria.query.condition.ConditionTypeEnum;
import com.hamze.banking.system.core.api.criteria.query.condition.IGenericConditionItem;
import com.hamze.banking.system.data.access.entity.ABaseEntity;
import org.springframework.stereotype.Component;

@Component("NotRegexpLikePredicate")
public class NotRegexpLikePredicateFactory<E extends ABaseEntity,
                                           T extends IGenericConditionItem<?>>
        implements IPredicateFactory<E, T> {

    public NotRegexpLikePredicateFactory() {
        PredicateFactoryRegistry.register(ConditionTypeEnum.NotRegexpLike,this);
    }

    @Override
    public CriteriaBuilder<E> buildPredicate(CriteriaBuilder<E> cb, String fieldName, T condition) {
        //TODO should be implemented
        throw new RuntimeException("Is not implemented");
    }
}