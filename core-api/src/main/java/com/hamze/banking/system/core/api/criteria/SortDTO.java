package com.hamze.banking.system.core.api.criteria;

import com.hamze.banking.system.shared.data.base.enumeration.SortDirectionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SortDTO {

    private String sortBy;

    private SortDirectionEnum sortDirection = SortDirectionEnum.Asc;
}
