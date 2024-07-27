package com.hamze.banking.system.core.service;

import com.hamze.banking.system.core.api.criteria.AccountCriteria;
import com.hamze.banking.system.core.api.data.account.AccountDTO;
import com.hamze.banking.system.core.api.data.account.custom.CreditAccountRequestDTO;
import com.hamze.banking.system.core.api.data.account.custom.DebitAccountRequestDTO;
import com.hamze.banking.system.core.api.data.account.custom.VoucherDTO;
import com.hamze.banking.system.core.api.exception.CoreServiceException;
import com.hamze.banking.system.core.api.service.IAccountFinancialService;
import com.hamze.banking.system.core.api.service.IBankAccountService;
import com.hamze.banking.system.core.api.service.ICustomerService;
import com.hamze.banking.system.data.access.entity.AccountEntity;
import com.hamze.banking.system.data.access.repository.api.IAccountRepository;
import com.hamze.banking.system.shared.data.base.dto.ErrorDTO;
import com.hamze.banking.system.shared.data.base.enumeration.ErrorCodeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service("BankAccountService")
public class BankAccountServiceImpl extends ABaseCoreService<AccountDTO,
                                                             AccountEntity,
                                                             String,
                                                             AccountCriteria,
                                                             IAccountRepository>
        implements IBankAccountService {

    @Value("${com.hamze.banking.system.node-id}")
    private Long nodeId;

    private final ICustomerService customerService;

    private final IAccountFinancialService accountFinancialService;

    @Override
    public AccountDTO createAccount(AccountDTO request) {

        checkCustomerExistence(request);
        checkAccountExistence(request);

        request.setOpenDate(new Date());
        request.setCloseDate(null);
        request.setStatus(0);
        request.setOpenAmount(request.getOpenAmount());

        AccountDTO savedAccount = save(request);

        if (savedAccount.getOpenAmount().compareTo(BigDecimal.ZERO) >= 0) {
            CreditAccountRequestDTO creditRequest = new CreditAccountRequestDTO();
            creditRequest.setDescription("افتتاح سپرده");
            creditRequest.setAmount(savedAccount.getOpenAmount());
            creditRequest.setAccountNumber(savedAccount.getAccountNumber());
            accountFinancialService.credit(creditRequest,this);
        }

        return savedAccount;
    }

    @Override
    public VoucherDTO credit(CreditAccountRequestDTO request) {
        return accountFinancialService.credit(request,this);
    }

    @Override
    public VoucherDTO debit(DebitAccountRequestDTO request) {
        return accountFinancialService.debit(request,this);
    }

    private void checkCustomerExistence(AccountDTO account) {
        if (!customerService.existsById(account.getCustomerNumber())) {
            String message = String.format("Customer [%s] does not exists", account.getCustomerNumber());
            throw CoreServiceException.builder()
                    .message(message)
                    .error(new ErrorDTO(ErrorCodeEnum.DataNotFound, message, "AccountHolder"))
                    .build();
        }
    }

    private void checkAccountExistence(AccountDTO account) {

        if (existsById(account.getAccountNumber())) {
            String message = String.format("Account [%s] already exists", account.getAccountNumber());
            throw CoreServiceException.builder()
                    .message(message)
                    .error(new ErrorDTO(ErrorCodeEnum.DuplicateData, message, "Account"))
                    .build();
        }
    }
}
