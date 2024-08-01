package com.hamze.banking.system.core.api.criteria;

import com.hamze.banking.system.core.api.criteria.query.condition.Condition;
import com.hamze.banking.system.core.api.criteria.query.condition.ConditionTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CustomerCriteria extends ABaseSearchCriteria {

    @Condition(type = ConditionTypeEnum.Equal, fieldName = "customerNumber")
    private Integer customerNumberEquals;

    @Condition(type = ConditionTypeEnum.Equal, fieldName = "nationalId")
    private String nationalIdEquals;
}
