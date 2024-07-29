package com.hamze.banking.system.core.api.criteria.query.predicate;


import com.hamze.banking.system.core.api.criteria.query.condition.ConditionTypeEnum;
import com.hamze.banking.system.core.api.criteria.query.condition.IGenericConditionItem;
import com.hamze.banking.system.data.access.entity.ABaseEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PredicateFactoryRegistry {
    private static final Map<ConditionTypeEnum, IPredicate<? extends ABaseEntity, ? extends IGenericConditionItem<?>>> registry = new ConcurrentHashMap<>();

    public static void register(ConditionTypeEnum conditionType, IPredicate<? extends ABaseEntity, ? extends IGenericConditionItem<?>> conditionItem) {
        registry.putIfAbsent(conditionType, conditionItem);
    }

    @SuppressWarnings("unchecked")
    public static <E extends ABaseEntity, T extends IGenericConditionItem<?>> IPredicate<E, T> getPredicate(ConditionTypeEnum conditionType) {
        return (IPredicate<E, T>) registry.get(conditionType);
    }
}