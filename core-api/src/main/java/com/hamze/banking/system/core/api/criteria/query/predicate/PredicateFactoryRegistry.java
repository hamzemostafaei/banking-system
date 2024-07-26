package com.hamze.banking.system.core.api.criteria.query.predicate;


import com.hamze.banking.system.core.api.criteria.query.condition.ConditionTypeEnum;
import com.hamze.banking.system.core.api.criteria.query.condition.IGenericConditionItem;
import com.hamze.banking.system.data.access.entity.ABaseEntity;

import java.util.HashMap;
import java.util.Map;

public class PredicateFactoryRegistry {
    private static final Map<ConditionTypeEnum, IPredicateFactory<? extends ABaseEntity, ? extends IGenericConditionItem<?>>> builderMap = new HashMap<>();

    public static synchronized void register(ConditionTypeEnum conditionType,
                                             IPredicateFactory<? extends ABaseEntity, ? extends IGenericConditionItem<?>> conditionItem) {

        builderMap.putIfAbsent(conditionType, conditionItem);
    }

    @SuppressWarnings("unchecked")
    public static <E extends ABaseEntity, T extends IGenericConditionItem<?>> IPredicateFactory<E, T> getBuilder(ConditionTypeEnum conditionType) {
        return (IPredicateFactory<E, T>) builderMap.get(conditionType);
    }
}