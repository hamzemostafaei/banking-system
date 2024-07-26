package com.hamze.banking.system.web.controller;

import com.hamze.banking.system.core.api.criteria.DepositCriteria;
import com.hamze.banking.system.core.api.data.CustomerDTO;
import com.hamze.banking.system.core.api.data.DepositDTO;
import com.hamze.banking.system.core.api.exception.CoreServiceException;
import com.hamze.banking.system.core.api.service.ICustomerService;
import com.hamze.banking.system.core.api.service.IDepositService;
import com.hamze.banking.system.shared.data.base.dto.ErrorDTO;
import com.hamze.banking.system.shared.data.base.enumeration.ErrorCodeEnum;
import com.hamze.banking.system.web.api.data.*;
import com.hamze.banking.system.web.api.mapper.ICustomerDTOEdgeResponseMapper;
import com.hamze.banking.system.web.api.mapper.IDepositDTOEdgeResponseMapper;
import com.hamze.banking.system.web.api.validation.IGetDepositDetailsEdgeRequestValidator;
import com.hamze.banking.system.web.api.validation.IOpenDepositEdgeRequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/bank-account",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class BankAccountController {

    private final IDepositService depositService;
    private final ICustomerService customerService;
    private final IDepositDTOEdgeResponseMapper depositDTOEdgeResponseMapper;
    private final IGetDepositDetailsEdgeRequestValidator getDepositDetailsEdgeRequestValidator;
    private final IOpenDepositEdgeRequestValidator openDepositEdgeRequestValidator;
    private final ICustomerDTOEdgeResponseMapper customerDTOEdgeResponseMapper;

    @PostMapping(path = "/v1/open")
    public ResponseEntity<OpenDepositEdgeResponseDTO> open(@RequestBody OpenDepositEdgeRequestDTO request) {

        OpenDepositEdgeResponseDTO response = new OpenDepositEdgeResponseDTO();

        boolean validationResult = openDepositEdgeRequestValidator.validate(request, response);
        if (!validationResult) {
            return ResponseEntity
                    .badRequest()
                    .body(response);
        }

        DepositDTO deposit = new DepositDTO();
        deposit.setDepositNumber(request.getDepositNumber());
        deposit.setCustomerNumber(request.getCustomerNumber());
        deposit.setDepositTitle(request.getDepositTitle());
        deposit.setCurrency(request.getCurrency());

        DepositDTO result;
        try {
            result = depositService.openDeposit(deposit);
            DepositEdgeDTO responseData = depositDTOEdgeResponseMapper.objectToEdgeObject(result);
            response.setData(responseData);
        } catch (CoreServiceException e) {
            response.setErrors(e.getErrors());
            return ResponseEntity
                    .badRequest()
                    .body(response);
        } catch (Exception e) {
            response.addError(new ErrorDTO(ErrorCodeEnum.InternalServiceError, e.getMessage()));
            return ResponseEntity
                    .internalServerError()
                    .body(response);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/v1/close")
    public ResponseEntity<Object> close(@RequestBody Object request) {
        return ResponseEntity.ok(request);
    }

    @PostMapping(path = "/v1/deposit")
    public ResponseEntity<Object> deposit(@RequestBody Object request) {
        return ResponseEntity.ok(request);
    }

    @PostMapping(path = "/v1/withdraw")
    public ResponseEntity<Object> withdraw(@RequestBody Object request) {
        return ResponseEntity.ok(request);
    }

    @PostMapping(path = "/v1/transfer")
    public ResponseEntity<Object> transfer(@RequestBody Object request) {
        return ResponseEntity.ok(request);
    }

    @PostMapping(path = "/v1/balance")
    public ResponseEntity<Object> balance(@RequestBody Object request) {
        return ResponseEntity.ok(request);
    }

    @PostMapping(path = "/v1/bill")
    public ResponseEntity<Object> turnover(@RequestBody Object request) {
        return ResponseEntity.ok(request);
    }

    @PostMapping(path = "/v1/detail")
    public ResponseEntity<GetDepositDetailsEdgeResponseDTO> detail(@RequestBody GetDepositDetailsEdgeRequestDTO request) {

        GetDepositDetailsEdgeResponseDTO response = new GetDepositDetailsEdgeResponseDTO();
        boolean validationResult = getDepositDetailsEdgeRequestValidator.validate(request, response);

        if (!validationResult) {
            return ResponseEntity
                    .badRequest()
                    .body(response);
        }

        DepositCriteria criteria = new DepositCriteria();
        criteria.setDepositNumberEquals(request.getDepositNumber());

        DepositDTO depositServiceResponse = depositService.getSingleResult(criteria);

        if (depositServiceResponse != null) {
            DepositEdgeDTO responseData = depositDTOEdgeResponseMapper.objectToEdgeObject(depositServiceResponse);
            CustomerDTO holderCustomer = customerService.findById(depositServiceResponse.getCustomerNumber());
            CustomerEdgeDTO customerEdge = customerDTOEdgeResponseMapper.objectToEdgeObject(holderCustomer);
            responseData.setHolder(customerEdge);
            response.setData(responseData);
        }

        return ResponseEntity.ok(response);
    }
}
