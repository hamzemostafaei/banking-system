package com.hamze.banking.system.web.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OpenDepositEdgeRequestDTO extends ABaseEdgeRequestDTO {

    @JsonProperty("depositNumber")
    private String depositNumber;

    @JsonProperty("customerNumber")
    private Integer customerNumber;

    @JsonProperty("depositTitle")
    private String depositTitle;

    @JsonProperty("currency")
    private String currency;
}
