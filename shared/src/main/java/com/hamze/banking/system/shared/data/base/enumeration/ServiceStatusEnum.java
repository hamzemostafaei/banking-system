package com.hamze.banking.system.shared.data.base.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum ServiceStatusEnum implements IMappedEnum<Integer> {

    BeingProcessed(0),
    Successful(1),
    Unsuccessful(2),
    RetryLater(3),
    ContactSystemAdministrator(4);

    private static final Map<Integer, ServiceStatusEnum> VALUE_MAP = new HashMap<>();

    static {
        for (ServiceStatusEnum value : ServiceStatusEnum.values()) {
            VALUE_MAP.put(value.getStatusCode(), value);
        }
    }

    private final int statusCode;

    ServiceStatusEnum(int statusCode) {
        this.statusCode = statusCode;
    }

    @JsonCreator
    public static ServiceStatusEnum getByValue(Integer statusCode) {
        if (statusCode == null) {
            return null;
        }

        ServiceStatusEnum value = VALUE_MAP.get(statusCode);
        if (value == null) {
            throw new IllegalArgumentException("Bad status code [" + statusCode + "] is provided.");
        }

        return value;
    }

    @JsonValue
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public Integer getMapping() {
        return getStatusCode();
    }

    public static ServiceStatusEnum getBySuccessFlag(Boolean successful) {
        if (successful == null) {
            return null;
        }

        if (successful) {
            return ServiceStatusEnum.Successful;
        } else {
            return ServiceStatusEnum.Unsuccessful;
        }
    }
}
