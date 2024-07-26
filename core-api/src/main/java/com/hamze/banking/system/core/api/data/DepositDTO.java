package com.hamze.banking.system.core.api.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class DepositDTO extends ABaseDTO {

    private Long depositId;
    private String depositNumber;
    private Integer customerNumber;
    private String depositTitle;
    private String currency;
    private Date openDate;
    private Date closeDate;
    private Integer status;
    private BigDecimal openAmount;

}
