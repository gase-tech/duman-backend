package com.kstech.duman.cache.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class CacheEvictAspectConfiguration {
    @Bean
    public CacheEvictAspect cacheEvictAspect() {
        return new CacheEvictAspect();
    }
}
