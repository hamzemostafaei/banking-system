package com.hamze.banking.system.shared.util;

import com.hamze.banking.system.shared.data.base.dto.ErrorDTO;
import com.hamze.banking.system.shared.data.base.enumeration.ErrorCodeEnum;

import java.math.BigDecimal;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public final class ValidationUtil {

    private static final Pattern TRACKING_NUMBER_PATTERN = Pattern.compile("^[\\w\\-:.]{8,64}$");
    private static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern.compile("^[0-9]{1,4}\\.[0-9]{1,4}\\.[1-9][0-9]{0,7}\\.[0-9]{1,3}$");
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

    public static ErrorDTO validateAccountNumber(String accountNumber) {
        return validateAccountNumber(accountNumber, "accountNumber", true);
    }

    public static ErrorDTO validateAmount(BigDecimal amount) {
        return validateAmount(amount, "amount");
    }

    public static ErrorDTO validateAmount(BigDecimal amount, String fieldName) {
        return validateAmount(amount, fieldName, true);
    }

    public static ErrorDTO validateAmount(BigDecimal amount, String fieldName, boolean mandatory) {
        if (mandatory && amount == null) {
            return new ErrorDTO(ErrorCodeEnum.MandatoryField, "MandatoryFieldNotSet", fieldName);
        }

        if (amount != null && amount.compareTo(BigDecimal.ZERO) < 0) {
            return new ErrorDTO(ErrorCodeEnum.OutOfBoundsData, "DataFormatMismatch", fieldName);
        }

        return null;
    }

    public static ErrorDTO validateAccountNumber(String accountNumber, String fieldName) {
        return validateAccountNumber(accountNumber, fieldName, true);
    }

    public static ErrorDTO validateAccountNumber(String accountNumber,
                                                 String fieldName,
                                                 boolean mandatory) {

        if (mandatory && accountNumber == null) {
            return new ErrorDTO(ErrorCodeEnum.MandatoryField, "MandatoryFieldNotSet", fieldName);
        }

        if (accountNumber != null && !ACCOUNT_NUMBER_PATTERN.matcher(accountNumber).matches()) {
            return new ErrorDTO(ErrorCodeEnum.DataFormatMismatch, "DataFormatMismatch", fieldName);
        }

        return null;
    }

    public static ErrorDTO validateNationalCode(String nationalCode) {
        return validateNationalCode(nationalCode, "nationalId");
    }

    public static ErrorDTO validateNationalCode(String nationalCode, String fieldName) {

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

    public static ErrorDTO validateCustomerNumber(Integer customerNumber) {
        if (customerNumber == null) {
            return new ErrorDTO(ErrorCodeEnum.MandatoryField, "MandatoryFieldNotSet", "customerNumber");
        }

        if (customerNumber == 0 || customerNumber > 99999999) {
            return new ErrorDTO(ErrorCodeEnum.DataFormatMismatch, "DataFormatMismatch", "customerNumber");
        }

        return null;
    }

}
