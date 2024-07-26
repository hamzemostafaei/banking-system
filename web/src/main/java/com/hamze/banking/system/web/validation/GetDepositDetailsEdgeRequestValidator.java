package com.hamze.banking.system.web.validation;

import com.hamze.banking.system.shared.util.ValidationUtil;
import com.hamze.banking.system.web.api.data.GetDepositDetailsEdgeRequestDTO;
import com.hamze.banking.system.web.api.data.GetDepositDetailsEdgeResponseDTO;
import com.hamze.banking.system.web.api.validation.IGetDepositDetailsEdgeRequestValidator;
import org.springframework.stereotype.Component;

@Component("GetDepositDetailsEdgeRequestValidator")
public class GetDepositDetailsEdgeRequestValidator extends ABaseEdgeRequestValidator<GetDepositDetailsEdgeRequestDTO,
                                                                                     GetDepositDetailsEdgeResponseDTO>
        implements IGetDepositDetailsEdgeRequestValidator {

    @Override
    public boolean validate(GetDepositDetailsEdgeRequestDTO request, GetDepositDetailsEdgeResponseDTO response) {
        boolean validationResult = super.validate(request, response);
        if(!validationResult) {
            return false;
        }

        handleValidation(ValidationUtil.validateDepositNumber(request.getDepositNumber()),response);

        return handleResult(response);
    }
}
