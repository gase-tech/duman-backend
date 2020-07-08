package com.kstech.duman.controller.base;

import com.kstech.duman.domain.base.BaseEntity;
import com.kstech.duman.model.base.BaseEntityDto;
import com.kstech.duman.model.base.BaseEntityResource;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Component
public abstract class AbstractReadEntityController<DTO extends BaseEntityDto, Entity extends BaseEntity, Resource extends BaseEntityResource, PK extends Serializable> extends AbstractController<DTO, Entity, Resource, PK> {
    public AbstractReadEntityController() {
    }

    @GetMapping({"/all"})
    public List<Resource> all() {
        List<Entity> all = this.getService().all();
        return this.toResource(all);
    }

    @GetMapping({"/pageable"})
    public PageImpl<Resource> pageable(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size, @RequestParam(name = "sort", required = false) String sort, @RequestParam(name = "direction", required = false, defaultValue = "ASC") String direction) {
        Pageable pageable = PageRequest.of(page, size);
        if (StringUtils.isNotBlank(sort)) {
            pageable = PageRequest.of(page, size, Sort.Direction.valueOf(direction), sort);
        }

        PageImpl<Entity> resultEntityPage = this.getService().pageable(pageable);
        List<Entity> resultEntities = resultEntityPage.getContent();
        List<Resource> resultResources = resultEntities.stream().map(entity -> this.getConverter().toResource(entity)).collect(Collectors.toList());
        return new PageImpl(resultResources, pageable, resultEntityPage.getTotalElements());
    }

    @GetMapping({"/{id}"})
    public Resource get(@PathVariable("id") PK id) {
        return this.getConverter().toResource(this.getService().getEntity(id));
    }
}