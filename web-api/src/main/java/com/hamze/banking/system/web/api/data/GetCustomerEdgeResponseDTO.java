package com.hamze.banking.system.web.api.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GetCustomerEdgeResponseDTO extends ABaseEdgeResponseDataDTO<CustomerEdgeDTO> {
}
