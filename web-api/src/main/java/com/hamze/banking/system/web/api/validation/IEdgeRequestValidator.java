package com.hamze.banking.system.web.api.validation;

import com.hamze.banking.system.web.api.data.ABaseEdgeRequestDTO;
import com.hamze.banking.system.web.api.data.ABaseEdgeResponseDTO;

public interface IEdgeRequestValidator<RQ extends ABaseEdgeRequestDTO, RP extends ABaseEdgeResponseDTO> {

    boolean validate(RQ request, RP response);
}
