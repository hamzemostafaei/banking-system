package com.hamze.banking.system.core.api.data.account;

import com.hamze.banking.system.core.api.data.ABaseDTO;
import com.hamze.banking.system.data.access.enumeration.AccountStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class AccountDTO extends ABaseDTO {

    private String accountNumber;
    private Integer customerNumber;
    private String accountTitle;
    private String currency;
    private Date openDate;
    private Date closeDate;
    private AccountStatusEnum status;
    private BigDecimal openAmount;
    private BigDecimal balance;
}
