package com.hamze.banking.system.web.api.mapper;

import com.hamze.banking.system.core.api.data.CustomerDTO;
import com.hamze.banking.system.web.api.data.CustomerEdgeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICustomerDTOEdgeResponseMapper extends IEdgeMapper<CustomerEdgeDTO, CustomerDTO> {
}
