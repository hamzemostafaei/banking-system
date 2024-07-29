package com.hamze.banking.system.core.api.criteria.query.predicate;

import com.blazebit.persistence.CriteriaBuilder;
import com.hamze.banking.system.core.api.criteria.query.condition.ConditionTypeEnum;
import com.hamze.banking.system.core.api.criteria.query.condition.IGenericConditionItem;
import com.hamze.banking.system.data.access.entity.ABaseEntity;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("InPredicate")
public class InPredicate<E extends ABaseEntity, T extends IGenericConditionItem<?>> implements IPredicate<E, T> {

    public InPredicate() {
        PredicateFactoryRegistry.register(ConditionTypeEnum.In, this);
    }

    @Override
    public CriteriaBuilder<E> buildPredicate(CriteriaBuilder<E> cb, String fieldName, T condition) {
        return cb.where(fieldName).in((Collection<?>) condition.getConditionData());
    }
}