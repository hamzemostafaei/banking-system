package com.hamze.banking.system.core.api.criteria.query.predicate;

import com.blazebit.persistence.CriteriaBuilder;
import com.hamze.banking.system.core.api.criteria.query.BetweenCriteriaDTO;
import com.hamze.banking.system.core.api.criteria.query.condition.ConditionTypeEnum;
import com.hamze.banking.system.core.api.criteria.query.condition.IGenericConditionItem;
import com.hamze.banking.system.data.access.entity.ABaseEntity;
import org.springframework.stereotype.Component;

@Component("BetweenPredicate")
public class BetweenPredicate<E extends ABaseEntity,
                              T extends IGenericConditionItem<BetweenCriteriaDTO<?, ?>>>
        implements IPredicate<E, T> {

    public BetweenPredicate() {
        PredicateFactoryRegistry.register(ConditionTypeEnum.Between, this);
    }

    @Override
    public CriteriaBuilder<E> buildPredicate(CriteriaBuilder<E> cb, String fieldName, T between) {
        cb.where(fieldName).between(between.getConditionData().getFrom()).and(between.getConditionData().getTo());
        return cb;
    }

}