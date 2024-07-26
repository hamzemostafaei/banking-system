package com.hamze.banking.system.web.validation;

import com.hamze.banking.system.shared.util.ValidationUtil;
import com.hamze.banking.system.web.api.data.CreateCustomerEdgeRequestDTO;
import com.hamze.banking.system.web.api.data.CreateCustomerEdgeResponseDTO;
import com.hamze.banking.system.web.api.validation.ICreateCustomerEdgeRequestValidator;
import org.springframework.stereotype.Component;

@Component("CreateCustomerEdgeRequestValidator")
public class CreateCustomerEdgeRequestValidator extends ABaseEdgeRequestValidator<CreateCustomerEdgeRequestDTO,
                                                                                  CreateCustomerEdgeResponseDTO>
        implements ICreateCustomerEdgeRequestValidator {

    @Override
    public boolean validate(CreateCustomerEdgeRequestDTO request, CreateCustomerEdgeResponseDTO response) {
        boolean validationResult = super.validate(request, response);
        if (!validationResult) {
            return false;
        }

        handleValidation(ValidationUtil.validateMandatory(request.getFirstName(), "firstName"), response);
        handleValidation(ValidationUtil.validateMandatory(request.getLastName(), "lastName"), response);
        handleValidation(ValidationUtil.validateNationalCode(request.getNationalId(), "nationalId"), response);

        return handleResult(response);
    }
}
