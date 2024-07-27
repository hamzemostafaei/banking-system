package com.hamze.banking.system.web.api.data.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hamze.banking.system.web.api.data.ABaseEdgeRequestDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TransferEdgeRequestDTO extends ABaseEdgeRequestDTO {

    @JsonProperty("source")
    private TransferItemEdgeDTO source;

    @JsonProperty("destination")
    private TransferItemEdgeDTO destination;
}
