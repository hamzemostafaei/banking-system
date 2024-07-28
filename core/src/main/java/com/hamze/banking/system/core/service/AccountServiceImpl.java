package com.hamze.banking.system.core.service;

import com.hamze.banking.system.core.api.criteria.AccountCriteria;
import com.hamze.banking.system.core.api.data.account.AccountDTO;
import com.hamze.banking.system.core.api.exception.CoreServiceException;
import com.hamze.banking.system.core.api.service.IAccountService;
import com.hamze.banking.system.core.api.service.ICustomerService;
import com.hamze.banking.system.data.access.entity.AccountEntity;
import com.hamze.banking.system.data.access.repository.api.IAccountRepository;
import com.hamze.banking.system.shared.data.base.dto.ErrorDTO;
import com.hamze.banking.system.shared.data.base.enumeration.ErrorCodeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service("AccountService")
public class AccountServiceImpl extends ABaseCoreService<AccountDTO,
                                                         AccountEntity,
                                                         String,
                                                         AccountCriteria,
                                                         IAccountRepository>
        implements IAccountService {

    private final ICustomerService customerService;

    @Override
    public AccountDTO create(AccountDTO request) {

        checkCustomerExistence(request);
        checkAccountExistence(request);

        request.setOpenDate(new Date());
        request.setCloseDate(null);
        request.setStatus(0);
        request.setOpenAmount(request.getOpenAmount());

        return save(request);
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
