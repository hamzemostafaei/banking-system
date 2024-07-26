package com.hamze.banking.system.web.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CreateCustomerEdgeRequestDTO extends ABaseEdgeRequestDTO{
    @JsonProperty("customerNumber")
    private Integer customerNumber;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("nationalId")
    private String nationalId;
}
