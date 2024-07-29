package com.hamze.banking.system.core.api.criteria.query.predicate;

import com.blazebit.persistence.CriteriaBuilder;
import com.hamze.banking.system.core.api.criteria.query.condition.ConditionTypeEnum;
import com.hamze.banking.system.core.api.criteria.query.condition.IGenericConditionItem;
import com.hamze.banking.system.data.access.entity.ABaseEntity;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;

@Component("RegexpLikePredicate")
public class RegexpLikePredicate<E extends ABaseEntity, T extends IGenericConditionItem<?>> implements IPredicate<E, T> {

    public RegexpLikePredicate() {
        PredicateFactoryRegistry.register(ConditionTypeEnum.RegexpLike, this);
    }

    @Override
    public CriteriaBuilder<E> buildPredicate(CriteriaBuilder<E> cb, String fieldName, T condition) {
        throw new NotImplementedException("Is not implemented");
    }
}