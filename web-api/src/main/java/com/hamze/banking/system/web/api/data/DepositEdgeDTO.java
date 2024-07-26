package com.hamze.banking.system.web.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class DepositEdgeDTO {

    @JsonProperty("depositNumber")
    private String depositNumber;

    @JsonProperty("customerNumber")
    private Integer customerNumber;

    @JsonProperty("depositTitle")
    private String depositTitle;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("openDate")
    private Date openDate;

    @JsonProperty("closeDate")
    private Date closeDate;

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("openAmount")
    private BigDecimal openAmount;

    @JsonProperty("holder")
    private CustomerEdgeDTO holder;
}
