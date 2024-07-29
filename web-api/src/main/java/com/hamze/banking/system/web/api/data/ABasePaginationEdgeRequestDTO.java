package com.hamze.banking.system.web.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class ABasePaginationEdgeRequestDTO extends ABaseEdgeRequestDTO {

    @JsonProperty("pageSize")
    private Integer pageSize;

    @JsonProperty("offset")
    private Integer offset;

    @JsonProperty("sort")
    private List<SortEdgeDTO> sort;

    @JsonProperty("returnAll")
    private Boolean returnAll = false;

    @JsonProperty("returnTotalSize")
    private Boolean returnTotalSize = false;
}
