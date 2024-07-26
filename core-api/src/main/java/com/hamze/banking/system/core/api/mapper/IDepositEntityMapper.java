package com.hamze.banking.system.core.api.mapper;

import com.hamze.banking.system.core.api.data.DepositDTO;
import com.hamze.banking.system.data.access.entity.DepositEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IDepositEntityMapper extends IEntityMapper<DepositEntity, DepositDTO> {
}
