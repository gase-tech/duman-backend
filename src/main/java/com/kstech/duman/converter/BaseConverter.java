package com.kstech.duman.converter;

import com.kstech.duman.domain.base.BaseEntity;
import com.kstech.duman.model.base.BaseEntityDto;
import com.kstech.duman.model.base.BaseEntityResource;

public interface BaseConverter<DTO extends BaseEntityDto, Entity extends BaseEntity, Resource extends BaseEntityResource> {
    Resource toResource(Entity var1);

    Entity toEntity(DTO var1);
}
