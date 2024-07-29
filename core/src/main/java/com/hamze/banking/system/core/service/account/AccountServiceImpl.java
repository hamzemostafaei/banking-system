package com.hamze.banking.system.core.service.account;

import com.hamze.banking.system.core.api.criteria.AccountCriteria;
import com.hamze.banking.system.core.api.data.account.AccountDTO;
import com.hamze.banking.system.core.api.data.account.custom.ABaseTransactionRequestDTO;
import com.hamze.banking.system.core.api.data.account.custom.TransactionRequestDTO;
import com.hamze.banking.system.core.api.data.account.custom.TransactionTypeEnum;
import com.hamze.banking.system.core.api.data.account.custom.VoucherDTO;
import com.hamze.banking.system.core.api.exception.CoreServiceException;
import com.hamze.banking.system.core.api.logging.ITransactionObserver;
import com.hamze.banking.system.core.api.service.IAccountService;
import com.hamze.banking.system.core.api.service.ICustomerService;
import com.hamze.banking.system.core.api.service.ITransactionStrategy;
import com.hamze.banking.system.core.service.ABaseCoreService;
import com.hamze.banking.system.core.service.account.strategy.TransactionStrategyRegistry;
import com.hamze.banking.system.data.access.entity.AccountEntity;
import com.hamze.banking.system.data.access.enumeration.AccountStatusEnum;
import com.hamze.banking.system.data.access.repository.api.IAccountRepository;
import com.hamze.banking.system.shared.data.base.dto.ErrorDTO;
import com.hamze.banking.system.shared.data.base.enumeration.ErrorCodeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
    private final List<ITransactionObserver> observers;

    @Override
    public AccountDTO create(AccountDTO request) {

        checkCustomerExistence(request);
        checkAccountExistence(request);

        request.setOpenDate(new Date());
        request.setCloseDate(null);
        request.setStatus(AccountStatusEnum.Active);
        request.setOpenAmount(request.getOpenAmount());

        AccountDTO savedAccount = save(request);

        if (request.getOpenAmount() != null && request.getOpenAmount().compareTo(BigDecimal.ZERO) > 0) {
            TransactionRequestDTO initialCreditRequest = new TransactionRequestDTO();
            initialCreditRequest.setDescription("افتتاح سپرده");
            initialCreditRequest.setAccountNumber(savedAccount.getAccountNumber());
            initialCreditRequest.setAmount(request.getOpenAmount());
            doTransaction(TransactionTypeEnum.Open, initialCreditRequest);
        }

        return savedAccount;
    }

    @Override
    public VoucherDTO doTransaction(TransactionTypeEnum transactionType, ABaseTransactionRequestDTO request) {
        ITransactionStrategy<ABaseTransactionRequestDTO> strategy = TransactionStrategyRegistry.getStrategy(transactionType);
        if (strategy == null) {
            throw new UnsupportedOperationException(String.format("Transaction type not supported: %s", transactionType));
        }
        VoucherDTO response = strategy.doTransaction(request);

        // Notify observers after processing the transaction
        notifyObservers(request, transactionType, response);

        return response;
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

    private void notifyObservers(ABaseTransactionRequestDTO request, TransactionTypeEnum transactionType, VoucherDTO voucherResponse) {
        for (ITransactionObserver observer : observers) {
            observer.onTransaction(request, transactionType, voucherResponse);
        }
    }
}
