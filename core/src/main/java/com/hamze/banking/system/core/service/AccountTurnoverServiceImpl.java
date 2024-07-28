package com.hamze.banking.system.core.service;

import com.hamze.banking.system.core.api.criteria.AccountTurnoverCriteria;
import com.hamze.banking.system.core.api.data.account.AccountDTO;
import com.hamze.banking.system.core.api.data.account.AccountTurnoverDTO;
import com.hamze.banking.system.core.api.data.account.custom.TransactionRequestDTO;
import com.hamze.banking.system.core.api.data.account.custom.TransferRequestDTO;
import com.hamze.banking.system.core.api.data.account.custom.VoucherDTO;
import com.hamze.banking.system.core.api.exception.CoreServiceException;
import com.hamze.banking.system.core.api.service.IAccountTurnoverService;
import com.hamze.banking.system.core.api.service.IAccountService;
import com.hamze.banking.system.data.access.entity.AccountTurnoverEntity;
import com.hamze.banking.system.data.access.entity.id.AccountTurnoverEntityId;
import com.hamze.banking.system.data.access.repository.api.IAccountTurnoverRepository;
import com.hamze.banking.system.shared.data.base.dto.ErrorDTO;
import com.hamze.banking.system.shared.data.base.enumeration.ErrorCodeEnum;
import com.hamze.banking.system.shared.util.SnowFlakeUniqueIDGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service("AccountTurnoverService")
public class AccountTurnoverServiceImpl extends ABaseCoreService<AccountTurnoverDTO,
                                                                 AccountTurnoverEntity,
                                                                 AccountTurnoverEntityId,
                                                                 AccountTurnoverCriteria,
                                                                 IAccountTurnoverRepository>
        implements IAccountTurnoverService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${com.hamze.banking.system.node-id}")
    private Long nodeId;

    private final IAccountService accountService;

    @Override
    public VoucherDTO credit(TransactionRequestDTO request) {
        return addEntry(request, false, "D1001");
    }

    @Override
    public VoucherDTO debit(TransactionRequestDTO request) {
        return addEntry(request, true, "D1002");
    }

    private VoucherDTO addEntry(TransactionRequestDTO request, boolean isDebit, String transactionType) {
        return addEntry(
                request,
                isDebit,
                transactionType,
                SnowFlakeUniqueIDGenerator.generateNextId(nodeId),
                new Date(),
                1
        );
    }

    public VoucherDTO addEntry(TransactionRequestDTO request,
                               boolean isDebit,
                               String transactionType,
                               Long turnoverNumber,
                               Date turnoverDate,
                               Integer entryNumber) {

        AccountDTO account = getAndValidateAccount(request.getAccountNumber());

        BigDecimal amount = isDebit ? request.getAmount().negate() : request.getAmount();

        AccountTurnoverDTO turnover = createTurnover(
                request.getDescription(),
                amount,
                account.getAccountNumber(),
                transactionType,
                turnoverNumber,
                turnoverDate,
                entryNumber
        );

        BigDecimal initialBal = account.getBalance() != null ? account.getBalance() : BigDecimal.ZERO;
        BigDecimal newBalance = calculateBalance(account, amount);
        turnover.setEntryBalance(newBalance);
        account.setBalance(newBalance);

        saveTurnoverAndAccount(initialBal,turnover, account);
        return createVoucherResponse(turnover);
    }

    @Override
    public VoucherDTO transfer(TransferRequestDTO request) {

        String transactionType = "D1004";
        Long turnoverNumber = SnowFlakeUniqueIDGenerator.generateNextId(nodeId);
        Date turnoverDate = new Date();

        addEntry(request.getSource(), true, transactionType,turnoverNumber,turnoverDate,1);
        addEntry(request.getDestination(), false, transactionType,turnoverNumber,turnoverDate,2);

        VoucherDTO response = new VoucherDTO();
        response.setTurnoverDate(turnoverDate);
        response.setTurnoverNumber(turnoverNumber);

        return response;
    }

    private AccountTurnoverDTO createTurnover(String description,
                                              BigDecimal amount,
                                              String accountNumber,
                                              String transactionType,
                                              Long turnoverNumber,
                                              Date turnoverDate,
                                              Integer entryNumber) {
        AccountTurnoverDTO turnover = new AccountTurnoverDTO();
        AccountTurnoverEntityId turnoverId = new AccountTurnoverEntityId();
        turnoverId.setTurnoverNumber(turnoverNumber);
        turnoverId.setTurnoverDate(turnoverDate);
        turnoverId.setEntryNumber(entryNumber);
        turnover.setTurnoverId(turnoverId);
        turnover.setDescription(description);
        turnover.setTransactionType(transactionType);
        turnover.setAmount(amount);
        turnover.setAccountNumber(accountNumber);
        return turnover;
    }

    private void saveTurnoverAndAccount(BigDecimal initialBal,AccountTurnoverDTO turnover, AccountDTO account) {
        AccountDTO finalAccount = accountService.findById(account.getAccountNumber());
        if (finalAccount.getBalance() != null && finalAccount.getBalance().compareTo(initialBal) != 0) {
            String message = String.format("Account balance of account [%s] modified by another instance", account.getAccountNumber());
            throw CoreServiceException.builder()
                    .message(message)
                    .error(new ErrorDTO(ErrorCodeEnum.InconsistentData, message, "AccountBalance"))
                    .build();
        }
        save(turnover);
        accountService.save(account);
    }

    private VoucherDTO createVoucherResponse(AccountTurnoverDTO turnover) {
        VoucherDTO response = new VoucherDTO();
        response.setTurnoverDate(turnover.getTurnoverId().getTurnoverDate());
        response.setTurnoverNumber(turnover.getTurnoverId().getTurnoverNumber());
        return response;
    }

    private AccountDTO getAndValidateAccount(String accountNumber) {
        AccountDTO account = accountService.findById(accountNumber);
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