package com.hamze.banking.system.web.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class VoucherEdgeDTO {

    @JsonProperty("turnoverNumber")
    private Long turnoverNumber;

    @JsonProperty("turnoverDate")
    private Date turnoverDate;
}
