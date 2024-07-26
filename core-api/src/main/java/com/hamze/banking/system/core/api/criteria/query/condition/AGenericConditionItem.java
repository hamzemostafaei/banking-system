package com.hamze.banking.system.core.api.criteria.query.condition;


import com.hamze.banking.system.shared.util.ReflectionUtil;

public abstract class AGenericConditionItem<T> implements IGenericConditionItem<T> {

    private final Class<T> conditionDataType;

    private final ConditionTypeEnum conditionType;

    private T conditionData;

    public AGenericConditionItem(ConditionTypeEnum conditionType) {
        this(conditionType, null);
    }

    @SuppressWarnings("unchecked")
    public AGenericConditionItem(ConditionTypeEnum conditionType, T conditionData) {

        assert conditionType != null;

        if (conditionData != null) {
            this.conditionDataType = (Class<T>) conditionData.getClass();
        } else {
            this.conditionDataType = ReflectionUtil.getGenericParameterType(this, 0);
        }

        this.conditionType = conditionType;
        this.conditionData = conditionData;

    }

    public Class<T> getConditionDataType() {
        return conditionDataType;
    }

    public ConditionTypeEnum getConditionType() {
        return conditionType;
    }

    @Override
    public T getConditionData() {
        return conditionData;
    }

    public void setConditionData(T conditionData) {
        this.conditionData = conditionData;
    }
}
