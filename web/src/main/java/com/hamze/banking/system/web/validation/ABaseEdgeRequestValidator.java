package com.hamze.banking.system.web.validation;

import com.hamze.banking.system.shared.data.base.dto.ErrorDTO;
import com.hamze.banking.system.shared.data.base.enumeration.ErrorCodeEnum;
import com.hamze.banking.system.shared.data.base.enumeration.ServiceStatusEnum;
import com.hamze.banking.system.shared.util.SnowFlakeUniqueIDGenerator;
import com.hamze.banking.system.shared.util.ValidationUtil;
import com.hamze.banking.system.web.api.data.*;
import com.hamze.banking.system.web.api.validation.IEdgeRequestValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("BaseEdgeRequestValidator")
public abstract class ABaseEdgeRequestValidator<RQ extends ABaseEdgeRequestDTO,
                                                RP extends ABaseEdgeResponseDTO>
        implements IEdgeRequestValidator<RQ, RP> {

    @Value("${com.hamze.banking.system.node-id}")
    private Long nodeId;

    @Override
    public boolean validate(RQ request, RP response) {
        assert response != null;

        response.setStatus(ServiceStatusEnum.Successful);

        ErrorDTO error;
        if (request == null) {
            error = new ErrorDTO(ErrorCodeEnum.MandatoryField, "Request");
            response.addError(error);

            return false;
        }

        handleValidation(ValidationUtil.validateTrackingNumber(request.getTrackingNumber()), response);

        response.setRegistrationDate(new Date());
        response.setTrackingNumber(request.getTrackingNumber());
        if (request.getTransactionId() != null) {
            response.setTransactionId(request.getTransactionId());
        } else {
            response.setTransactionId(Long.toString(SnowFlakeUniqueIDGenerator.nextId(nodeId)));
        }

        return handleResult(response);
    }

    @SuppressWarnings("UnusedReturnValue")
    protected boolean handleValidation(ErrorDTO error, RP response) {
        if (error != null) {
            response.addError(error);
            return false;
        }

        return true;
    }

    protected boolean handleResult(RP response) {
        return !response.hasError();
    }
}
