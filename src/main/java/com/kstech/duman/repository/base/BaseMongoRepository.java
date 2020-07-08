package com.kstech.duman.repository.base;

import com.kstech.duman.domain.base.BaseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseMongoRepository<T extends BaseEntity, PK extends Serializable> extends MongoRepository<T, PK> {
}