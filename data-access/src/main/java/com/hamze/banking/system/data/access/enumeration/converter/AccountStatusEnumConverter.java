package com.hamze.banking.system.data.access.enumeration.converter;

import com.hamze.banking.system.data.access.enumeration.AccountStatusEnum;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AccountStatusEnumConverter extends AGenericEnumTypeConverter<AccountStatusEnum, Integer> {
}
