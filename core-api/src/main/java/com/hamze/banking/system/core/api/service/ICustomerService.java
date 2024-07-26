package com.hamze.banking.system.core.api.service;

import com.hamze.banking.system.core.api.criteria.CustomerCriteria;
import com.hamze.banking.system.core.api.data.CustomerDTO;
import com.hamze.banking.system.data.access.entity.CustomerEntity;
import com.hamze.banking.system.data.access.repository.api.ICustomerRepository;

public interface ICustomerService extends ICoreService<CustomerDTO,
                                                       CustomerEntity,
                                                       Integer,
                                                       CustomerCriteria,
                                                       ICustomerRepository> {
    CustomerDTO createCustomer(CustomerDTO serviceRequest);
}
