package com.hamze.banking.system.core.service;

import com.hamze.banking.system.core.api.data.account.AccountDTO;
import com.hamze.banking.system.core.api.data.account.custom.TransactionRequestDTO;
import com.hamze.banking.system.core.api.data.account.custom.TransferRequestDTO;
import com.hamze.banking.system.core.api.data.account.custom.VoucherDTO;
import com.hamze.banking.system.core.api.service.IAccountTurnoverService;
import com.hamze.banking.system.core.api.service.IAccountService;
import com.hamze.banking.system.core.api.service.IBankAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
@Service("BankAccountService")
@Transactional(propagation = Propagation.REQUIRED)
public class BankAccountServiceImpl implements IBankAccountService {

    private final IAccountService accountService;
    private final IAccountTurnoverService accountTurnoverService;

    @Override
    public AccountDTO create(AccountDTO request) {
        AccountDTO account = accountService.create(request);
        if (request.getOpenAmount() != null && request.getOpenAmount().compareTo(BigDecimal.ZERO) > 0) {
            TransactionRequestDTO initialCreditRequest = new TransactionRequestDTO();
            initialCreditRequest.setDescription("افتتاح سپرده");
            initialCreditRequest.setAccountNumber(account.getAccountNumber());
            initialCreditRequest.setAmount(request.getOpenAmount());
            accountTurnoverService.credit(initialCreditRequest);
        }
        return account;
    }

    @Override
    public VoucherDTO credit(TransactionRequestDTO request) {
        return accountTurnoverService.credit(request);
    }

    @Override
    public VoucherDTO debit(TransactionRequestDTO request) {
        return accountTurnoverService.debit(request);
    }

    @Override
    public VoucherDTO transfer(TransferRequestDTO request) {
        return accountTurnoverService.transfer(request);
    }

    @Override
    public AccountDTO details(String accountNumber) {
        return accountService.findById(accountNumber);
    }
}
