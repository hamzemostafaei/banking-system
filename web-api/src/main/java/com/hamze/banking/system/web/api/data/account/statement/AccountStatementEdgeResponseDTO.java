package com.hamze.banking.system.web.api.data.account.statement;

import com.hamze.banking.system.web.api.data.ABaseEdgeResponseDataDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AccountStatementEdgeResponseDTO extends ABaseEdgeResponseDataDTO<AccountStatementEdgeDTO> {
}
