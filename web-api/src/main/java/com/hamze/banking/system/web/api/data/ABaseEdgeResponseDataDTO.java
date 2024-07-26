package com.hamze.banking.system.web.api.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ABaseEdgeResponseDataDTO<T> extends ABaseEdgeResponseDTO {

    @JsonProperty("data")
    private T data;
}
