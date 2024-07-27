package com.hamze.banking.system.core.service;

import com.hamze.banking.system.core.api.criteria.AccountTurnoverCriteria;
import com.hamze.banking.system.core.api.data.account.AccountDTO;
import com.hamze.banking.system.core.api.data.account.AccountTurnoverDTO;
import com.hamze.banking.system.core.api.data.account.custom.CreditAccountRequestDTO;
import com.hamze.banking.system.core.api.data.account.custom.DebitAccountRequestDTO;
import com.hamze.banking.system.core.api.data.account.custom.VoucherDTO;
import com.hamze.banking.system.core.api.exception.CoreServiceException;
import com.hamze.banking.system.core.api.service.IAccountFinancialService;
import com.hamze.banking.system.core.api.service.IBankAccountService;
import com.hamze.banking.system.data.access.entity.AccountTurnoverEntity;
import com.hamze.banking.system.data.access.repository.api.IAccountTurnoverRepository;
import com.hamze.banking.system.shared.data.base.dto.ErrorDTO;
import com.hamze.banking.system.shared.data.base.enumeration.ErrorCodeEnum;
import com.hamze.banking.system.shared.util.SnowFlakeUniqueIDGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service("AccountFinancialService")
public class AccountFinancialServiceImpl extends ABaseCoreService<AccountTurnoverDTO,
                                                                  AccountTurnoverEntity,
                                                                  Long,
                                                                  AccountTurnoverCriteria,
                                                                  IAccountTurnoverRepository>
        implements IAccountFinancialService {

    @Value("${com.hamze.banking.system.node-id}")
    private Long nodeId;

    @Override
    public VoucherDTO credit(CreditAccountRequestDTO request,IBankAccountService bankAccountService) {

        AccountDTO account = getAndValidateAccount(request.getAccountNumber(), bankAccountService);

        AccountTurnoverDTO creditTurnover = createTurnover(
                request.getDescription(),
                request.getAmount(),
                account.getAccountNumber(),
                "D1001"
        );

        BigDecimal newBalance = calculateBalance(account, request.getAmount());
        creditTurnover.setEntryBalance(newBalance);
        account.setBalance(newBalance);

        saveTurnoverAndAccount(creditTurnover, account, bankAccountService);
        return createVoucherResponse(creditTurnover);
    }

    @Override
    public VoucherDTO debit(DebitAccountRequestDTO request,IBankAccountService bankAccountService) {

        AccountDTO account = getAndValidateAccount(request.getAccountNumber(),bankAccountService);

        BigDecimal debitAmount = request.getAmount().negate();
        AccountTurnoverDTO debitTurnover = createTurnover(
                request.getDescription(),
                debitAmount,
                account.getAccountNumber(),
                "D1002"
        );

        BigDecimal newBalance = calculateBalance(account, debitAmount);
        debitTurnover.setEntryBalance(newBalance);
        account.setBalance(newBalance);

        saveTurnoverAndAccount(debitTurnover, account, bankAccountService);
        return createVoucherResponse(debitTurnover);
    }

    private AccountTurnoverDTO createTurnover(String description, BigDecimal amount, String accountNumber, String transactionType) {
        AccountTurnoverDTO turnover = new AccountTurnoverDTO();
        turnover.setTurnoverId(SnowFlakeUniqueIDGenerator.generateNextId(nodeId));
        turnover.setTurnoverNumber(SnowFlakeUniqueIDGenerator.generateNextId(nodeId));
        turnover.setTurnoverDate(new Date());
        turnover.setEntryNumber(1);
        turnover.setDescription(description);
        turnover.setTransactionType(transactionType);
        turnover.setAmount(amount);
        turnover.setAccountNumber(accountNumber);
        return turnover;
    }

    private void saveTurnoverAndAccount(AccountTurnoverDTO turnover, AccountDTO account,IBankAccountService bankAccountService) {
        save(turnover);
        bankAccountService.save(account);
    }

    private VoucherDTO createVoucherResponse(AccountTurnoverDTO turnover) {
        VoucherDTO response = new VoucherDTO();
        response.setTurnoverDate(turnover.getTurnoverDate());
        response.setTurnoverNumber(turnover.getTurnoverNumber());
        return response;
    }

    private AccountDTO getAndValidateAccount(String accountNumber,IBankAccountService bankAccountService) {
        AccountDTO account = bankAccountService.findById(accountNumber);
        if (account == null) {
            String message = String.format("Account [%s] not found", accountNumber);
            throw CoreServiceException.builder()
                    .message(message)
                    .error(new ErrorDTO(ErrorCodeEnum.DataNotFound, message, "AccountNumber"))
                    .build();
        }
        return account;
    }

    private BigDecimal calculateBalance(AccountDTO account, BigDecimal amount) {
        BigDecimal currentBalance = account.getBalance() != null ? account.getBalance() : BigDecimal.ZERO;
        BigDecimal newBalance = currentBalance.add(amount);

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            String message = String.format("Account [%s] does not have enough balance", account.getAccountNumber());
            throw CoreServiceException.builder()
                    .message(message)
                    .error(new ErrorDTO(ErrorCodeEnum.OutOfBoundsData, message, "AccountBalance"))
                    .build();
        }
        return newBalance;
    }
}