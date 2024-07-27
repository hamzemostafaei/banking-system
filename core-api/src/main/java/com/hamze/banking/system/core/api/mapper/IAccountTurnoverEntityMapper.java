package com.hamze.banking.system.core.api.mapper;

import com.hamze.banking.system.core.api.data.account.AccountTurnoverDTO;
import com.hamze.banking.system.data.access.entity.AccountTurnoverEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IAccountTurnoverEntityMapper extends IEntityMapper<AccountTurnoverEntity, AccountTurnoverDTO> {
}
