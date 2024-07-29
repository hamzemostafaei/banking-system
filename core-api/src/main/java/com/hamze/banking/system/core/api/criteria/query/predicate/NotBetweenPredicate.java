package com.hamze.banking.system.core.api.criteria.query.predicate;

import com.blazebit.persistence.CriteriaBuilder;
import com.hamze.banking.system.core.api.criteria.query.BetweenCriteriaDTO;
import com.hamze.banking.system.core.api.criteria.query.condition.ConditionTypeEnum;
import com.hamze.banking.system.core.api.criteria.query.condition.IGenericConditionItem;
import com.hamze.banking.system.data.access.entity.ABaseEntity;
import org.springframework.stereotype.Component;

@Component("NotBetweenPredicate")
public class NotBetweenPredicate<E extends ABaseEntity, T extends IGenericConditionItem<BetweenCriteriaDTO<?, ?>>>
        implements IPredicate<E, T> {

    public NotBetweenPredicate() {
        PredicateFactoryRegistry.register(ConditionTypeEnum.NotBetween, this);
    }

    @Override
    public CriteriaBuilder<E> buildPredicate(CriteriaBuilder<E> cb, String fieldName, T between) {
        cb.where(fieldName).notBetween(between.getConditionData().getFrom()).and(between.getConditionData().getTo());
        return cb;
    }

}