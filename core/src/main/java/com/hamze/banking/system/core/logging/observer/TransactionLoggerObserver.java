package com.hamze.banking.system.core.logging.observer;

import com.hamze.banking.system.core.api.data.account.custom.VoucherDTO;
import com.hamze.banking.system.core.api.logging.ITransactionObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TransactionLoggerObserver implements ITransactionObserver {

    private final Logger logger = LoggerFactory.getLogger("com.hamze.banking.system.transactions.log");

    @Override
    public void onTransaction(String accountNumber, String transactionType, double amount, VoucherDTO voucherResponse) {
        if (logger.isInfoEnabled()) {
            logger.info("accountNumber: {}, transactionType: {}, amount: {}, voucherResponse:{}", accountNumber, transactionType, amount, voucherResponse);
        }
    }
}
