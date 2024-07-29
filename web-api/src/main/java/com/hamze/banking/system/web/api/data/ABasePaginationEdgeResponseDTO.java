package com.hamze.banking.system.web.api.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public abstract class ABasePaginationEdgeResponseDTO {

    private Boolean hasNext;

    @JsonProperty("recordCount")
    private Long recordCount;

    @JsonIgnore
    private Integer offset;

    @JsonIgnore
    private Integer pageSize;

    @JsonProperty("hasNext")
    public boolean getHasNext() {
        if (recordCount == null) {
            return false;
        }

        if (this.offset != null && this.pageSize != null) {
            return this.recordCount > (long) (this.offset + 1) * this.pageSize;
        }

        return false;

    }

    public void setRecordCount(Long recordCount) {
        if (recordCount < 0) {
            this.recordCount = 0L;
        } else {
            this.recordCount = recordCount;
        }

    }
}
