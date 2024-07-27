package com.hamze.banking.system.web.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
public class CreateAccountEdgeRequestDTO extends ABaseEdgeRequestDTO {

    @JsonProperty("accountNumber")
    private String accountNumber;

    @JsonProperty("customerNumber")
    private Integer customerNumber;

    @JsonProperty("accountTitle")
    private String accountTitle;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("openAmount")
    private BigDecimal openAmount;
}
