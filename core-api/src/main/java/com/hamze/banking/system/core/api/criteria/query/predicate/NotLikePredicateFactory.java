package com.hamze.banking.system.core.api.criteria.query.predicate;

import com.blazebit.persistence.CriteriaBuilder;
import com.hamze.banking.system.core.api.criteria.query.condition.ConditionTypeEnum;
import com.hamze.banking.system.core.api.criteria.query.condition.IGenericConditionItem;
import com.hamze.banking.system.data.access.entity.ABaseEntity;
import org.springframework.stereotype.Component;

@Component("NotLikePredicate")
public class NotLikePredicateFactory<E extends ABaseEntity,
                                     T extends IGenericConditionItem<?>>
        implements IPredicateFactory<E, T> {

    public NotLikePredicateFactory() {
        PredicateFactoryRegistry.register(ConditionTypeEnum.NotLike,this);
    }

    @Override
    public CriteriaBuilder<E> buildPredicate(CriteriaBuilder<E> cb, String fieldName, T condition) {
        return cb.where(fieldName).notLike().value(condition.getConditionData()).noEscape();
    }
}