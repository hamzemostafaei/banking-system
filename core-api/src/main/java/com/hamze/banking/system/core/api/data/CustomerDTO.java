package com.hamze.banking.system.core.api.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerDTO extends ABaseDTO {
    private Integer customerNumber;
    private String firstName;
    private String lastName;
    private String nationalId;
}
