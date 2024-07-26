package com.hamze.banking.system.core.api.criteria.query.condition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Condition {

    ConditionTypeEnum type();

    String fieldName();
}