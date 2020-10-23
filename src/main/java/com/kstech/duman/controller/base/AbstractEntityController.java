package com.kstech.duman.controller.base;

import com.kstech.duman.domain.base.BaseEntity;
import com.kstech.duman.model.base.BaseEntityDto;
import com.kstech.duman.model.base.BaseEntityResource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.io.Serializable;

@Component
public abstract class AbstractEntityController<DTO extends BaseEntityDto, Entity extends BaseEntity, Resource extends BaseEntityResource, PK extends Serializable> extends AbstractReadEntityController<DTO, Entity, Resource, PK> {
    public AbstractEntityController() {
    }

    @DeleteMapping({"/{id}"})
    public void delete(@PathVariable("id") PK id) {
        this.getService().delete(id);
    }

    @PostMapping
    public Resource save(@RequestBody @Valid DTO dto) {
        Entity entity = this.getService().save(this.getConverter().toEntity(dto));
        return this.getConverter().toResource(entity);
    }

    @PutMapping({"/{id}"})
    public Resource put(@PathVariable("id") PK id, @RequestBody @Valid DTO dto) {
        Entity forSave = this.getConverter().toEntity(dto);
        Entity entity = this.getService().put(id, forSave);
        return this.getConverter().toResource(entity);
    }
}
