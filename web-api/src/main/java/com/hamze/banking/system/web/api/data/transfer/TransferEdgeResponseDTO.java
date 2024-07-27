package com.hamze.banking.system.web.api.data.transfer;

import com.hamze.banking.system.web.api.data.ABaseEdgeResponseDataDTO;
import com.hamze.banking.system.web.api.data.VoucherEdgeDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TransferEdgeResponseDTO extends ABaseEdgeResponseDataDTO<VoucherEdgeDTO> {
}
