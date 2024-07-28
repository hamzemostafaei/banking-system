package com.hamze.banking.system.shared.data.base.enumeration;

import java.io.Serializable;

public interface IMappedEnum<T extends Serializable> {
    T getMapping();
}
