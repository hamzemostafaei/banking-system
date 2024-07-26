package com.hamze.banking.system.core.api.mapper;

import com.hamze.banking.system.core.api.data.ABaseDTO;
import com.hamze.banking.system.data.access.entity.ABaseEntity;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface IEntityMapper<E extends ABaseEntity, D extends ABaseDTO> {

    D entityToDTO(E entity);

    @Mapping(target = "version", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "creationDate", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "creatorUserId", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    E dtoToEntity(D dto);
}
