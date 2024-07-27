package com.hamze.banking.system.web.validation;

import com.hamze.banking.system.shared.util.ValidationUtil;
import com.hamze.banking.system.web.api.data.GetAccountDetailsEdgeRequestDTO;
import com.hamze.banking.system.web.api.data.GetAccountDetailsEdgeResponseDTO;
import com.hamze.banking.system.web.api.validation.IGetAccountDetailsEdgeRequestValidator;
import org.springframework.stereotype.Component;

@Component("GetAccountDetailsEdgeRequestValidator")
public class GetAccountDetailsEdgeRequestValidator extends ABaseEdgeRequestValidator<GetAccountDetailsEdgeRequestDTO,
                                                                                     GetAccountDetailsEdgeResponseDTO>
        implements IGetAccountDetailsEdgeRequestValidator {

    @Override
    public boolean validate(GetAccountDetailsEdgeRequestDTO request, GetAccountDetailsEdgeResponseDTO response) {
        boolean validationResult = super.validate(request, response);
        if(!validationResult) {
            return false;
        }

        handleValidation(ValidationUtil.validateAccountNumber(request.getAccountNumber()),response);

        return handleResult(response);
    }
}
