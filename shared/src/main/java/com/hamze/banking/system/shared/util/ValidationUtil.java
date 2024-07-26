package com.hamze.banking.system.shared.util;

import com.hamze.banking.system.shared.data.base.dto.ErrorDTO;
import com.hamze.banking.system.shared.data.base.enumeration.ErrorCodeEnum;

import java.util.regex.Pattern;

public final class ValidationUtil {

    private static final Pattern TRACKING_NUMBER_PATTERN = Pattern.compile("^[\\w\\-:.]{8,64}$");
    private static final Pattern DEPOSIT_NUMBER_PATTERN = Pattern.compile("^[0-9]{1,4}\\.[0-9]{1,4}\\.[1-9][0-9]{0,7}\\.[0-9]{1,3}$");

    public static ErrorDTO validateTrackingNumber(String trackingNumber) {

        if (trackingNumber == null) {
            return new ErrorDTO(ErrorCodeEnum.MandatoryField, "MandatoryFieldNotSet", "trackingNumber");
        }

        if (!TRACKING_NUMBER_PATTERN.matcher(trackingNumber).matches()) {
            return new ErrorDTO(ErrorCodeEnum.DataFormatMismatch, "DataFormatMismatch", "trackingNumber");
        }

        return null;
    }

    public static ErrorDTO validateMandatory(Object value, String fieldName) {
        if (value == null) {
            return new ErrorDTO(ErrorCodeEnum.MandatoryField, "MandatoryFieldNotSet", fieldName);
        }

        return null;
    }

    public static ErrorDTO validateDepositNumber(String depositNumber) {
        return validateDepositNumber(depositNumber, "depositNumber", true);
    }

    public static ErrorDTO validateDepositNumber(String depositNumber, String fieldName) {
        return validateDepositNumber(depositNumber, fieldName, true);
    }

    public static ErrorDTO validateDepositNumber(String depositNumber,
                                                 String fieldName,
                                                 boolean mandatory) {

        if (mandatory && depositNumber == null) {
            return new ErrorDTO(ErrorCodeEnum.MandatoryField, "MandatoryFieldNotSet", fieldName);
        }

        if (depositNumber != null && !DEPOSIT_NUMBER_PATTERN.matcher(depositNumber).matches()) {
            return new ErrorDTO(ErrorCodeEnum.DataFormatMismatch, "DataFormatMismatch", fieldName);
        }

        return null;
    }

}
