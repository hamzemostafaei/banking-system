package com.hamze.banking.system.core.api.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public abstract class ABaseSearchCriteria implements ISearchCriteria {
    protected Integer offset;
    protected Integer pageSize;
    protected List<SortDTO> sortItems;
    protected Boolean returnTotalSize;
}
