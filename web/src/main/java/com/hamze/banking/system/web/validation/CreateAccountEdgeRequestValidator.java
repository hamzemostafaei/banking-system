package com.hamze.banking.system.web.validation;

import com.hamze.banking.system.shared.data.base.dto.ErrorDTO;
import com.hamze.banking.system.shared.data.base.enumeration.ErrorCodeEnum;
import com.hamze.banking.system.shared.util.ValidationUtil;
import com.hamze.banking.system.web.api.data.CreateAccountEdgeRequestDTO;
import com.hamze.banking.system.web.api.data.CreateAccountEdgeResponseDTO;
import com.hamze.banking.system.web.api.validation.ICreateAccountEdgeRequestValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("CreateAccountEdgeRequestValidator")
public class CreateAccountEdgeRequestValidator extends ABaseEdgeRequestValidator<CreateAccountEdgeRequestDTO,
                                                                                 CreateAccountEdgeResponseDTO>
        implements ICreateAccountEdgeRequestValidator {

    @Override
    public boolean validate(CreateAccountEdgeRequestDTO request, CreateAccountEdgeResponseDTO response) {
        boolean validationResult = super.validate(request, response);
        if (!validationResult) {
            return false;
        }

        handleValidation(ValidationUtil.validateMandatory(request.getCustomerNumber(),"customerNumber"),response);
        handleValidation(ValidationUtil.validateMandatory(request.getAccountTitle(),"accountTitle"),response);
        handleValidation(ValidationUtil.validateAccountNumber(request.getAccountNumber()),response);
        validateCurrency(request,response);

        return handleResult(response);
    }

    private void validateCurrency(CreateAccountEdgeRequestDTO request, CreateAccountEdgeResponseDTO response) {
        if (StringUtils.isBlank(request.getCurrency())) {
            request.setCurrency("IRR");
        } else if (!Objects.equals("IRR", request.getCurrency())) {
            response.addError(new ErrorDTO(ErrorCodeEnum.NotAuthorizedForData, "Currency"));
        }
    }
}
