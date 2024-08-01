package com.hamze.banking.system.web.controller;

import com.hamze.banking.system.core.api.criteria.CustomerCriteria;
import com.hamze.banking.system.core.api.data.CustomerDTO;
import com.hamze.banking.system.core.api.exception.CoreServiceException;
import com.hamze.banking.system.core.api.service.ICustomerService;
import com.hamze.banking.system.shared.data.base.dto.ErrorDTO;
import com.hamze.banking.system.shared.data.base.enumeration.ErrorCodeEnum;
import com.hamze.banking.system.shared.data.base.enumeration.ServiceStatusEnum;
import com.hamze.banking.system.shared.util.SnowFlakeUniqueIDGenerator;
import com.hamze.banking.system.web.api.data.CreateCustomerEdgeRequestDTO;
import com.hamze.banking.system.web.api.data.CreateCustomerEdgeResponseDTO;
import com.hamze.banking.system.web.api.data.CustomerEdgeDTO;
import com.hamze.banking.system.web.api.data.GetCustomerEdgeResponseDTO;
import com.hamze.banking.system.web.api.mapper.ICustomerDTOEdgeResponseMapper;
import com.hamze.banking.system.web.api.validation.ICreateCustomerEdgeRequestValidator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    @Value("${com.hamze.banking.system.node-id}")
    private Long nodeId;

    private final ICustomerService customerService;
    private final ICreateCustomerEdgeRequestValidator createCustomerEdgeRequestValidator;
    private final ICustomerDTOEdgeResponseMapper customerDTOEdgeResponseMapper;

    @PostMapping(path = "/v1/create")
    public ResponseEntity<CreateCustomerEdgeResponseDTO> createCustomer(@RequestBody CreateCustomerEdgeRequestDTO request) {

        CreateCustomerEdgeResponseDTO response = new CreateCustomerEdgeResponseDTO();

        boolean validationResult = createCustomerEdgeRequestValidator.validate(request, response);
        if (!validationResult) {
            return ResponseEntity.badRequest().body(response);
        }

        try {
            CustomerDTO serviceRequest = new CustomerDTO();
            serviceRequest.setCustomerNumber(request.getCustomerNumber());
            serviceRequest.setFirstName(request.getFirstName());
            serviceRequest.setLastName(request.getLastName());
            serviceRequest.setNationalId(request.getNationalId());

            CustomerDTO serviceResponse = customerService.createCustomer(serviceRequest);

            CustomerEdgeDTO responseData = customerDTOEdgeResponseMapper.objectToEdgeObject(serviceResponse);

            response.setData(responseData);
        } catch (CoreServiceException e) {
            response.setErrors(e.getErrors());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.addError(new ErrorDTO(ErrorCodeEnum.InternalServiceError, "CreateCustomer"));
            return ResponseEntity.internalServerError().body(response);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/v1/{customerNumber}")
    public ResponseEntity<GetCustomerEdgeResponseDTO> getCustomer(@PathVariable("customerNumber") Integer customerNumber, HttpServletRequest request) {

        GetCustomerEdgeResponseDTO response = new GetCustomerEdgeResponseDTO();

        //TODO: should be set by controller interceptor or filter chain
        response.setRegistrationDate(new Date());
        response.setTrackingNumber(Long.toString(SnowFlakeUniqueIDGenerator.generateNextId(nodeId)));
        response.setTransactionId(Long.toString(SnowFlakeUniqueIDGenerator.generateNextId(nodeId)));
        response.setServiceStatus(ServiceStatusEnum.Successful);

        try {
            CustomerCriteria customerCriteria = new CustomerCriteria();
            customerCriteria.setCustomerNumberEquals(customerNumber);

            CustomerDTO serviceResponse = customerService.getSingleResult(customerCriteria);

            response.setData(customerDTOEdgeResponseMapper.objectToEdgeObject(serviceResponse));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.addError(new ErrorDTO(ErrorCodeEnum.InternalServiceError, "CreateCustomer"));
            return ResponseEntity.internalServerError().body(response);
        }

        return ResponseEntity.ok(response);
    }
}
