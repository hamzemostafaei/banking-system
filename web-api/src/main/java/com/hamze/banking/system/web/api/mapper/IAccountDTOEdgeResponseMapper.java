package com.hamze.banking.system.web.api.mapper;

import com.hamze.banking.system.core.api.data.account.AccountDTO;
import com.hamze.banking.system.web.api.data.AccountEdgeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IAccountDTOEdgeResponseMapper extends IEdgeMapper<AccountEdgeDTO, AccountDTO> {
}
