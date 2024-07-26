package com.hamze.banking.system.core.api.exception;

import com.hamze.banking.system.shared.data.base.dto.ErrorDTO;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CoreServiceException extends RuntimeException {

    @Singular
    protected List<ErrorDTO> errors;
    protected String message;

}

