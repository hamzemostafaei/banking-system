package com.hamze.banking.system.web.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class ABaseEdgeRequestDTO {

    @JsonProperty("trackingNumber")
    protected String trackingNumber;

    @JsonProperty("transactionId")
    protected String transactionId;

}
