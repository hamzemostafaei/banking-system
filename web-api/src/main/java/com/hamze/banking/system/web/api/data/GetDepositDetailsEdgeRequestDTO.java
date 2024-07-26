package com.hamze.banking.system.web.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GetDepositDetailsEdgeRequestDTO extends ABaseEdgeRequestDTO {

    @JsonProperty("depositNumber")
    private String depositNumber;
}
