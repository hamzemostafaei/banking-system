package com.hamze.banking.system.web.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GetAccountDetailsEdgeRequestDTO extends ABaseEdgeRequestDTO {

    @JsonProperty("accountNumber")
    private String accountNumber;
}
