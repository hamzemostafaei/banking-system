package com.hamze.banking.system.web.api.data.account.statement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.hamze.banking.system.data.access.enumeration.AccountStatusEnum;
import com.hamze.banking.system.web.api.data.ABasePaginationEdgeResponseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@JsonPropertyOrder({
        "accountNumber",
        "accountTitle",
        "status",
        "openDate",
        "closeDate",
        "balance",
        "turnovers"
})
@Data
@EqualsAndHashCode(callSuper = false)
public class AccountStatementEdgeDTO extends ABasePaginationEdgeResponseDTO {

    @JsonProperty("accountNumber")
    private String accountNumber;

    @JsonProperty("accountTitle")
    private String accountTitle;

    @JsonProperty("status")
    private AccountStatusEnum status;

    @JsonProperty("openDate")
    private Date openDate;

    @JsonProperty("closeDate")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date closeDate;

    @JsonProperty("balance")
    private BigDecimal balance;

    @JsonProperty("turnovers")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<AccountTurnoverEdgeDTO> turnovers;
}
