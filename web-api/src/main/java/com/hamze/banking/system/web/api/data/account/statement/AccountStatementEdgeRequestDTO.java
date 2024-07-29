package com.hamze.banking.system.web.api.data.account.statement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hamze.banking.system.web.api.data.ABasePaginationEdgeRequestDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AccountStatementEdgeRequestDTO extends ABasePaginationEdgeRequestDTO {

    @JsonProperty("accountNumber")
    private String accountNumber;
}
