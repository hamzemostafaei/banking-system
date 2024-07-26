package com.hamze.banking.system.shared.domain.enumeration;

import java.io.Serializable;

public interface IMappedEnum<E extends Enum<E>, T extends Serializable> {
    T getMapping();
}
