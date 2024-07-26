package com.hamze.banking.system.shared.util;

import com.hamze.banking.system.shared.data.base.dto.ErrorDTO;
import com.hamze.banking.system.shared.data.base.enumeration.ErrorCodeEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public final class ValidationUtil {

    private static final Pattern TRACKING_NUMBER_PATTERN = Pattern.compile("^[\\w\\-:.]{8,64}$");
    private static final Pattern DEPOSIT_NUMBER_PATTERN = Pattern.compile("^[0-9]{1,4}\\.[0-9]{1,4}\\.[1-9][0-9]{0,7}\\.[0-9]{1,3}$");
    private static final Pattern NATIONAL_CODE_PATTERN = Pattern.compile("^\\d{10}$");

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

    public static ErrorDTO validateNationalCode(String nationalCode) {
        return validateNationalCode(nationalCode,"nationalId");
    }

    public static ErrorDTO validateNationalCode(String nationalCode,String fieldName) {

        if (nationalCode == null) {
            return new ErrorDTO(ErrorCodeEnum.MandatoryField, "MandatoryFieldNotSet", fieldName);
        }

        if (!NATIONAL_CODE_PATTERN.matcher(nationalCode).matches()) {
            return new ErrorDTO(ErrorCodeEnum.DataFormatMismatch, "DataFormatMismatch", fieldName);
        }

        long numericNationalCode = Long.parseLong(nationalCode);
        byte[] arrayNationalCode = new byte[10];

        for (int i = 0; i < 10; i++) {
            arrayNationalCode[i] = (byte) (numericNationalCode % 10);
            numericNationalCode = numericNationalCode / 10;
        }

        int sum = 0;
        for (int i = 9; i > 0; i--) {
            sum += arrayNationalCode[i] * (i + 1);
        }

        int temp = sum % 11;
        if (temp < 2) {
            if (arrayNationalCode[0] == temp) {
                return null;
            } else {
                return new ErrorDTO(ErrorCodeEnum.DataFormatMismatch, "DataFormatMismatch", fieldName);
            }
        } else {
            if (arrayNationalCode[0] == 11 - temp) {
                return null;
            } else {
                return new ErrorDTO(ErrorCodeEnum.DataFormatMismatch, "DataFormatMismatch", fieldName);
            }
        }
    }

}
