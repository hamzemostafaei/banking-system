package com.hamze.banking.system.core.api.criteria;

import java.util.List;

public interface ISearchCriteria {

    Integer getOffset();

    Integer getPageSize();

    List<SortDTO> getSortItems();

    Boolean getReturnTotalSize();
}
