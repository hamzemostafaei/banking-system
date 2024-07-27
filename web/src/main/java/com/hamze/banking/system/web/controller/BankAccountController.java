package com.hamze.banking.system.web.controller;

import com.hamze.banking.system.core.api.data.CustomerDTO;
import com.hamze.banking.system.core.api.data.account.AccountDTO;
import com.hamze.banking.system.core.api.data.account.custom.CreditAccountRequestDTO;
import com.hamze.banking.system.core.api.data.account.custom.DebitAccountRequestDTO;
import com.hamze.banking.system.core.api.data.account.custom.VoucherDTO;
import com.hamze.banking.system.core.api.exception.CoreServiceException;
import com.hamze.banking.system.core.api.service.IBankAccountService;
import com.hamze.banking.system.core.api.service.ICustomerService;
import com.hamze.banking.system.shared.data.base.dto.ErrorDTO;
import com.hamze.banking.system.shared.data.base.enumeration.ErrorCodeEnum;
import com.hamze.banking.system.web.api.data.*;
import com.hamze.banking.system.web.api.mapper.IAccountDTOEdgeResponseMapper;
import com.hamze.banking.system.web.api.mapper.ICustomerDTOEdgeResponseMapper;
import com.hamze.banking.system.web.api.validation.ICreateAccountEdgeRequestValidator;
import com.hamze.banking.system.web.api.validation.IDepositBankAccountEdgeRequestValidator;
import com.hamze.banking.system.web.api.validation.IGetAccountDetailsEdgeRequestValidator;
import com.hamze.banking.system.web.api.validation.IWithdrawBankAccountEdgeRequestValidator;
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

    private final IBankAccountService bankAccountService;
    private final ICustomerService customerService;
    private final IAccountDTOEdgeResponseMapper accountDTOEdgeResponseMapper;
    private final IGetAccountDetailsEdgeRequestValidator getAccountDetailsEdgeRequestValidator;
    private final ICreateAccountEdgeRequestValidator createAccountEdgeRequestValidator;
    private final ICustomerDTOEdgeResponseMapper customerDTOEdgeResponseMapper;
    private final IWithdrawBankAccountEdgeRequestValidator withdrawBankAccountEdgeRequestValidator;
    private final IDepositBankAccountEdgeRequestValidator depositBankAccountEdgeRequestValidator;

    @PostMapping(path = "/v1/create")
    public ResponseEntity<CreateAccountEdgeResponseDTO> createAccount(@RequestBody CreateAccountEdgeRequestDTO request) {

        CreateAccountEdgeResponseDTO response = new CreateAccountEdgeResponseDTO();

        boolean validationResult = createAccountEdgeRequestValidator.validate(request, response);
        if (!validationResult) {
            return ResponseEntity
                    .badRequest()
                    .body(response);
        }

        AccountDTO account = new AccountDTO();
        account.setAccountNumber(request.getAccountNumber());
        account.setCustomerNumber(request.getCustomerNumber());
        account.setAccountTitle(request.getAccountTitle());
        account.setCurrency(request.getCurrency());
        account.setOpenAmount(request.getOpenAmount());

        try {
            AccountDTO result = bankAccountService.createAccount(account);
            AccountEdgeDTO responseData = accountDTOEdgeResponseMapper.objectToEdgeObject(result);
            CustomerDTO holder = customerService.findById(request.getCustomerNumber());
            responseData.setHolder(customerDTOEdgeResponseMapper.objectToEdgeObject(holder));
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
    public ResponseEntity<DepositBankAccountEdgeResponseDTO> deposit(@RequestBody DepositBankAccountEdgeRequestDTO request) {
        DepositBankAccountEdgeResponseDTO response = new DepositBankAccountEdgeResponseDTO();

        boolean validationResult = depositBankAccountEdgeRequestValidator.validate(request, response);
        if (!validationResult) {
            return ResponseEntity.badRequest().body(response);
        }

        try {
            CreditAccountRequestDTO creditRequest = new CreditAccountRequestDTO();
            creditRequest.setAccountNumber(request.getAccountNumber());
            creditRequest.setAmount(request.getAmount());
            creditRequest.setDescription(request.getDescription());

            VoucherDTO serviceResponse = bankAccountService.credit(creditRequest);

            VoucherEdgeDTO responseData = new VoucherEdgeDTO();
            responseData.setTurnoverDate(serviceResponse.getTurnoverDate());
            responseData.setTurnoverNumber(serviceResponse.getTurnoverNumber());

            response.setData(responseData);
        } catch (CoreServiceException e) {
            response.setErrors(e.getErrors());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.addError(new ErrorDTO(ErrorCodeEnum.InternalServiceError, "DepositBankAccount"));
            return ResponseEntity.internalServerError().body(response);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/v1/withdraw")
    public ResponseEntity<WithdrawBankAccountEdgeResponseDTO> withdraw(@RequestBody WithdrawBankAccountEdgeRequestDTO request) {
        WithdrawBankAccountEdgeResponseDTO response = new WithdrawBankAccountEdgeResponseDTO();

        boolean validationResult = withdrawBankAccountEdgeRequestValidator.validate(request, response);
        if (!validationResult) {
            return ResponseEntity.badRequest().body(response);
        }

        try {
            DebitAccountRequestDTO debitRequest = new DebitAccountRequestDTO();
            debitRequest.setAccountNumber(request.getAccountNumber());
            debitRequest.setAmount(request.getAmount());
            debitRequest.setDescription(request.getDescription());

            VoucherDTO serviceResponse = bankAccountService.debit(debitRequest);

            VoucherEdgeDTO responseData = new VoucherEdgeDTO();
            responseData.setTurnoverDate(serviceResponse.getTurnoverDate());
            responseData.setTurnoverNumber(serviceResponse.getTurnoverNumber());

            response.setData(responseData);
        } catch (CoreServiceException e) {
            response.setErrors(e.getErrors());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.addError(new ErrorDTO(ErrorCodeEnum.InternalServiceError, "WithdrawBankAccount"));
            return ResponseEntity.internalServerError().body(response);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/v1/transfer")
    public ResponseEntity<Object> transfer(@RequestBody Object request) {
        return ResponseEntity.ok(request);
    }

    @PostMapping(path = "/v1/balance")
    public ResponseEntity<Object> balance(@RequestBody Object request) {
        return ResponseEntity.ok(request);
    }

    @PostMapping(path = "/v1/statement")
    public ResponseEntity<Object> statement(@RequestBody Object request) {
        return ResponseEntity.ok(request);
    }

    @PostMapping(path = "/v1/detail")
    public ResponseEntity<GetAccountDetailsEdgeResponseDTO> detail(@RequestBody GetAccountDetailsEdgeRequestDTO request) {

        GetAccountDetailsEdgeResponseDTO response = new GetAccountDetailsEdgeResponseDTO();
        boolean validationResult = getAccountDetailsEdgeRequestValidator.validate(request, response);

        if (!validationResult) {
            return ResponseEntity
                    .badRequest()
                    .body(response);
        }

        AccountDTO serviceResponse = bankAccountService.findById(request.getAccountNumber());

        if (serviceResponse != null) {
            AccountEdgeDTO responseData = accountDTOEdgeResponseMapper.objectToEdgeObject(serviceResponse);
            CustomerDTO holderCustomer = customerService.findById(serviceResponse.getCustomerNumber());
            CustomerEdgeDTO customerEdge = customerDTOEdgeResponseMapper.objectToEdgeObject(holderCustomer);
            responseData.setHolder(customerEdge);
            response.setData(responseData);
        }

        return ResponseEntity.ok(response);
    }
}
