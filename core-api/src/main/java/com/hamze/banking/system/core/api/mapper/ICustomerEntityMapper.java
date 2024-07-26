package com.hamze.banking.system.core.api.mapper;

import com.hamze.banking.system.core.api.data.CustomerDTO;
import com.hamze.banking.system.data.access.entity.CustomerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICustomerEntityMapper extends IEntityMapper<CustomerEntity, CustomerDTO> {
}
