package com.hamze.banking.system.web.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OpenAccountRequestDTO extends ABaseEdgeRequestDTO {

    @JsonProperty("accountNumber")
    private String accountNumber;

    @JsonProperty("customerNumber")
    private Integer customerNumber;

    @JsonProperty("accountTitle")
    private String accountTitle;

    @JsonProperty("currency")
    private String currency;
}
