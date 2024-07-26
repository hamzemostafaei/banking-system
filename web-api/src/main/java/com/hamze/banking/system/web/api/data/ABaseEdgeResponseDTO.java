package com.hamze.banking.system.web.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hamze.banking.system.shared.data.base.dto.ErrorDTO;
import com.hamze.banking.system.shared.data.base.enumeration.ServiceStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class ABaseEdgeResponseDTO extends ABaseEdgeRequestDTO {

    @JsonProperty("registrationDate")
    private Date registrationDate;

    @JsonProperty("status")
    private ServiceStatusEnum status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("errors")
    private List<ErrorDTO> errors;

    public List<ErrorDTO> getErrors(boolean buildIfEmpty) {

        if (errors == null && buildIfEmpty) {
            errors = new ArrayList<>();
        }
        return errors;
    }

    public void setErrors(List<ErrorDTO> errors) {
        this.errors = errors;

        if (!CollectionUtils.isEmpty(errors)) {
            setStatus(ServiceStatusEnum.Unsuccessful);
        }
    }

    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    public void addError(ErrorDTO error) {
        if (getErrors() == null) {
            setErrors(new ArrayList<>(Arrays.asList(error)));
        } else {
            getErrors().add(error);
        }

        setStatus(ServiceStatusEnum.Unsuccessful);
    }

    public boolean hasError() {
        List<ErrorDTO> errors = getErrors();
        return errors != null && !errors.isEmpty();
    }
}
