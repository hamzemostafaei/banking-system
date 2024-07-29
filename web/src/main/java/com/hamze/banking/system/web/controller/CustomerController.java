package com.hamze.banking.system.web.controller;

import com.hamze.banking.system.core.api.criteria.CustomerCriteria;
import com.hamze.banking.system.core.api.data.CustomerDTO;
import com.hamze.banking.system.core.api.exception.CoreServiceException;
import com.hamze.banking.system.core.api.service.ICustomerService;
import com.hamze.banking.system.shared.data.base.dto.ErrorDTO;
import com.hamze.banking.system.shared.data.base.enumeration.ErrorCodeEnum;
import com.hamze.banking.system.shared.data.base.enumeration.ServiceStatusEnum;
import com.hamze.banking.system.web.api.data.CreateCustomerEdgeRequestDTO;
import com.hamze.banking.system.web.api.data.CreateCustomerEdgeResponseDTO;
import com.hamze.banking.system.web.api.data.CustomerEdgeDTO;
import com.hamze.banking.system.web.api.data.GetCustomerEdgeResponseDTO;
import com.hamze.banking.system.web.api.mapper.ICustomerDTOEdgeResponseMapper;
import com.hamze.banking.system.web.api.validation.ICreateCustomerEdgeRequestValidator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
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
    public ResponseEntity<GetCustomerEdgeResponseDTO> getCustomer(@PathVariable("customerNumber") String customerNumber, HttpServletRequest request) {

        GetCustomerEdgeResponseDTO response = new GetCustomerEdgeResponseDTO();

        //TODO
        String trackingNumber = (String) request.getAttribute("trackingNumber");
        String transactionId = (String) request.getAttribute("transactionId");
        Date registrationDate = (Date) request.getAttribute("registrationDate");
        response.setRegistrationDate(registrationDate);
        response.setTrackingNumber(trackingNumber);
        response.setTransactionId(transactionId);
        response.setStatus(ServiceStatusEnum.Successful);

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
