package com.hamze.banking.system.web.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hamze.banking.system.shared.data.base.enumeration.SortDirectionEnum;
import lombok.Data;

@Data
public class SortEdgeDTO {

    @JsonProperty("sortBy")
    private String sortBy;

    @JsonProperty("sortDirection")
    private SortDirectionEnum sortDirection = SortDirectionEnum.Asc;

}