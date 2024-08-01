package com.hamze.banking.system.core.api.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CustomerDTO extends ABaseDTO {
    private Integer customerNumber;
    private String firstName;
    private String lastName;
    private String nationalId;
}
