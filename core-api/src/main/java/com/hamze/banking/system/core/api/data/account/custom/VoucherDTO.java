package com.hamze.banking.system.core.api.data.account.custom;

import lombok.Data;

import java.util.Date;

@Data
public class VoucherDTO {
    private Long turnoverNumber;
    private Date turnoverDate;
}
