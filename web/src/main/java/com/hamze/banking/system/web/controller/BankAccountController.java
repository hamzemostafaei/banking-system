package com.hamze.banking.system.web.controller;

import com.hamze.banking.system.core.api.criteria.AccountTurnoverCriteria;
import com.hamze.banking.system.core.api.criteria.SortDTO;
import com.hamze.banking.system.core.api.data.CustomerDTO;
import com.hamze.banking.system.core.api.data.account.AccountDTO;
import com.hamze.banking.system.core.api.data.account.AccountTurnoverDTO;
import com.hamze.banking.system.core.api.data.account.custom.TransactionRequestDTO;
import com.hamze.banking.system.core.api.data.account.custom.TransactionTypeEnum;
import com.hamze.banking.system.core.api.data.account.custom.TransferRequestDTO;
import com.hamze.banking.system.core.api.data.account.custom.VoucherDTO;
import com.hamze.banking.system.core.api.exception.BadSortColumnNameException;
import com.hamze.banking.system.core.api.exception.CoreServiceException;
import com.hamze.banking.system.core.api.service.IAccountService;
import com.hamze.banking.system.core.api.service.IAccountTurnoverService;
import com.hamze.banking.system.core.api.service.ICustomerService;
import com.hamze.banking.system.shared.data.base.dto.ErrorDTO;
import com.hamze.banking.system.shared.data.base.enumeration.ErrorCodeEnum;
import com.hamze.banking.system.web.api.data.*;
import com.hamze.banking.system.web.api.data.account.statement.AccountStatementEdgeDTO;
import com.hamze.banking.system.web.api.data.account.statement.AccountStatementEdgeRequestDTO;
import com.hamze.banking.system.web.api.data.account.statement.AccountStatementEdgeResponseDTO;
import com.hamze.banking.system.web.api.data.account.statement.AccountTurnoverEdgeDTO;
import com.hamze.banking.system.web.api.data.transfer.TransferEdgeRequestDTO;
import com.hamze.banking.system.web.api.data.transfer.TransferEdgeResponseDTO;
import com.hamze.banking.system.web.api.mapper.IAccountDTOEdgeResponseMapper;
import com.hamze.banking.system.web.api.mapper.ICustomerDTOEdgeResponseMapper;
import com.hamze.banking.system.web.api.validation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/bank-account",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class BankAccountController {

    private final IAccountService accountService;
    private final ICustomerService customerService;
    private final IAccountDTOEdgeResponseMapper accountDTOEdgeResponseMapper;
    private final IGetAccountDetailsEdgeRequestValidator getAccountDetailsEdgeRequestValidator;
    private final ICreateAccountEdgeRequestValidator createAccountEdgeRequestValidator;
    private final ICustomerDTOEdgeResponseMapper customerDTOEdgeResponseMapper;
    private final IWithdrawBankAccountEdgeRequestValidator withdrawBankAccountEdgeRequestValidator;
    private final IDepositBankAccountEdgeRequestValidator depositBankAccountEdgeRequestValidator;
    private final ITransferEdgeRequestValidator transferEdgeRequestValidator;
    private final IAccountTurnoverService accountTurnoverService;
    private final IAccountStatementEdgeRequestValidator accountStatementEdgeRequestValidator;

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
            AccountDTO result = accountService.create(account);
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
            log.error(e.getMessage(), e);
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
            TransactionRequestDTO creditRequest = new TransactionRequestDTO();
            creditRequest.setAccountNumber(request.getAccountNumber());
            creditRequest.setAmount(request.getAmount());
            creditRequest.setDescription(request.getDescription());

            VoucherDTO serviceResponse = accountService.doTransaction(TransactionTypeEnum.Deposit, creditRequest);

            VoucherEdgeDTO responseData = new VoucherEdgeDTO();
            responseData.setTurnoverDate(serviceResponse.getTurnoverDate());
            responseData.setTurnoverNumber(serviceResponse.getTurnoverNumber());

            response.setData(responseData);
        } catch (CoreServiceException e) {
            response.setErrors(e.getErrors());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
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
            TransactionRequestDTO debitRequest = new TransactionRequestDTO();
            debitRequest.setAccountNumber(request.getAccountNumber());
            debitRequest.setAmount(request.getAmount());
            debitRequest.setDescription(request.getDescription());

            VoucherDTO serviceResponse = accountService.doTransaction(TransactionTypeEnum.Withdraw, debitRequest);

            VoucherEdgeDTO responseData = new VoucherEdgeDTO();
            responseData.setTurnoverDate(serviceResponse.getTurnoverDate());
            responseData.setTurnoverNumber(serviceResponse.getTurnoverNumber());

            response.setData(responseData);
        } catch (CoreServiceException e) {
            response.setErrors(e.getErrors());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.addError(new ErrorDTO(ErrorCodeEnum.InternalServiceError, "WithdrawBankAccount"));
            return ResponseEntity.internalServerError().body(response);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/v1/transfer")
    public ResponseEntity<TransferEdgeResponseDTO> transfer(@RequestBody TransferEdgeRequestDTO request) {
        TransferEdgeResponseDTO response = new TransferEdgeResponseDTO();
        boolean validationResult = transferEdgeRequestValidator.validate(request, response);
        if (!validationResult) {
            return ResponseEntity.badRequest().body(response);
        }

        try {
            TransferRequestDTO transferRequest = getTransferRequestDTO(request);

            VoucherDTO serviceResponse = accountService.doTransaction(TransactionTypeEnum.Transfer, transferRequest);
            VoucherEdgeDTO responseData = new VoucherEdgeDTO();
            responseData.setTurnoverDate(serviceResponse.getTurnoverDate());
            responseData.setTurnoverNumber(serviceResponse.getTurnoverNumber());
            response.setData(responseData);

        } catch (CoreServiceException e) {
            response.setErrors(e.getErrors());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.addError(new ErrorDTO(ErrorCodeEnum.InternalServiceError, "Transfer"));
            return ResponseEntity.internalServerError().body(response);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/v1/balance")
    public ResponseEntity<Object> balance(@RequestBody Object request) {
        return ResponseEntity.ok(request);
    }

    @PostMapping(path = "/v1/statement")
    public ResponseEntity<AccountStatementEdgeResponseDTO> statement(@RequestBody AccountStatementEdgeRequestDTO request) {

        AccountStatementEdgeResponseDTO response = new AccountStatementEdgeResponseDTO();

        boolean validationResult = accountStatementEdgeRequestValidator.validate(request, response);
        if (!validationResult) {
            return ResponseEntity.badRequest().body(response);
        }

        try {
            AccountStatementEdgeDTO accountStatement = new AccountStatementEdgeDTO();

            AccountDTO accountInfo = accountService.findById(request.getAccountNumber());
            if (accountInfo == null) {
                response.addError(new ErrorDTO(ErrorCodeEnum.DataNotFound, "AccountNumber"));
                return ResponseEntity.badRequest().body(response);
            }
            accountStatement.setAccountNumber(accountInfo.getAccountNumber());
            accountStatement.setAccountTitle(accountInfo.getAccountTitle());
            accountStatement.setStatus(accountInfo.getStatus());
            accountStatement.setOpenDate(accountInfo.getOpenDate());
            accountStatement.setCloseDate(accountInfo.getCloseDate());
            accountStatement.setBalance(accountInfo.getBalance());

            AccountTurnoverCriteria accountTurnoverCriteria = new AccountTurnoverCriteria();
            accountTurnoverCriteria.setAccountNumberEquals(request.getAccountNumber());
            accountTurnoverCriteria.setPageSize(request.getPageSize());
            accountTurnoverCriteria.setOffset(request.getOffset());

            if (!CollectionUtils.isEmpty(request.getSort())) {
                accountTurnoverCriteria.setSortItems(
                        request.getSort()
                                .stream()
                                .map(
                                        (edgeSortItem) -> new SortDTO(edgeSortItem.getSortBy(), edgeSortItem.getSortDirection())
                                )
                                .toList()
                );
            }
            accountTurnoverCriteria.setReturnTotalSize(request.getReturnTotalSize());

            Page<AccountTurnoverDTO> serviceResponse = accountTurnoverService.search(accountTurnoverCriteria);
            if (serviceResponse.hasContent()) {
                List<AccountTurnoverEdgeDTO> accountTurnovers =
                        serviceResponse.getContent().stream()
                                .map((turnover) -> {
                                    AccountTurnoverEdgeDTO edgeTurnover = new AccountTurnoverEdgeDTO();
                                    edgeTurnover.setTurnoverDate(turnover.getTurnoverId().getTurnoverDate());
                                    edgeTurnover.setTurnoverNumber(turnover.getTurnoverId().getTurnoverNumber());
                                    edgeTurnover.setEntryNumber(turnover.getTurnoverId().getEntryNumber());
                                    edgeTurnover.setDescription(turnover.getDescription());
                                    edgeTurnover.setTransactionType(turnover.getTransactionType());
                                    edgeTurnover.setDebit(turnover.getAmount().compareTo(BigDecimal.ZERO) < 0 ? turnover.getAmount() : BigDecimal.ZERO);
                                    edgeTurnover.setCredit(turnover.getAmount().compareTo(BigDecimal.ZERO) > 0 ? turnover.getAmount() : BigDecimal.ZERO);
                                    edgeTurnover.setBalance(turnover.getAmount());
                                    return edgeTurnover;
                                })
                                .toList();

                accountStatement.setTurnovers(accountTurnovers);

                accountStatement.setPageSize(request.getPageSize());
                accountStatement.setOffset(request.getOffset());
                accountStatement.setRecordCount(serviceResponse.getTotalElements());
            }

            response.setData(accountStatement);

        } catch (BadSortColumnNameException e) {
            response.addError(new ErrorDTO(ErrorCodeEnum.InconsistentData, "BadSortColumn"));
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.addError(new ErrorDTO(ErrorCodeEnum.InternalServiceError, "AccountStatement"));
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
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

        AccountDTO serviceResponse = accountService.findById(request.getAccountNumber());

        if (serviceResponse != null) {
            AccountEdgeDTO responseData = accountDTOEdgeResponseMapper.objectToEdgeObject(serviceResponse);
            CustomerDTO holderCustomer = customerService.findById(serviceResponse.getCustomerNumber());
            CustomerEdgeDTO customerEdge = customerDTOEdgeResponseMapper.objectToEdgeObject(holderCustomer);
            responseData.setHolder(customerEdge);
            response.setData(responseData);
        }

        return ResponseEntity.ok(response);
    }

    private TransferRequestDTO getTransferRequestDTO(TransferEdgeRequestDTO request) {
        TransactionRequestDTO source = new TransactionRequestDTO();
        source.setAccountNumber(request.getSource().getAccountNumber());
        source.setDescription(request.getSource().getDescription());
        source.setAmount(request.getSource().getAmount());

        TransactionRequestDTO destination = new TransactionRequestDTO();
        destination.setAccountNumber(request.getDestination().getAccountNumber());
        destination.setDescription(request.getDestination().getDescription());
        destination.setAmount(request.getDestination().getAmount());

        TransferRequestDTO transferRequest = new TransferRequestDTO();
        transferRequest.setSource(source);
        transferRequest.setDestination(destination);
        return transferRequest;
    }
}
