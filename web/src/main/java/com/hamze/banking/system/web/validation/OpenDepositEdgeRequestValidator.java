package com.hamze.banking.system.web.validation;

import com.hamze.banking.system.shared.data.base.dto.ErrorDTO;
import com.hamze.banking.system.shared.data.base.enumeration.ErrorCodeEnum;
import com.hamze.banking.system.shared.util.ValidationUtil;
import com.hamze.banking.system.web.api.data.OpenDepositEdgeRequestDTO;
import com.hamze.banking.system.web.api.data.OpenDepositEdgeResponseDTO;
import com.hamze.banking.system.web.api.validation.IOpenDepositEdgeRequestValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("OpenDepositEdgeRequestValidator")
public class OpenDepositEdgeRequestValidator extends ABaseEdgeRequestValidator<OpenDepositEdgeRequestDTO, OpenDepositEdgeResponseDTO>
        implements IOpenDepositEdgeRequestValidator {

    @Override
    public boolean validate(OpenDepositEdgeRequestDTO request, OpenDepositEdgeResponseDTO response) {
        boolean validationResult = super.validate(request, response);
        if (!validationResult) {
            return false;
        }

        handleValidation(ValidationUtil.validateMandatory(request.getCustomerNumber(),"customerNumber"),response);
        handleValidation(ValidationUtil.validateMandatory(request.getDepositTitle(),"depositTitle"),response);
        handleValidation(ValidationUtil.validateDepositNumber(request.getDepositNumber()),response);
        validateCurrency(request,response);

        return handleResult(response);
    }

    private void validateCurrency(OpenDepositEdgeRequestDTO request, OpenDepositEdgeResponseDTO response) {
        if (StringUtils.isBlank(request.getCurrency())) {
            request.setCurrency("IRR");
        } else if (!Objects.equals("IRR", request.getCurrency())) {
            response.addError(new ErrorDTO(ErrorCodeEnum.NotAuthorizedForData, "Currency"));
        }
    }
}
