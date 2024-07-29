package com.hamze.banking.system.core.api.criteria.query.predicate;

import com.blazebit.persistence.CriteriaBuilder;
import com.hamze.banking.system.core.api.criteria.query.condition.ConditionTypeEnum;
import com.hamze.banking.system.core.api.criteria.query.condition.IGenericConditionItem;
import com.hamze.banking.system.data.access.entity.ABaseEntity;
import org.springframework.stereotype.Component;

@Component("LikePredicate")
public class LikePredicate<E extends ABaseEntity, T extends IGenericConditionItem<?>> implements IPredicate<E, T> {

    public LikePredicate() {
        PredicateFactoryRegistry.register(ConditionTypeEnum.Like, this);
    }

    @Override
    public CriteriaBuilder<E> buildPredicate(CriteriaBuilder<E> cb, String fieldName, T condition) {
        return cb.where(fieldName).like().value(condition.getConditionData()).noEscape();
    }
}