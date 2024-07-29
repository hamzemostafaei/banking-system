package com.hamze.banking.system.data.access.enumeration.converter;

import com.hamze.banking.system.shared.data.base.enumeration.IMappedEnum;
import com.hamze.banking.system.shared.util.ReflectionUtil;
import jakarta.persistence.AttributeConverter;
import lombok.Getter;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class AGenericEnumTypeConverter<E extends Enum<E> & IMappedEnum<D>, D extends Serializable>
        implements AttributeConverter<E, D> {

    @Getter
    private final Class<E> enumType;
    @Getter
    private final Class<D> mappingType;
    private final Map<D, E> valueMap;

    public AGenericEnumTypeConverter() {

        this.enumType = ReflectionUtil.getGenericParameterType(this, 0);
        this.mappingType = ReflectionUtil.getGenericParameterType(this, 1);

        E[] enumValues = enumType.getEnumConstants();
        this.valueMap = new HashMap<>(enumValues.length);
        for (E enumValue : enumValues) {
            this.valueMap.put(enumValue.getMapping(), enumValue);
        }
    }

    @Nullable
    @Override
    public D convertToDatabaseColumn(E enumValue) {
        if (enumValue == null) {
            return null;
        }

        return enumValue.getMapping();
    }

    @Nullable
    @Override
    public E convertToEntityAttribute(D dbData) {
        if (dbData == null) {
            return null;
        }

        return this.valueMap.get(dbData);
    }

}