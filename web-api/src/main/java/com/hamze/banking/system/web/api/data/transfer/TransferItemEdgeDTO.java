package com.hamze.banking.system.web.api.data.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hamze.banking.system.web.api.data.ABaseEdgeRequestDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
public class TransferItemEdgeDTO extends ABaseEdgeRequestDTO {

    @JsonProperty("accountNumber")
    private String accountNumber;

    @JsonProperty("description")
    private String description;

    @JsonProperty("amount")
    private BigDecimal amount;
}
