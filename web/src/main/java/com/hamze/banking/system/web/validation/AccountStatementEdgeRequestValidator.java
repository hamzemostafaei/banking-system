package com.hamze.banking.system.web.validation;

import com.hamze.banking.system.shared.util.ValidationUtil;
import com.hamze.banking.system.web.api.data.account.statement.AccountStatementEdgeRequestDTO;
import com.hamze.banking.system.web.api.data.account.statement.AccountStatementEdgeResponseDTO;
import com.hamze.banking.system.web.api.validation.IAccountStatementEdgeRequestValidator;
import org.springframework.stereotype.Component;

@Component("AccountStatementEdgeRequestValidator")
public class AccountStatementEdgeRequestValidator extends ABaseEdgeRequestValidator<AccountStatementEdgeRequestDTO,
                                                                                    AccountStatementEdgeResponseDTO>
        implements IAccountStatementEdgeRequestValidator {
    @Override
    public boolean validate(AccountStatementEdgeRequestDTO request, AccountStatementEdgeResponseDTO response) {
        boolean validationResult = super.validate(request, response);
        if (!validationResult) {
            return false;
        }

        handleValidation(ValidationUtil.validateAccountNumber(request.getAccountNumber()),response);

        return handleResult(response);
    }
}
