package com.hamze.banking.system.web.validation;

import com.hamze.banking.system.shared.data.base.dto.ErrorDTO;
import com.hamze.banking.system.shared.data.base.enumeration.ErrorCodeEnum;
import com.hamze.banking.system.shared.util.ValidationUtil;
import com.hamze.banking.system.web.api.data.transfer.TransferEdgeRequestDTO;
import com.hamze.banking.system.web.api.data.transfer.TransferEdgeResponseDTO;
import com.hamze.banking.system.web.api.validation.ITransferEdgeRequestValidator;
import org.springframework.stereotype.Component;

@Component("TransferEdgeRequestValidator")
public class TransferEdgeRequestValidator extends ABaseEdgeRequestValidator<TransferEdgeRequestDTO, TransferEdgeResponseDTO>
        implements ITransferEdgeRequestValidator {

    @Override
    public boolean validate(TransferEdgeRequestDTO request, TransferEdgeResponseDTO response) {
        boolean validationResult = super.validate(request, response);
        if (!validationResult) {
            return false;
        }

        handleValidation(ValidationUtil.validateMandatory(request.getSource(), "source"), response);
        handleValidation(ValidationUtil.validateMandatory(request.getDestination(), "destination"), response);

        if (response.hasError()) {
            return false;
        }

        handleValidation(ValidationUtil.validateAccountNumber(request.getSource().getAccountNumber(), "source.accountNumber"), response);
        handleValidation(ValidationUtil.validateAmount(request.getSource().getAmount(), "source.amount"), response);
        handleValidation(ValidationUtil.validateMandatory(request.getSource().getDescription(), "source.description"), response);

        handleValidation(ValidationUtil.validateAccountNumber(request.getDestination().getAccountNumber(), "destination.accountNumber"), response);
        handleValidation(ValidationUtil.validateAmount(request.getDestination().getAmount(), "destination.amount"), response);
        handleValidation(ValidationUtil.validateMandatory(request.getDestination().getDescription(), "destination.description"), response);

        if (response.hasError()) {
            return false;
        }

        if (request.getSource().getAmount().compareTo(request.getDestination().getAmount()) != 0) {
            response.addError(new ErrorDTO(ErrorCodeEnum.InconsistentData, "Transfer items are not balanced.", "TransferBalance"));
            return false;
        }

        return handleResult(response);
    }
}
