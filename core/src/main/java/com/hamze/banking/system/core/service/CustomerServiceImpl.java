package com.hamze.banking.system.core.service;

import com.hamze.banking.system.core.api.criteria.CustomerCriteria;
import com.hamze.banking.system.core.api.data.CustomerDTO;
import com.hamze.banking.system.core.api.exception.CoreServiceException;
import com.hamze.banking.system.core.api.service.ICustomerService;
import com.hamze.banking.system.data.access.entity.CustomerEntity;
import com.hamze.banking.system.data.access.repository.api.ICustomerRepository;
import com.hamze.banking.system.shared.data.base.dto.ErrorDTO;
import com.hamze.banking.system.shared.data.base.enumeration.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("CustomerService")
public class CustomerServiceImpl extends ABaseCoreService<CustomerDTO,
                                                          CustomerEntity,
                                                          Integer,
                                                          CustomerCriteria,
                                                          ICustomerRepository>
        implements ICustomerService {

    @Override
    public CustomerDTO createCustomer(CustomerDTO serviceRequest) {

        validateCustomerDuplication(serviceRequest);

        return save(serviceRequest);

    }

    private void validateCustomerDuplication(CustomerDTO serviceRequest) {
        if (existsById(serviceRequest.getCustomerNumber())) {
            String message = String.format("Customer number [%s] already exists", serviceRequest.getCustomerNumber());
            throw CoreServiceException.builder()
                    .message(message)
                    .error(new ErrorDTO(ErrorCodeEnum.DuplicateData, message, "CustomerNumber"))
                    .build();
        }

        CustomerCriteria criteria = new CustomerCriteria();
        criteria.setNationalIdEquals(serviceRequest.getNationalId());
        if (exists(criteria)) {
            String message = String.format("Customer with national id [%s] already exists", serviceRequest.getNationalId());
            throw CoreServiceException.builder()
                    .message(message)
                    .error(new ErrorDTO(ErrorCodeEnum.DuplicateData, message, "CustomerNationalId"))
                    .build();
        }
    }
}
