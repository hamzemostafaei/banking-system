package com.hamze.banking.system.web.validation;

import com.hamze.banking.system.shared.util.ValidationUtil;
import com.hamze.banking.system.web.api.data.DepositBankAccountEdgeRequestDTO;
import com.hamze.banking.system.web.api.data.DepositBankAccountEdgeResponseDTO;
import com.hamze.banking.system.web.api.validation.IDepositBankAccountEdgeRequestValidator;
import org.springframework.stereotype.Component;

@Component("DepositBankAccountEdgeRequestValidator")
public class DepositBankAccountEdgeRequestValidator extends ABaseEdgeRequestValidator<DepositBankAccountEdgeRequestDTO, DepositBankAccountEdgeResponseDTO>
        implements IDepositBankAccountEdgeRequestValidator {

    @Override
    public boolean validate(DepositBankAccountEdgeRequestDTO request, DepositBankAccountEdgeResponseDTO response) {
        boolean validationResult = super.validate(request, response);
        if (!validationResult) {
            return false;
        }

        handleValidation(ValidationUtil.validateMandatory(request.getDescription(),"description"),response);
        handleValidation(ValidationUtil.validateAccountNumber(request.getAccountNumber()),response);
        handleValidation(ValidationUtil.validateAmount(request.getAmount()),response);

        return handleResult(response);
    }
}
