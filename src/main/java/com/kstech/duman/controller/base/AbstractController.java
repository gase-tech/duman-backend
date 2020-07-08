package com.kstech.duman.controller.base;

import com.kstech.duman.converter.BaseConverter;
import com.kstech.duman.domain.base.BaseEntity;
import com.kstech.duman.model.base.BaseEntityDto;
import com.kstech.duman.model.base.BaseEntityResource;
import com.kstech.duman.service.base.AbstractEntityService;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractController<DTO extends BaseEntityDto, Entity extends BaseEntity, Resource extends BaseEntityResource, PK extends Serializable> {
    public AbstractController() {
    }

    protected abstract AbstractEntityService<Entity, PK> getService();

    protected abstract BaseConverter<DTO, Entity, Resource> getConverter();

    protected List<Resource> toResource(List<Entity> all) {
        return !all.isEmpty() ? (List<Resource>) all.stream().map(entity -> (BaseEntityResource) this.getConverter().toResource(entity)).collect(Collectors.toList()) : new ArrayList<>();
    }

    protected Resource toResource(Entity entity) {
        return entity != null ? this.getConverter().toResource(entity) : null;
    }

    protected Resource toResource(Optional<Entity> entity) {
        return entity.map(this::toResource).orElse(null);
    }

    protected List<Entity> toEntity(List<DTO> dtos) {
        return !dtos.isEmpty() ? dtos.stream().map(dto -> this.getConverter().toEntity(dto)).collect(Collectors.toList()): null;
    }

    protected void clearAuditing(BaseEntityResource resource) {
        if (resource != null) {
            resource.setLastModifiedDate(null);
            resource.setCreatedDate(null);
        }

    }
}
