package com.hamze.banking.system.web.api.mapper;

import com.hamze.banking.system.core.api.data.DepositDTO;
import com.hamze.banking.system.web.api.data.DepositEdgeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IDepositDTOEdgeResponseMapper extends IEdgeMapper<DepositEdgeDTO, DepositDTO> {
}
