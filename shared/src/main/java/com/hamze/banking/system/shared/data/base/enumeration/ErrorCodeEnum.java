package com.hamze.banking.system.shared.data.base.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum ErrorCodeEnum implements IMappedEnum<Integer> {

    GeneralError(0),
    MandatoryField(1),
    DataFormatMismatch(2),
    DuplicateData(3),
    InconsistentData(4),
    ServiceContractsViolation(5),
    DataNotFound(6),
    NotEnoughResources(7),
    NotAuthorizedForData(8),
    InternalServiceError(9),
    SecurityError(10),
    OutOfBoundsData(11),
    InactiveReference(12),
    ExpiredReference(13);

    private static final Map<Integer, ErrorCodeEnum> VALUE_MAP = new HashMap<>();

    static {
        for (ErrorCodeEnum value : ErrorCodeEnum.values()) {
            VALUE_MAP.put(value.getErrorCode(), value);
        }
    }

    private final int errorCode;

    ErrorCodeEnum(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonCreator
    public static ErrorCodeEnum getByValue(Integer errorCode) {
        ErrorCodeEnum value = VALUE_MAP.get(errorCode);
        if (value == null) {
            throw new IllegalArgumentException("Bad error code [" + errorCode + "] is provided.");
        }

        return value;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public Integer getMapping() {
        return getErrorCode();
    }
}