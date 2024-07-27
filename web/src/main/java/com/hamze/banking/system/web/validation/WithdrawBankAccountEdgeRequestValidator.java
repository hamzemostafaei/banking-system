package com.hamze.banking.system.web.validation;

import com.hamze.banking.system.shared.util.ValidationUtil;
import com.hamze.banking.system.web.api.data.WithdrawBankAccountEdgeRequestDTO;
import com.hamze.banking.system.web.api.data.WithdrawBankAccountEdgeResponseDTO;
import com.hamze.banking.system.web.api.validation.IWithdrawBankAccountEdgeRequestValidator;
import org.springframework.stereotype.Component;

@Component("WithdrawBankAccountEdgeRequestValidator")
public class WithdrawBankAccountEdgeRequestValidator extends ABaseEdgeRequestValidator<WithdrawBankAccountEdgeRequestDTO,
                                                                                       WithdrawBankAccountEdgeResponseDTO>
        implements IWithdrawBankAccountEdgeRequestValidator {

    @Override
    public boolean validate(WithdrawBankAccountEdgeRequestDTO request, WithdrawBankAccountEdgeResponseDTO response) {
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
