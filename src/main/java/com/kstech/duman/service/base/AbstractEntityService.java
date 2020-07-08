package com.kstech.duman.service.base;

import com.kstech.duman.domain.base.BaseEntity;
import com.kstech.duman.exception.NotFoundException;
import com.kstech.duman.repository.base.BaseMongoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;


@Slf4j
@CacheConfig(cacheNames = "cache")
public abstract class AbstractEntityService<T extends BaseEntity, PK extends Serializable> {
    public AbstractEntityService() {
    }

    BaseEntity entity = new BaseEntity() {
        @Override
        public <T extends BaseEntity> void update(T entity) {

        }
    };

    public abstract BaseMongoRepository<T, PK> getMongoRepository();

    protected T verifySave(T entity) {
        return entity;
    }

    protected T verifyPut(T theReal, T forSave) {
        return theReal;
    }

    protected T verifyDelete(T entity) {
        return entity;
    }

    @CacheEvict
    @Transactional
    public T save(T entity) {
        this.verifySave(entity);
        this.getMongoRepository().save(entity);

        return entity;
    }

    @CacheEvict
    @Transactional
    public List<T> saveAll(List<T> entities) {
        entities.forEach(this::verifySave);
        return this.getMongoRepository().saveAll(entities);
    }

    @CacheEvict
    @Transactional
    public T put(PK id, T forSave) {
        T theReal = this.getEntity(id);
        theReal.update(forSave);
        this.verifyPut(theReal, forSave);
        this.getMongoRepository().save(theReal);

        return theReal;
    }

    @CacheEvict
    @Transactional
    public void delete(PK id) {
        T entity = this.getEntity(id);
        this.verifyDelete(entity);
        this.getMongoRepository().delete(entity);
    }

    @Cacheable(keyGenerator = "customKeyGenerator")
    public T getEntity(PK id) {
        Optional<T> entityOpt = this.getMongoRepository().findById(id);
        if (!entityOpt.isPresent()) {
            throw new NotFoundException("Entity not found.");
        } else {
            return entityOpt.get();
        }
    }

    @Cacheable(keyGenerator = "customKeyGenerator")
    public List<T> all() {
        return this.getMongoRepository().findAll();
    }

    @Cacheable(keyGenerator = "customKeyGenerator")
    public PageImpl pageable(Pageable pageable) {
        Page resp;
        long count;

        resp = this.getMongoRepository().findAll(pageable);
        count = this.getMongoRepository().count();

        return new PageImpl<>(resp.getContent(), pageable, count);
    }
}
