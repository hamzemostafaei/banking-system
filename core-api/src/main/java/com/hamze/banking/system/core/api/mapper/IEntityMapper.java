package com.hamze.banking.system.core.api.mapper;

import com.hamze.banking.system.core.api.data.ABaseDTO;
import com.hamze.banking.system.data.access.entity.ABaseEntity;

public interface IEntityMapper<E extends ABaseEntity, D extends ABaseDTO> {

    D entityToDTO(E entity);

    E dtoToEntity(D dto);
}
