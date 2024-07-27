package com.hamze.banking.system.core.api.mapper;

import com.hamze.banking.system.core.api.data.account.AccountDTO;
import com.hamze.banking.system.data.access.entity.AccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IAccountEntityMapper extends IEntityMapper<AccountEntity, AccountDTO> {
}
